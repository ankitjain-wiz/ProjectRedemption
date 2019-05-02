package org.epo.cms.edfs.services.common.aop;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.epo.cms.edfs.services.common.beans.BICMSDossier;
import org.epo.cms.edfs.services.common.beans.BICMSLookupResponse;
import org.epo.cms.edfs.services.common.dao.ApplicationDao;
import org.epo.cms.edfs.services.common.dao.CMSCaseMasterDao;
import org.epo.cms.edfs.services.common.dao.ProductOrderDao;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.common.externalapi.ExternalApiService;
import org.epo.cms.edfs.services.common.util.Constants;
import org.epo.cms.edfs.services.dossierpersistence.entity.Application;
import org.epo.cms.edfs.services.dossierpersistence.entity.CMSCaseMaster;
import org.epo.cms.edfs.services.dossierpersistence.entity.ProductOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
public class BICMSApiAspect { 

  @Autowired
  private ApplicationDao applicationDao;

  @Autowired
  private ProductOrderDao productOrderDao;

  @Autowired
  private CMSCaseMasterDao cMSCaseMasterDao;

  @Autowired
  private ExternalApiService externalApiService;

  @Autowired
  private ResponseValidator responseValidator;

  private static final Logger LOGGER = LogManager.getLogger();

  /**
   * Common logging just After method Entry
   * 
   * @param joinPoint to get the joinPoint
   * @throws CustomException for CustomException
   * @throws ParseException
   * @throws IOException
   * @throws IllegalAccessException
   */
  @Before("selectAll()")
  public void beforeAdvice(JoinPoint joinPoint) 
		  throws CustomException, IOException, ParseException, IllegalAccessException {
   
      MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
      Method method = methodSignature.getMethod();
      if (!("createTrackingSheet".equals(method.getName())
          || "getDocCode".equals(method.getName()))) {

        String applicationNumber = getApplicationNumber(joinPoint, methodSignature, method);
        LOGGER.debug("Application Number is : {} ", applicationNumber);
        if (!StringUtils.isEmpty(applicationNumber)) {
          Application application = applicationDao.findByApplicationNumber(applicationNumber);
          if (null == application) {
            saveApplicationDataFromCMS(applicationNumber);
          }
        }
      }   
  }

  /**
   * This method calls the external service method to get the data from cms system.
   * 
   * @param applicationNumber to get applicationNumber
   * @throws CustomException for CustomExceptions
   * @throws IOException for IOException
   * @throws ParseException for ParseException
   */
  private void saveApplicationDataFromCMS(String applicationNumber)
      throws CustomException, IOException, ParseException {

    String cmsLookupHostUrl = "ReadDossier/" + applicationNumber;
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    String bicmsLookupResponseStr = externalApiService
        .getCmsResponseUsingExternalApi(cmsLookupHostUrl, Constants.HOST_FOR_CMSSERVICE);
    LOGGER.debug("CMS api response is : {} ", bicmsLookupResponseStr);
    if (!StringUtils.isEmpty(bicmsLookupResponseStr)) {
      BICMSLookupResponse bicmsLookupResponse =
          objectMapper.readValue(bicmsLookupResponseStr, BICMSLookupResponse.class);
      if (null != bicmsLookupResponse
          && !CollectionUtils.isEmpty(bicmsLookupResponse.getID06ReadDossier())) {
        Application application = new Application();
        setApplicationData(application, bicmsLookupResponse);
      }
    } else {
        throw new CustomException(
                responseValidator.getErrorResponse(Integer.parseInt(Constants.BADREQUEST)));
   }
  }

  /**
   * This method insert the data into Application, CMScaseMaster and ProductOrder table.
   * 
   * @param Application application
   * @param BICMSLookupResponse bicmsLookupResponse
   * @throws CustomException for CustomExceptions
   * @throws ParseException for parseEceptions
   */
  private void setApplicationData(Application application, BICMSLookupResponse bicmsLookupResponse)
      throws CustomException, ParseException {
    for (BICMSDossier bicmsDossier : bicmsLookupResponse.getID06ReadDossier()) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Application number is : " + bicmsDossier.getApplicationNumber());
      }
      if (!StringUtils.isEmpty(bicmsDossier.getDossierNumber())) {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setDossierNumber(bicmsDossier.getDossierNumber());
        productOrder.setPhase(bicmsDossier.getDossierPhase());
        CMSCaseMaster cmsCaseMaster = new CMSCaseMaster();
        cmsCaseMaster.setApplication(application);
        application.setApplicationNumber(bicmsDossier.getApplicationNumber());
        application.setApplicationType(bicmsDossier.getDossierType());
        application.setFilingLanguage(bicmsDossier.getFilingLanguage());
        application.setProceduralLanguage(bicmsDossier.getProceduralLanguage());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (!StringUtils.isEmpty(bicmsDossier.getFilingDate())) {
          application.setFilingDatetime(simpleDateFormat.parse(bicmsDossier.getFilingDate()));
        }
        if (!StringUtils.isEmpty(bicmsDossier.getReceivingDate())) {
          application.setReceiptDatetime(simpleDateFormat.parse(bicmsDossier.getReceivingDate()));
        }

        Integer applicationId = applicationDao.saveApplication(application);
        if (applicationId != -1) {
          application.setApplicationId(applicationId);

          productOrder.setApplication(application);

          Integer productOrderId = productOrderDao.saveProductOrder(productOrder);
          productOrder.setProductOrderId(productOrderId);
          cmsCaseMaster.setProductOrder(productOrderDao.findByProductOrderId(productOrderId));
          cMSCaseMasterDao.saveCMSCaseMaster(cmsCaseMaster);
        }
      }
    }
  }

  /**
   * This method checks if applicationNumber field is available in method signature and if
   * applicationNumber is present then fetch the applicationNumber value.
   * 
   * @param JoinPoint joinPoint
   * @param MethodSignature methodSignature
   * @param Method method
   * @return String apllicationNumber value
   * @throws IllegalArgumentException for IllegalArgumentException
   * @throws IllegalAccessException for IllegalAccessException
   */
  private String getApplicationNumber(JoinPoint joinPoint, MethodSignature methodSignature,
      Method method) throws IllegalAccessException {
    String applicationNumber = "";
    Object[] signatureArgs = joinPoint.getArgs();

    RequestMethod[] requestMethods = method.getAnnotation(RequestMapping.class).method();

    if (Constants.POST.equalsIgnoreCase(requestMethods[0].name())) {

      Class<?> thisClass = signatureArgs[0].getClass();

      Field[] aClassFields = thisClass.getDeclaredFields();
      for (Field field : aClassFields) {
        field.setAccessible(true);
        if (Constants.APPLICATION_NUMBER.equalsIgnoreCase(field.getName())) {
          applicationNumber = (String) field.get(signatureArgs[0]);
          break;
        }
      }
    } else if (Constants.GET.equalsIgnoreCase(requestMethods[0].name())) {

      String[] parameterNames = methodSignature.getParameterNames();
      for (int i = 0; i < parameterNames.length; i++) {
        if (parameterNames[i].equalsIgnoreCase(Constants.APPLICATION_NUMBER)) {
          applicationNumber = (String) signatureArgs[i];
          break;
        }
      }

    }

    return applicationNumber;
  }

  /**
   * This pointcut Method is used to call beforeAdvice()(@before) in each controller methods whose
   * path matches.
   * 
   */
  @Pointcut("execution(* org.epo.cms.edfs.services.*.controller..*(..)) ")
  private void selectAll() {
    /* .. This method is used to declare pointcut */
  }

}
