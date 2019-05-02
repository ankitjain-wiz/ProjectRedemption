package org.epo.cms.edfs.services.settings.aspect;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.epo.cms.edfs.services.common.util.Constants;

/**
 * Implementation of logging using AOP.
 * 
 * @author amigarg
 * 
 */

@Aspect
public class LoggingAspect {

  private static final Logger LOGGER = LogManager.getLogger();

  /**
   * Common logging just After method Entry
   * 
   * @param joinPoint of type JoinPoint
   */
  @Before("selectAll()")
  public void beforeAdvice(JoinPoint joinPoint) {

    LOGGER.info(Constants.ENTRY + joinPoint.getTarget().getClass().getSimpleName() + " "
        + joinPoint.getSignature().getName() + "  " + Calendar.getInstance().getTime());

  }

  /**
   * Common logging just before method Exit
   * 
   * @param joinPoint of type JoinPoint
   */
  @After("selectAll()")
  public void afterAdvice(JoinPoint joinPoint) {

    LOGGER.info(Constants.EXIT + joinPoint.getTarget().getClass().getSimpleName() + " "
        + joinPoint.getSignature().getName() + "  " + Calendar.getInstance().getTime());

  }

  /**
   * Common logging when any exception is thrown
   * 
   * @param jp of type JoinPoint
   * @param error of type Throwable
   */
  @AfterThrowing(pointcut = "selectAll()", throwing = "error")
  public void afterThrow(JoinPoint jp, Throwable error) {
    LOGGER.error(Constants.ERROR + jp.getTarget().getClass().getSimpleName() + " "
        + jp.getSignature().getName() + "  " + Calendar.getInstance().getTime() + " Error- "
        + error);

  }

  /**
   * Pointcut Method used for defining the package where logging is implemented
   */
  @Pointcut("execution(* org.epo.cms.edfs.services.settings..*(..))")
  private void selectAll() {
    // This method is empty.
  }


}
