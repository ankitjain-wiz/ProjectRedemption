package org.epo.cms.edfs.services.settings.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.util.PropertyFileReader;
import org.epo.cms.edfs.services.settings.beans.CaseSample;
import org.epo.cms.edfs.services.settings.beans.Helper;
import org.epo.cms.edfs.services.settings.beans.HelperList;
import org.epo.cms.edfs.services.settings.beans.Module;
import org.epo.cms.edfs.services.settings.beans.ModuleList;
import org.epo.cms.edfs.services.settings.beans.Notification;
import org.epo.cms.edfs.services.settings.beans.NotificationFilter;
import org.epo.cms.edfs.services.settings.beans.PersonalTag;
import org.epo.cms.edfs.services.settings.beans.PersonalTagList;
import org.epo.cms.edfs.services.settings.beans.PrintOption;
import org.epo.cms.edfs.services.settings.beans.PrintOptionList;
import org.epo.cms.edfs.services.settings.beans.Role;
import org.epo.cms.edfs.services.settings.beans.RoleDetail;
import org.epo.cms.edfs.services.settings.beans.RoleSettingResponse;
import org.epo.cms.edfs.services.settings.beans.SettingsRequest;
import org.epo.cms.edfs.services.settings.beans.TimeLimit;
import org.epo.cms.edfs.services.settings.beans.UpdaterSettingsResponse;
import org.epo.cms.edfs.services.settings.beans.Workspace;
import org.epo.cms.edfs.services.settings.beans.WorkspaceList;
import org.epo.cms.edfs.services.settings.dao.GlobalProfileDao;
import org.epo.cms.edfs.services.settings.dao.HelperDao;
import org.epo.cms.edfs.services.settings.dao.HelperGlobalSettingMappingDao;
import org.epo.cms.edfs.services.settings.dao.ModuleDao;
import org.epo.cms.edfs.services.settings.dao.PersonalLevelDao;
import org.epo.cms.edfs.services.settings.dao.PersonalTagDao;
import org.epo.cms.edfs.services.settings.dao.PersonalTagGlobalSettingMappingDao;
import org.epo.cms.edfs.services.settings.dao.PrintGlobalSettingMappingDao;
import org.epo.cms.edfs.services.settings.dao.PrintOptionDao;
import org.epo.cms.edfs.services.settings.dao.RoleDao;
import org.epo.cms.edfs.services.settings.dao.WorkspaceDao;
import org.epo.cms.edfs.services.settings.dto.GlobalProfileDto;
import org.epo.cms.edfs.services.settings.dto.HelperDto;
import org.epo.cms.edfs.services.settings.dto.HelperGlobalSettingMappingDto;
import org.epo.cms.edfs.services.settings.dto.ModuleDto;
import org.epo.cms.edfs.services.settings.dto.PersonalLevelDto;
import org.epo.cms.edfs.services.settings.dto.PersonalTagDto;
import org.epo.cms.edfs.services.settings.dto.PersonalTagGlobalSettingMappingDto;
import org.epo.cms.edfs.services.settings.dto.PrintGlobalSettingMappingDto;
import org.epo.cms.edfs.services.settings.dto.PrintOptionDto;
import org.epo.cms.edfs.services.settings.dto.RoleDto;
import org.epo.cms.edfs.services.settings.dto.WorkSpaceDto;
import org.epo.cms.edfs.services.settings.enums.SectionName;
import org.epo.cms.edfs.services.settings.service.GlobalSettingsService;
import org.epo.cms.edfs.services.settings.utils.NotificationUtils;
import org.epo.cms.edfs.services.settings.utils.SettingsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Imp class to call DAO layer and populate data in response objects
 * 
 * @author dinagar
 *
 */
@Service
public class GlobalSettingsServiceImpl implements GlobalSettingsService {

	private static final Logger LOGGER = LogManager.getLogger(GlobalSettingsServiceImpl.class);

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private ModuleDao moduleDao;

	@Autowired
	private WorkspaceDao workpaceDao;

	@Autowired
	private PrintOptionDao printOptionDao;

	@Autowired
	private PersonalTagDao personalTagDAO;

	@Autowired
	private PersonalLevelDao personalLevelDao;

	@Autowired
	private GlobalProfileDao globalProfileDAO;

	@Autowired
	private PrintGlobalSettingMappingDao printGlobalSettingMappingDao;

	@Autowired
	private HelperGlobalSettingMappingDao helperGlobalSettingMappingDao;

	@Autowired
	private PersonalTagGlobalSettingMappingDao personalTagGlobalSettingMappingDAO;

	@Autowired
	protected PropertyFileReader propertyFileReader;

	@Autowired
	private HelperDao helperDAO;

	@Autowired
    private NotificationUtils notificationUtils;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public RoleSettingResponse getGlobalSettings(String role) throws CustomException {
		RoleDto roleObj = roleDao.getRole(role, true);
		RoleSettingResponse roleSettingsResponse = new RoleSettingResponse();

		Module module = getModuleResponse(roleObj);

		Workspace workspace = getWorkspaceResponse(roleObj);

		Helper helper = getHelperResponse(roleObj);

		PrintOption printOption = getPrintOptionResponse(roleObj);

		PersonalTag personalTag = getPersonalTagResponse(roleObj);
		
		String notificationAlertLevel = globalProfileDAO.getGlobalProfileAlertLevel(module, workspace,roleObj);
		if (null == notificationAlertLevel)
			notificationAlertLevel = "";
		
		
		NotificationFilter notificationFilter = notificationUtils.getNotificationFilter(role);
		
		Notification notification = new Notification();
		notification.setNotificationAlertLevel(notificationAlertLevel);
		notification.setNotificationFilter(notificationFilter);
		notification.setSectionName(SectionName.NOTIFICATION.value());

		int fontSize = getFontSize(roleObj);
		roleSettingsResponse.setFontSize(fontSize);

		roleSettingsResponse.setHelper(helper);
		roleSettingsResponse.setModule(module);
		roleSettingsResponse.setWorkspace(workspace);
		roleSettingsResponse.setPrintOption(printOption);
		roleSettingsResponse.setPersonalTag(personalTag);
		roleSettingsResponse.setNotification(notification);
		
		checkCaseSampleSection(roleObj, roleSettingsResponse);
		checkTimeLimitSection(roleObj, roleSettingsResponse);

		
		LOGGER.debug("final response datails ", roleSettingsResponse);

		return roleSettingsResponse;
	}
	
	private void checkCaseSampleSection(RoleDto roleObj, RoleSettingResponse roleSettingResponse) {
		if (roleObj.getRoleName().equals("Director")) {
			CaseSample caseSample = new CaseSample();
			caseSample.setSectionName(SectionName.CaseSampling.value());
			roleSettingResponse.setCaseSample(caseSample);
		} else {
			CaseSample caseSampleEmpty = new CaseSample();
			roleSettingResponse.setCaseSample(caseSampleEmpty);
		}
	}

	private void checkTimeLimitSection(RoleDto roleObj, RoleSettingResponse roleSettingResponse) {
		if (roleObj.getRoleName().equals("TimeLimitUser")) {
			TimeLimit timeLimit = new TimeLimit();
			timeLimit.setSectionName(SectionName.TimeLimit.value());
			roleSettingResponse.setTimeLimit(timeLimit);
		} else {
			TimeLimit timeLimitEmpty = new TimeLimit();
			roleSettingResponse.setTimeLimit(timeLimitEmpty);
		}
	}

	/**
	 * Method To get Global Font Size
	 * @param role : RoleDto
	 * @return int : int
	 */
	private int getFontSize(RoleDto role) {
		int fontSize = 0;
		
		Set<GlobalProfileDto> globalProfileDtoList = role.getGlobalProfile();
		if (CollectionUtils.isNotEmpty(globalProfileDtoList)) {
			for (GlobalProfileDto globalProfileDto : globalProfileDtoList) {
				fontSize = globalProfileDto.getFontSize();
				break;
			}
		}

		return fontSize;
	}

	/**
	 * @param role : RoleDto
	 * @return Module : Module
	 */
	private Module getModuleResponse(RoleDto role) {
		Module module = new Module();
		module.setSectionName(SectionName.DISPLAY.value());
		List<ModuleList> moduleList = new ArrayList<>();
		Set<ModuleDto> moduleDtoList = role.getModule();
		for (ModuleDto moduleDto : moduleDtoList) {
			ModuleList moduleListObj = new ModuleList();
			moduleListObj.setModuleId(moduleDto.getModuleId());
			moduleListObj.setModuleName(moduleDto.getModuleName());
			Set<GlobalProfileDto> globalProfileDtoList = role.getGlobalProfile();
			if (!globalProfileDtoList.isEmpty()) {
				for (GlobalProfileDto globalProfileDto : globalProfileDtoList) {
					if (globalProfileDto.getModule().getModuleId() == moduleDto.getModuleId()) {
						moduleListObj.setDefaultModule(true);
						break;
					}
				}

			}
			moduleList.add(moduleListObj);
		}
		module.setModuleList(moduleList);
		return module;
	}

	/**
	 * @param role : RoleDto
	 * @return Workspace : Workspace
	 */
	private Workspace getWorkspaceResponse(RoleDto role) {
		Workspace workspace = new Workspace();
		workspace.setSectionName(SectionName.DISPLAY.value());
		Set<WorkSpaceDto> workspaceDtoList = role.getWorkSpace();
		List<WorkspaceList> workspaceRespList = new ArrayList<>();
		for (WorkSpaceDto worksapceDto : workspaceDtoList) {
			WorkspaceList workspaceList = new WorkspaceList();
			workspaceList.setWorkspaceId(worksapceDto.getWorkSpaceId());
			workspaceList.setWorkspaceName(worksapceDto.getWorkSpaceName());
			Set<GlobalProfileDto> globalProfileDtoList = role.getGlobalProfile();
			if (!globalProfileDtoList.isEmpty()) {
				for (GlobalProfileDto globalProfileDto : globalProfileDtoList) {
					if (globalProfileDto.getWorkSpace().getWorkSpaceId() == worksapceDto.getWorkSpaceId()) {
						workspaceList.setDefaultModule(true);
						break;
					}
				}

			}
			workspaceRespList.add(workspaceList);
		}
		workspace.setWorkspaceList(workspaceRespList);
		return workspace;

	}

	/**
	 * @param role : RoleDto
	 * @return Helper : Helper
	 * @throws CustomException :CustomException
	 */
	private Helper getHelperResponse(RoleDto role) throws CustomException {
		Helper helper = new Helper();
		helper.setSectionName(SectionName.HELPER.value());
		Set<HelperGlobalSettingMappingDto> helperGlobalSettingMappingDto = role.getHelperGlobalSettingMapping();
		Map<Long, HelperList> helperMap = new HashedMap<>();
		List<HelperDto> helperDtoList = helperDAO.getHelperList();
		for (HelperDto helperDto : helperDtoList) {
			HelperList helperListObj = new HelperList();
			helperListObj.setHelperId(helperDto.getHelperId());
			helperListObj.setHelperName(helperDto.getHelperName());
			helperListObj.setModuleSpace(helperDto.getModuleOrWorkspace());
			for (HelperGlobalSettingMappingDto helperGlobalSettingsDto : helperGlobalSettingMappingDto) {
				if (helperDto.getHelperId() == helperGlobalSettingsDto.getHelper().getHelperId()) {
					helperListObj.setCheck(true);
				}
			}
			helperMap.put(helperListObj.getHelperId(), helperListObj);
		}

		List<HelperList> helperList = new ArrayList<>(helperMap.values());
		helper.setHelperList(helperList);
		return helper;
	}

	/**
	 * @param role : RoleDto
	 * @return PrintOption : PrintOption
	 */
	private PrintOption getPrintOptionResponse(RoleDto role) {
		PrintOption printOption = new PrintOption();
		printOption.setSectionName(SectionName.PRINTOPTION.value());
		Map<Long, PrintOptionList> printOptionMap = new HashedMap<>();
		Set<PrintGlobalSettingMappingDto> printGlobleOptionDtoList = role.getPrintGlobalSettingMapping();
		List<PrintOptionDto> printOptionDtoList = printOptionDao.getPrintOptionList();
		for (PrintOptionDto printOptionDto : printOptionDtoList) {
			PrintOptionList printOptionListObj = new PrintOptionList();
			printOptionListObj.setPrintOptionId(printOptionDto.getPrintOptionId());
			printOptionListObj.setPrintAdditionalOption(printOptionDto.getPrintAdditionalInfo());
			for (PrintGlobalSettingMappingDto printGlobleOptionDto : printGlobleOptionDtoList) {
				if (printOptionDto.getPrintOptionId() == printGlobleOptionDto.getPrintOption().getPrintOptionId()) {
					printOptionListObj.setSelected(true);
				}
			}
			printOptionMap.put(printOptionListObj.getPrintOptionId(), printOptionListObj);
		}
		List<PrintOptionList> printOptionList = new ArrayList<>(printOptionMap.values());
		printOption.setPrintOptionList(printOptionList);
		return printOption;

	}

	/**
	 * @param role : RoleDto
	 * @return PersonalTag : PersonalTag
	 */
	private PersonalTag getPersonalTagResponse(RoleDto role) {

		PersonalTag personalTag = new PersonalTag();
		personalTag.setSectionName(SectionName.PERSONALTAG.value());
		List<PersonalTagList> personalTagObjList = new ArrayList<>();
		Set<PersonalTagGlobalSettingMappingDto> personalTagGlobalSettingMappingDtoList = role
				.getPersonalTagGlobalSettingMapping();
		for (PersonalTagGlobalSettingMappingDto personalTagGlobalSettingMappingDto : personalTagGlobalSettingMappingDtoList) {
			PersonalTagList personalTagList = new PersonalTagList();
			PersonalTagDto personalTagDto = personalTagGlobalSettingMappingDto.getPersonalTag();
			PersonalLevelDto personalLevelDto = personalTagGlobalSettingMappingDto.getPersonalLevel();
			personalTagList.setTag(personalTagDto.getTagName());
			personalTagList.setTagLevel(personalLevelDto.getLevelName());
			personalTagList.setTitleName(personalTagGlobalSettingMappingDto.getTitleName());
			
			//newly added line 
			personalTagList.setPersonalTagGlobalSettingMappingId(personalTagGlobalSettingMappingDto.getPersonalTagGlobalSettingMappingId());
			personalTagObjList.add(personalTagList);
		}
		personalTag.setPersonalTagList(personalTagObjList);
		return personalTag;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = CustomException.class)
	public UpdaterSettingsResponse updateSettingsDetails(SettingsRequest settingsRequest, String role, String userId)
			throws CustomException {
		RoleDto roleDto = roleDao.getRole(role);

		ModuleDto moduleDto = moduleDao.getModule(settingsRequest.getDefaultModule(), roleDto.getRoleId());
		WorkSpaceDto workSpaceDto = workpaceDao.getWorkSpace(settingsRequest.getDefaultWorkspace(),
				roleDto.getRoleId());
		moduleDto.setModuleName(settingsRequest.getDefaultModule());
		workSpaceDto.setWorkSpaceName(settingsRequest.getDefaultWorkspace());

		GlobalProfileDto globalProfileDto = new GlobalProfileDto();
		globalProfileDto.setModule(moduleDto);
		globalProfileDto.setWorkSpace(workSpaceDto);
		globalProfileDto.setRole(roleDto);
		globalProfileDto.setAlertLevel(settingsRequest.getNotificationAlertLevel());
		globalProfileDto.setFontSize(settingsRequest.getFontSize());

		PrintOptionDto printOptionDto = printOptionDao.getPrintOption(settingsRequest.getPrintAdditionalOption());

		PrintGlobalSettingMappingDto printGlobalSettingMappingDto = new PrintGlobalSettingMappingDto();
		printGlobalSettingMappingDto.setPrintOption(printOptionDto);
		printGlobalSettingMappingDto.setRole(roleDto);
		/*if (CollectionUtils.isNotEmpty(settingsRequest.getHelperList())) {
			helperGlobalSettingMappingDao.removeHelperGlobalMapping(roleDto.getRoleId());
		}*/
		for (HelperList helperList : settingsRequest.getHelperList()) {
			HelperGlobalSettingMappingDto helperGlobalSettingMappingDto = new HelperGlobalSettingMappingDto();
			HelperDto helperDto = helperDAO.getHelperDetails(helperList.getHelperName(), helperList.getModuleSpace());
			helperGlobalSettingMappingDto.setHelper(helperDto);
			helperGlobalSettingMappingDto.setRole(roleDto);
			helperGlobalSettingMappingDto.setCheck(helperList.isCheck());

			helperGlobalSettingMappingDao.updateHelperGlobalSettingMapping(helperGlobalSettingMappingDto);
		}

		for (PersonalTagList personalTagList : settingsRequest.getPersonalTagList()) {
			PersonalTagGlobalSettingMappingDto dto = new PersonalTagGlobalSettingMappingDto();
			PersonalTagDto personalTagDto = personalTagDAO.getPersonalTag(personalTagList.getTag());
			PersonalLevelDto personalLevelDto = personalLevelDao.getPersonalLevel(personalTagList.getTagLevel());
			dto.setPersonalLevel(personalLevelDto);
			dto.setPersonalTag(personalTagDto);
			dto.setRole(roleDto);
			dto.setTitleName(personalTagList.getTitleName());
			personalTagGlobalSettingMappingDAO.updatePersonalTagGlobalSettingMapping(dto);

		}
		if (!CollectionUtils.isEmpty(settingsRequest.getNotificationFilterRequest())) {
		  notificationUtils.updateGlobalNotificaticionFilterSettings(settingsRequest.getNotificationFilterRequest(), role);
		}
		UpdaterSettingsResponse updaterSettingsResponse = new UpdaterSettingsResponse();

		printGlobalSettingMappingDao.updatePrintGlobalSettingMappingDetails(printGlobalSettingMappingDto);
		globalProfileDAO.updateGlobalProfile(globalProfileDto);
		updaterSettingsResponse.setDescription(SettingsConstants.UPDATE_SUCCESS);
		return updaterSettingsResponse;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public RoleDetail getRoleList() throws CustomException {
		List<RoleDto> roleDtoList = roleDao.getRoleList();
		List<Role> roleList = new ArrayList<>();
		RoleDetail roleDetail = new RoleDetail();
		for (RoleDto roleDto : roleDtoList) {
			Role role = new Role();
			role.setRoleId(roleDto.getRoleId());
			role.setRoleName(roleDto.getRoleName());
			roleList.add(role);
		}
		roleDetail.setRole(roleList);
		return roleDetail;
	}

}
