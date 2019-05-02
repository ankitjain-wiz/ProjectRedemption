package org.epo.cms.edfs.services.settings.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.util.PropertyFileReader;
import org.epo.cms.edfs.services.dossierpersistence.entity.PrintUserSettingMapping;
import org.epo.cms.edfs.services.settings.beans.CaseSample;
import org.epo.cms.edfs.services.settings.beans.Helper;
import org.epo.cms.edfs.services.settings.beans.HelperList;
import org.epo.cms.edfs.services.settings.beans.Module;
import org.epo.cms.edfs.services.settings.beans.ModuleList;
import org.epo.cms.edfs.services.settings.beans.Notification;
import org.epo.cms.edfs.services.settings.beans.NotificationFilter;
import org.epo.cms.edfs.services.settings.beans.NotificationFilterReqBean;
import org.epo.cms.edfs.services.settings.beans.PersonalTag;
import org.epo.cms.edfs.services.settings.beans.PersonalTagList;
import org.epo.cms.edfs.services.settings.beans.PrintOption;
import org.epo.cms.edfs.services.settings.beans.PrintOptionList;
import org.epo.cms.edfs.services.settings.beans.RoleSettingResponse;
import org.epo.cms.edfs.services.settings.beans.SettingsRequest;
import org.epo.cms.edfs.services.settings.beans.TimeLimit;
import org.epo.cms.edfs.services.settings.beans.UpdaterSettingsResponse;
import org.epo.cms.edfs.services.settings.beans.Workspace;
import org.epo.cms.edfs.services.settings.beans.WorkspaceList;
import org.epo.cms.edfs.services.settings.dao.GlobalProfileDao;
import org.epo.cms.edfs.services.settings.dao.HelperDao;
import org.epo.cms.edfs.services.settings.dao.HelperUserSettingMappingDao;
import org.epo.cms.edfs.services.settings.dao.ModuleDao;
import org.epo.cms.edfs.services.settings.dao.PersonalLevelDao;
import org.epo.cms.edfs.services.settings.dao.PersonalTagDao;
import org.epo.cms.edfs.services.settings.dao.PersonalTagUserSettingMappingDao;
import org.epo.cms.edfs.services.settings.dao.PrintOptionDao;
import org.epo.cms.edfs.services.settings.dao.PrintUserSettingMappingDao;
import org.epo.cms.edfs.services.settings.dao.RoleDao;
import org.epo.cms.edfs.services.settings.dao.UserProfileDao;
import org.epo.cms.edfs.services.settings.dao.WorkspaceDao;
import org.epo.cms.edfs.services.settings.dto.GlobalProfileDto;
import org.epo.cms.edfs.services.settings.dto.HelperDto;
import org.epo.cms.edfs.services.settings.dto.HelperGlobalSettingMappingDto;
import org.epo.cms.edfs.services.settings.dto.HelperUserSettingMappingDto;
import org.epo.cms.edfs.services.settings.dto.ModuleDto;
import org.epo.cms.edfs.services.settings.dto.PersonalLevelDto;
import org.epo.cms.edfs.services.settings.dto.PersonalTagDto;
import org.epo.cms.edfs.services.settings.dto.PersonalTagGlobalSettingMappingDto;
import org.epo.cms.edfs.services.settings.dto.PersonalTagUserSettingMappingDto;
import org.epo.cms.edfs.services.settings.dto.PrintGlobalSettingMappingDto;
import org.epo.cms.edfs.services.settings.dto.PrintOptionDto;
import org.epo.cms.edfs.services.settings.dto.PrintUserSettingMappingDto;
import org.epo.cms.edfs.services.settings.dto.RoleDto;
import org.epo.cms.edfs.services.settings.dto.UserProfileDto;
import org.epo.cms.edfs.services.settings.dto.WorkSpaceDto;
import org.epo.cms.edfs.services.settings.enums.SectionName;
import org.epo.cms.edfs.services.settings.service.UserSettingsService;
import org.epo.cms.edfs.services.settings.utils.NotificationUtils;
import org.epo.cms.edfs.services.settings.utils.SettingsConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author dinagar
 *
 */
@Service
public class UserSettingsServiceImpl implements UserSettingsService {

	private static final Logger LOGGER = LogManager.getLogger(UserSettingsServiceImpl.class);

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
	private UserProfileDao userProfileDAO;

	@Autowired
	private PrintUserSettingMappingDao printUserSettingMappingDao;

	@Autowired
	private HelperUserSettingMappingDao helperUserSettingMappingDao;

	@Autowired
	private PersonalTagUserSettingMappingDao personalTagUserSettingMappingDAO;

	@Autowired
	private HelperDao helperDao;

	@Autowired
	protected PropertyFileReader propertyFileReader;

	@Autowired
	private GlobalProfileDao globalProfileDAO;

	@Autowired
	private NotificationUtils notificationUtils;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(readOnly = true)
	public RoleSettingResponse getUserSettingsDetails(String role, String userId) throws CustomException {
		LOGGER.info("user service call..");
		RoleDto roleObj = roleDao.getRole(role, true);
		RoleSettingResponse roleSettingsResponse = new RoleSettingResponse();

		Module module = getModuleUserResponse(roleObj, userId);

		roleSettingsResponse.setModule(module);

		Workspace workspace = getWorkspaceUserResponse(roleObj, userId);

		Helper helper = getHelperUserResponse(roleObj, userId);

		PrintOption printOption = getPrintOptionUserResponse(roleObj, userId);

		PersonalTag personalTag = getPersonalTagUserResponse(roleObj, userId);

		int fontSize = getFontSize(roleObj, userId);
		roleSettingsResponse.setFontSize(fontSize);

		NotificationFilter notificationFilter = notificationUtils.getNotificationFilter();

		String notificationAlertLevel = notificationAlertLevel(module, workspace, userId, roleObj);
		if (null == notificationAlertLevel)
			notificationAlertLevel = "";

		Notification notification = setNotificationResponse(notificationFilter, notificationAlertLevel);

		roleSettingsResponse.setHelper(helper);
		roleSettingsResponse.setWorkspace(workspace);
		roleSettingsResponse.setPrintOption(printOption);
		roleSettingsResponse.setPersonalTag(personalTag);
		roleSettingsResponse.setNotification(notification);
		
		checkCaseSampleSection(roleObj, roleSettingsResponse);
		checkTimeLimitSection(roleObj, roleSettingsResponse);



		LOGGER.debug("final response datails ", roleSettingsResponse);
		return roleSettingsResponse;

	}

	/**
	 * @param notificationFilter
	 *            : NotificationFilter
	 * @param notificationAlertLevel
	 *            : String
	 * @return Notification : Notification
	 */
	private Notification setNotificationResponse(NotificationFilter notificationFilter, String notificationAlertLevel) {
		Notification notification = new Notification();
		notification.setNotificationAlertLevel(notificationAlertLevel);
		notification.setNotificationFilter(notificationFilter);
		notification.setSectionName(SectionName.NOTIFICATION.value());
		return notification;
	}

	/**
	 * Method to get Font Size from User Profile
	 * 
	 * @param role:
	 *            RoleDto
	 * @param userId
	 *            : String
	 * @return int : int
	 * @throws CustomException:
	 *             CustomException
	 */
	private int getFontSize(RoleDto role, String userId) throws CustomException {
		int fontSize = 0;

		List<UserProfileDto> userProfileDtoList = userProfileDAO.getUserProfileList(userId);
		if (CollectionUtils.isNotEmpty(userProfileDtoList)) {
			for (UserProfileDto userProfileDto : userProfileDtoList) {
				fontSize = userProfileDto.getFontSize();
				break;
			}
		} else {
			fontSize = getGlobalFontSize(role);
		}

		return fontSize;
	}

	/**
	 * @param module
	 *            : Module
	 * @param workspace:
	 *            Workspace
	 * @param userId:
	 *            String
	 * @param roleObj:
	 *            RoleDto
	 * @return String : String
	 * @throws CustomException:
	 *             CustomException
	 */
	private String notificationAlertLevel(Module module, Workspace workspace, String userId, RoleDto roleObj)
			throws CustomException {
		String notificationAlertLevel;
		List<UserProfileDto> userProfileDtoList = userProfileDAO.getUserProfileList(userId);
		if (CollectionUtils.isNotEmpty(userProfileDtoList)) {
			notificationAlertLevel = userProfileDAO.getUserProfileAlertLevel(module, workspace, userId);
		} else {
			notificationAlertLevel = globalProfileDAO.getGlobalProfileAlertLevel(module, workspace, roleObj);
		}
		return notificationAlertLevel;
	}

	/**
	 * @param role
	 *            : RoleDto
	 * @param userId:
	 *            String
	 * @return Module : Module
	 */
	private Module getModuleUserResponse(RoleDto role, String userId) {
		Module module = new Module();
		//module.setResetToDefault(true);
		module.setResetToDefault(false);
		module.setSectionName(SectionName.DISPLAY.value());
		List<ModuleList> moduleList = new ArrayList<>();
		Set<ModuleDto> moduleDtoList = role.getModule();
		// moduleDao.getModule(module, roleId)
		boolean userSettings = userProfileDAO.checkUserModuleWorkspace(userId);
		for (ModuleDto moduleDto : moduleDtoList) {
			ModuleList moduleListObj = new ModuleList();
			moduleListObj.setModuleId(moduleDto.getModuleId());
			moduleListObj.setModuleName(moduleDto.getModuleName());
			Set<UserProfileDto> userProfileDtoList = moduleDto.getUserProfile();
			if (!userProfileDtoList.isEmpty() && userSettings) {
				for (UserProfileDto userProfileDto : userProfileDtoList) {
					if (userProfileDto.isActive() && userProfileDto.getModule().getModuleId() == moduleDto.getModuleId()
							&& userProfileDto.getUserId().equalsIgnoreCase(userId)) {
						moduleListObj.setDefaultModule(true);
						userSettings = true;
						module.setResetToDefault(true);
						break;
					}
				}
			}
			if (!userSettings) {
				globalModule(role, module, moduleDto, moduleListObj);
			}
			moduleList.add(moduleListObj);
		}
		module.setModuleList(moduleList);
		return module;
	}

	/**
	 * @param role
	 *            : RoleDto
	 * @param module
	 *            : Module
	 * @param moduleDto
	 *            : ModuleDto
	 * @param moduleListObj:
	 *            ModuleList
	 */
	private void globalModule(RoleDto role, Module module, ModuleDto moduleDto, ModuleList moduleListObj) {
		module.setResetToDefault(false);
		Set<GlobalProfileDto> globalProfileDtos = moduleDto.getGlobalProfile();
		for (GlobalProfileDto globalProfileDto : globalProfileDtos) {
			if (role.getRoleId() == globalProfileDto.getRole().getRoleId()
					&& moduleListObj.getModuleId() == globalProfileDto.getModule().getModuleId()) {
				moduleListObj.setDefaultModule(true);
			}
		}
	}

	/**
	 * @param role:
	 *            RoleDto
	 * @param userId:
	 *            String
	 * @return Workspace : Workspace
	 */
	private Workspace getWorkspaceUserResponse(RoleDto role, String userId) {
		Workspace workspace = new Workspace();
		//workspace.setResetToDefault(true);
		workspace.setResetToDefault(false);
		workspace.setSectionName(SectionName.DISPLAY.value());
		boolean userSettings = userProfileDAO.checkUserModuleWorkspace(userId);
		Set<WorkSpaceDto> workspaceDtoList = role.getWorkSpace();
		List<WorkspaceList> workspaceRespList = new ArrayList<>();
		for (WorkSpaceDto worksapceDto : workspaceDtoList) {
			WorkspaceList workspaceList = new WorkspaceList();
			workspaceList.setWorkspaceId(worksapceDto.getWorkSpaceId());
			workspaceList.setWorkspaceName(worksapceDto.getWorkSpaceName());
			Set<UserProfileDto> userProfileDtoList = worksapceDto.getUserProfile();
			if (!userProfileDtoList.isEmpty() && userSettings) {
				for (UserProfileDto userProfileDto : userProfileDtoList) {
					if (userProfileDto.getWorkSpace().getWorkSpaceId() == worksapceDto.getWorkSpaceId()
							&& userProfileDto.isActive() && userProfileDto.getUserId().equalsIgnoreCase(userId)) {
						workspaceList.setDefaultModule(true);
						userSettings = true;
						workspace.setResetToDefault(true);
						break;
					}
				}
			}
			if (!userSettings) {
				golbalWorkspace(role, workspace, worksapceDto, workspaceList);
			}
			workspaceRespList.add(workspaceList);
		}
		workspace.setWorkspaceList(workspaceRespList);
		return workspace;

	}

	/**
	 * @param role:RoleDto
	 * @param workspace
	 *            : Workspace
	 * @param worksapceDto
	 *            : WorkSpaceDto
	 * @param workspaceList
	 *            : WorkspaceList
	 */
	private void golbalWorkspace(RoleDto role, Workspace workspace, WorkSpaceDto worksapceDto,
			WorkspaceList workspaceList) {
		workspace.setResetToDefault(false);
		Set<GlobalProfileDto> globalProfileDtos = worksapceDto.getGlobalProfile();
		for (GlobalProfileDto globalProfileDto : globalProfileDtos) {
			if (role.getRoleId() == globalProfileDto.getRole().getRoleId()
					&& workspaceList.getWorkspaceId() == globalProfileDto.getWorkSpace().getWorkSpaceId()) {
				workspaceList.setDefaultModule(true);
			}

		}
	}

	/**
	 * @param role:
	 *            RoleDto
	 * @param userId:
	 *            String
	 * @return Helper : Helper
	 * @throws CustomException:
	 *             CustomException
	 */
	private Helper getHelperUserResponse(RoleDto role, String userId) throws CustomException {
		Helper helper = new Helper();
		helper.setResetToDefault(false);

		helper.setSectionName(SectionName.HELPER.value());
		Map<Long, HelperList> helperMap = new HashedMap<>();
		Map<Long, HelperList> clickedHelper = new HashMap<>();
		List<HelperDto> helperDtoList = helperDao.getHelperList();

		List<HelperUserSettingMappingDto> helperUserSettingMappingDtoList = helperUserSettingMappingDao
				.userHelperList(userId);
		List<HelperUserSettingMappingDto> helperActiveUserSettings = helperUserSettingMappingDtoList.stream()
				.filter(userActiveHelper -> userActiveHelper.isActive()).collect(Collectors.toList());
		List<HelperUserSettingMappingDto> helperCheckedUserSettings = helperUserSettingMappingDtoList.stream()
				.filter(checkedHelper -> !checkedHelper.isChecked() && checkedHelper.isActive())
				.collect(Collectors.toList());

		for (HelperDto helperDto : helperDtoList) {
			HelperList helperListObj = new HelperList();
			helperListObj.setHelperId(helperDto.getHelperId());
			helperListObj.setHelperName(helperDto.getHelperName());
			helperListObj.setModuleSpace(helperDto.getModuleOrWorkspace());
			Set<HelperGlobalSettingMappingDto> helperGlobalSettingMappingDtos = role.getHelperGlobalSettingMapping();
			if (CollectionUtils.isNotEmpty(helperActiveUserSettings)) {
				helper.setResetToDefault(true);
				for (HelperUserSettingMappingDto helperUserSettingMapping : helperActiveUserSettings) {
					if (helperUserSettingMapping.getUserId().equalsIgnoreCase(userId)
							&& helperUserSettingMapping.getHelper().getHelperId() == helperListObj.getHelperId()) {
						helperListObj.setCheck(true);
					}
				}
			}

			globalHelper(role, helperListObj, helperGlobalSettingMappingDtos);

			helperMap.put(helperListObj.getHelperId(), helperListObj);

		}

		HelperList helperListObj;
		if (CollectionUtils.isNotEmpty(helperCheckedUserSettings)) {
			for (HelperUserSettingMappingDto helperUserSettingMapping : helperCheckedUserSettings) {
				helperListObj = new HelperList();
				helperListObj.setCheck(false);
				helperListObj.setHelperId(helperUserSettingMapping.getHelper().getHelperId());
				helperListObj.setHelperName(helperUserSettingMapping.getHelper().getHelperName());
				helperListObj.setModuleSpace(helperUserSettingMapping.getHelper().getModuleOrWorkspace());
				clickedHelper.put(helperListObj.getHelperId(), helperListObj);
			}

		}

		clickedHelper.forEach((key, helperList) -> {
			if (!helperList.isCheck()) {
				helperMap.get(helperList.getHelperId()).setCheck(false);
			}
		});

		List<HelperList> helperLists = new ArrayList<>(helperMap.values());

		helper.setHelperList(helperLists);
		return helper;
	}

	/**
	 * @param role:
	 *            RoleDto
	 * @param helper:
	 *            Helper
	 * @param helperListObj
	 *            : HelperList
	 * @param helperGlobalSettingMappingDtos:
	 *            Set<HelperGlobalSettingMappingDto>
	 */
	private void globalHelper(RoleDto role, HelperList helperListObj,
			Set<HelperGlobalSettingMappingDto> helperGlobalSettingMappingDtos) {

		for (HelperGlobalSettingMappingDto helperGlobalSettingMapping : helperGlobalSettingMappingDtos) {
			if (role.getRoleId() == helperGlobalSettingMapping.getRole().getRoleId()
					&& helperListObj.getHelperId() == helperGlobalSettingMapping.getHelper().getHelperId()) {
				helperListObj.setCheck(true);
			}
		}
	}

	/**
	 * @param role:
	 *            RoleDto
	 * @param userId
	 *            : String
	 * @return PrintOption : PrintOption
	 */
	private PrintOption getPrintOptionUserResponse(RoleDto role, String userId) {
		PrintOption printOption = new PrintOption();
		printOption.setResetToDefault(true);
		printOption.setSectionName(SectionName.PRINTOPTION.value());
		boolean userSettings = false;
		List<PrintUserSettingMapping> printUserSettingMappingList = printUserSettingMappingDao
				.getPrintUserSettingMapping(userId);
		if (CollectionUtils.isNotEmpty(printUserSettingMappingList)) {
			userSettings = true;
		}
		Map<Long, PrintOptionList> printOptionMap = new HashedMap<>();
		List<PrintOptionDto> printOptionDtoList = printOptionDao.getPrintOptionList();
		PrintOptionList printOptionListObj;
		for (PrintOptionDto printOptionDto : printOptionDtoList) {
			printOptionListObj = new PrintOptionList();
			printOptionListObj.setPrintOptionId(printOptionDto.getPrintOptionId());
			printOptionListObj.setPrintAdditionalOption(printOptionDto.getPrintAdditionalInfo());
			Set<PrintGlobalSettingMappingDto> printGlobalSettingMappingDtos = role.getPrintGlobalSettingMapping();

			if (CollectionUtils.isNotEmpty(printUserSettingMappingList) && userSettings) {
				for (PrintUserSettingMapping printUserSettingMapping : printUserSettingMappingList) {
					if (printUserSettingMapping.getActive()
							&& printUserSettingMapping.getUserId().equalsIgnoreCase(userId) && printUserSettingMapping
									.getPrintOption().getPrintOptionId() == printOptionDto.getPrintOptionId()) {
						printOptionListObj.setSelected(true);
						userSettings = true;
					}
				}
			}
			if (!userSettings) {
				globalPrintOption(role, printOption, printOptionListObj, printGlobalSettingMappingDtos);
			}
			printOptionMap.put(printOptionListObj.getPrintOptionId(), printOptionListObj);
		}

		List<PrintOptionList> printOptionList = new ArrayList<>(printOptionMap.values());
		printOption.setPrintOptionList(printOptionList);
		return printOption;

	}

	/**
	 * @param role:
	 *            RoleDto
	 * @param printOption
	 *            : PrintOption
	 * @param printOptionListObj
	 *            : PrintOptionList
	 * @param printGlobalSettingMappingDtos:
	 *            Set<PrintGlobalSettingMappingDto>
	 */
	private void globalPrintOption(RoleDto role, PrintOption printOption, PrintOptionList printOptionListObj,
			Set<PrintGlobalSettingMappingDto> printGlobalSettingMappingDtos) {
		printOption.setResetToDefault(false);
		for (PrintGlobalSettingMappingDto printGlobalSettingMappingDto : printGlobalSettingMappingDtos) {
			if (role.getRoleId() == printGlobalSettingMappingDto.getRole().getRoleId() && printGlobalSettingMappingDto
					.getPrintOption().getPrintOptionId() == printOptionListObj.getPrintOptionId()) {
				printOptionListObj.setSelected(true);
			}
		}
	}

	/**
	 * @param role:
	 *            RoleDto
	 * @param userId
	 *            : String
	 * @return PersonalTag : PersonalTag
	 */
	private PersonalTag getPersonalTagUserResponse(RoleDto role, String userId) {
		PersonalTag personalTag = new PersonalTag();
		personalTag.setResetToDefault(false);
		personalTag.setSectionName(SectionName.PERSONALTAG.value());
		List<PersonalTagList> personalTagUserList = new ArrayList<>();
		List<PersonalTagList> personalTagGlobalList = new ArrayList<>();
		Set<PersonalTagGlobalSettingMappingDto> personalTagGlobalSettingMappingDtoList = role
				.getPersonalTagGlobalSettingMapping();
		List<PersonalTagUserSettingMappingDto> personalTagUserSettingMappingList = personalTagUserSettingMappingDAO
				.getPersonalTagUserSettingMapping(userId);

		PersonalTagList personalTagList;
		if (CollectionUtils.isNotEmpty(personalTagUserSettingMappingList)) {
			for (PersonalTagUserSettingMappingDto personalTagUserSettingMappingDto : personalTagUserSettingMappingList) {
				personalTag.setResetToDefault(true);
				personalTagList = new PersonalTagList();
				personalTagUserSettingMappingDto.getPersonalLevel().getLevelName();

				// Code for Dummy data RETRIEVE at Start
				// personalTagUserSettingMappingDto.getTitleName().equals("NEGATIVE")
				// Negative Data Retrieve
				if ("NEGATIVE".equals(personalTagUserSettingMappingDto.getTitleName())) {
					personalTagList.setTag("");
					personalTagList.setTitleName("");
					personalTagList.setTagLevel("");
					personalTagList.setPersonalTagGlobalSettingMappingId(-1);
				} else {
					personalTagList.setTag(personalTagUserSettingMappingDto.getPersonalTag().getTagName());
					personalTagList.setTitleName(personalTagUserSettingMappingDto.getTitleName());
					personalTagList.setTagLevel(personalTagUserSettingMappingDto.getPersonalLevel().getLevelName());
					personalTagList.setPersonalTagGlobalSettingMappingId(
							personalTagUserSettingMappingDto.getPersonalTagGlobalSettingMappingId());
				}

				personalTagUserList.add(personalTagList);
			}
		} else if (CollectionUtils.isNotEmpty(personalTagGlobalSettingMappingDtoList)) {
			for (PersonalTagGlobalSettingMappingDto personalTagGlobalSettingMappingDto : personalTagGlobalSettingMappingDtoList) {
				personalTagList = new PersonalTagList();
				personalTagList.setTag(personalTagGlobalSettingMappingDto.getPersonalTag().getTagName());
				personalTagList.setTitleName(personalTagGlobalSettingMappingDto.getTitleName());
				personalTagList.setTagLevel(personalTagGlobalSettingMappingDto.getPersonalLevel().getLevelName());
				personalTagList.setPersonalTagGlobalSettingMappingId(
						personalTagGlobalSettingMappingDto.getPersonalTagGlobalSettingMappingId());
				personalTagGlobalList.add(personalTagList);
			}
		}

		personalTagUserList.forEach(personaluserList -> {
			if (personaluserList.getPersonalTagGlobalSettingMappingId() != 0) {
				personalTagGlobalList.forEach(personalGlobalList -> {
					if (personalGlobalList.getPersonalTagGlobalSettingMappingId() == personaluserList
							.getPersonalTagGlobalSettingMappingId()) {
						personalTagGlobalList.remove(personalGlobalList);
					}
				});
			}
		});
		personalTagUserList.addAll(personalTagGlobalList);

		personalTag.setPersonalTagList(personalTagUserList);
		return personalTag;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = CustomException.class)
	public UpdaterSettingsResponse updateUserSettingsDetails(SettingsRequest settingsRequest, String role,
			String userId) throws CustomException {

		RoleDto roleObj = roleDao.getRole(role);
		List<String> strList;
		strList = settingsRequest.getSectionNameList();

		if (CollectionUtils.isNotEmpty(settingsRequest.getSectionNameList())
				&& strList.contains(SectionName.DISPLAY.value())) {
			userProfileDAO.resetToDefaultUserProfile(userId, true);
		} else {
			if (!StringUtils.isEmpty(settingsRequest.getDefaultModule())
					&& !StringUtils.isEmpty(settingsRequest.getDefaultWorkspace())
					&& !StringUtils.isEmpty(Integer.toString(settingsRequest.getFontSize()))) {

				updateUserProfile(settingsRequest.getDefaultModule(), settingsRequest.getDefaultWorkspace(),
						settingsRequest.getNotificationAlertLevel(), userId, roleObj.getRoleId(),
						settingsRequest.getFontSize());

			}
		}

		if (CollectionUtils.isNotEmpty(settingsRequest.getSectionNameList())
				&& strList.contains(SectionName.HELPER.value())) {
			helperUserSettingMappingDao.resetHelperUserSettingMapping(userId);
		} else {
			if (!CollectionUtils.isEmpty(settingsRequest.getHelperList())) {
				updateHelperSettings(settingsRequest.getHelperList(), userId);
			}

		}

		if (CollectionUtils.isNotEmpty(settingsRequest.getSectionNameList())
				&& strList.contains(SectionName.PRINTOPTION.value())) {
			printUserSettingMappingDao.resetPrintUserSettings(userId);
		} else {
			if (!StringUtils.isEmpty(settingsRequest.getPrintAdditionalOption())) {
				updatePrintOption(settingsRequest.getPrintAdditionalOption(), userId);
			}
		}

		if (CollectionUtils.isNotEmpty(settingsRequest.getSectionNameList())
				&& strList.contains(SectionName.PERSONALTAG.value())) {
			personalTagUserSettingMappingDAO.restPersonalTagUserSettingMapping(userId);
		} else {
			if (!CollectionUtils.isEmpty(settingsRequest.getPersonalTagList())) {
				updatePersonalSettings(settingsRequest.getPersonalTagList(), userId, role);
			}
		}

		if (CollectionUtils.isNotEmpty(settingsRequest.getSectionNameList())
				&& strList.contains(SectionName.NOTIFICATION.value())) {
			NotificationFilterReqBean notificationFilterReqBean = new NotificationFilterReqBean();
			notificationFilterReqBean.setSectionName(SectionName.NOTIFICATION.value());

			notificationUtils.updateUserNotificaticionFilterSettings(notificationFilterReqBean);
		} else {
			if (!CollectionUtils.isEmpty(settingsRequest.getNotificationFilterRequest())) {
				NotificationFilterReqBean notificationFilterReqBean = new NotificationFilterReqBean();
				notificationFilterReqBean
						.setNotificationFilterRequestList(settingsRequest.getNotificationFilterRequest());
				notificationUtils.updateUserNotificaticionFilterSettings(notificationFilterReqBean);

			}
		}

		UpdaterSettingsResponse updaterSettingsResponse = new UpdaterSettingsResponse();
		updaterSettingsResponse.setDescription(SettingsConstants.UPDATE_SUCCESS);
		return updaterSettingsResponse;
	}

	/**
	 * @param personalTagLists:
	 *            List<PersonalTagList>
	 * @param userId
	 *            : String
	 * @param role
	 *            : String
	 * @throws CustomException:
	 *             CustomException
	 */
	private void updatePersonalSettings(List<PersonalTagList> personalTagLists, String userId, String role)
			throws CustomException {
		PersonalTagUserSettingMappingDto dto = new PersonalTagUserSettingMappingDto();

		if (personalTagUserSettingMappingDAO.checkEmptyPersonalTagUserSettingMapping(userId)) {
			RoleDto roleObj = roleDao.getRole(role, true);
			PersonalTag personalTag = getGlobalPersonalTagResponse(roleObj);
			List<PersonalTagList> personalTagGlobalLists = personalTag.getPersonalTagList();

			for (PersonalTagList personalTagList : personalTagGlobalLists) {
				PersonalTagDto personalTagDto = personalTagDAO.getPersonalTag(personalTagList.getTag());
				PersonalLevelDto personalLevelDto = personalLevelDao.getPersonalLevel(personalTagList.getTagLevel());
				dto.setPersonalLevel(personalLevelDto);
				dto.setPersonalTag(personalTagDto);
				dto.setUserId(userId);
				dto.setTitleName(personalTagList.getTitleName());
				dto.setPersonalTagGlobalSettingMappingId(personalTagList.getPersonalTagGlobalSettingMappingId());
				personalTagUserSettingMappingDAO.updatePersonalTagUserSettingMapping(dto);
			}

			// Code for Dummy data insert at Start
			// Negative Data insert
			PersonalTagDto personalTagDto = personalTagDAO.getPersonalTag("shadeFour");
			PersonalLevelDto personalLevelDto = personalLevelDao.getPersonalLevel("Document");
			dto.setPersonalLevel(personalLevelDto);
			dto.setPersonalTag(personalTagDto);
			dto.setUserId(userId);
			dto.setTitleName("NEGATIVE");
			dto.setPersonalTagGlobalSettingMappingId(-1);
			personalTagUserSettingMappingDAO.updatePersonalTagUserSettingMapping(dto);

		}

		for (PersonalTagList personalTagList : personalTagLists) {
			PersonalTagDto personalTagDto = personalTagDAO.getPersonalTag(personalTagList.getTag());
			PersonalLevelDto personalLevelDto = personalLevelDao.getPersonalLevel(personalTagList.getTagLevel());
			dto.setPersonalLevel(personalLevelDto);
			dto.setPersonalTag(personalTagDto);
			dto.setUserId(userId);
			dto.setTitleName(personalTagList.getTitleName());
			dto.setPersonalTagGlobalSettingMappingId(personalTagList.getPersonalTagGlobalSettingMappingId());
			personalTagUserSettingMappingDAO.updatePersonalTagUserSettingMapping(dto);
		}

	}

	/**
	 * @param helperList
	 *            : List<HelperList>
	 * @param userId
	 *            : String
	 * @throws CustomException
	 *             : CustomException
	 */
	private void updateHelperSettings(List<HelperList> helperLists, String userId) throws CustomException {
		HelperUserSettingMappingDto helperUserSettingMappingDto = new HelperUserSettingMappingDto();

		for (HelperList helperList : helperLists) {
			HelperDto helperDto;
			helperDto = helperDao.getHelperDetails(helperList.getHelperName(), helperList.getModuleSpace());
			helperUserSettingMappingDto.setHelper(helperDto);
			helperUserSettingMappingDto.setUserId(userId);
			helperUserSettingMappingDto.setChecked(helperList.isCheck());
			helperUserSettingMappingDao.updateHelperUserSettingMapping(helperUserSettingMappingDto);
		}

	}

	/**
	 * @param printAdditionalOption:
	 *            String
	 * @param userId:
	 *            String
	 * @throws CustomException
	 *             : CustomException
	 */
	private void updatePrintOption(String printAdditionalOption, String userId) throws CustomException {
		PrintOptionDto printOptionDto;
		PrintUserSettingMappingDto printUserSettingMappingDto = new PrintUserSettingMappingDto();
		if (!StringUtils.isEmpty(printAdditionalOption)) {
			printOptionDto = printOptionDao.getPrintOption(printAdditionalOption);
			printUserSettingMappingDto.setPrintOption(printOptionDto);
			printUserSettingMappingDto.setUserId(userId);
			printUserSettingMappingDao.updatePrintUserSettingMapping(printUserSettingMappingDto);
		}
	}

	/**
	 * @param defaultModule
	 *            : String
	 * @param defaultWorkspace
	 *            : String
	 * @param notificationAlertLevel
	 *            : String
	 * @param userId
	 *            : String
	 * @param roleId
	 *            : long
	 * @param fontSize
	 *            : int
	 * @throws CustomException:
	 *             CustomException
	 * 
	 */
	private void updateUserProfile(String defaultModule, String defaultWorkspace, String notificationAlertLevel,
			String userId, long roleId, int fontSize) throws CustomException {
		ModuleDto moduleDto;
		UserProfileDto userProfileDto = new UserProfileDto();
		WorkSpaceDto workSpaceDto;
		moduleDto = moduleDao.getModule(defaultModule, roleId);
		moduleDto.setModuleName(defaultModule);
		userProfileDto.setModule(moduleDto);
		workSpaceDto = workpaceDao.getWorkSpace(defaultWorkspace, roleId);
		workSpaceDto.setWorkSpaceName(defaultWorkspace);
		userProfileDto.setWorkSpace(workSpaceDto);
		userProfileDto.setAlertLevel(notificationAlertLevel);
		userProfileDto.setUserId(userId);
		userProfileDto.setFontSize(fontSize);
		userProfileDAO.updateUserProfile(userProfileDto);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = CustomException.class)
	public RoleSettingResponse resetUserSettingsToGlobal(String sectionName, String role, String userId)
			throws CustomException {
		RoleDto roleObj = roleDao.getRole(role, true);
		RoleSettingResponse roleSettingResponse = new RoleSettingResponse();
		Module module = getModuleGlobalResponse(roleObj);
		Workspace workspace = getWorkspaceGlobalResponse(roleObj);
		Helper helper = getHelperGlobalResponse(roleObj);
		PersonalTag personalTag = getGlobalPersonalTagResponse(roleObj);
		PrintOption printOption = getGlobalPrintOptionResponse(roleObj);
		int fontSize = getGlobalFontSize(roleObj);

		NotificationFilter notificationFilter = notificationUtils.getNotificationFilter(role);

		String notificationAlertLevel = globalProfileDAO.getGlobalProfileAlertLevel(module, workspace, roleObj);
		if (null == notificationAlertLevel)
			notificationAlertLevel = "";
		Notification notification = setNotificationResponse(notificationFilter, notificationAlertLevel);

		Module moduleEmpty = new Module();
		moduleEmpty.setSectionName(SectionName.DISPLAY.value());
		moduleEmpty.setModuleList(new ArrayList<>());

		Workspace workspaceEmpty = new Workspace();
		workspaceEmpty.setSectionName(SectionName.DISPLAY.value());
		workspaceEmpty.setWorkspaceList(new ArrayList<>());

		Helper helperEmpty = new Helper();
		helperEmpty.setSectionName(SectionName.HELPER.value());
		helperEmpty.setHelperList(new ArrayList<>());

		PersonalTag personalTagEmpty = new PersonalTag();
		personalTagEmpty.setSectionName(SectionName.PERSONALTAG.value());
		personalTagEmpty.setPersonalTagList(new ArrayList<>());

		PrintOption printOptionEmpty = new PrintOption();
		printOptionEmpty.setSectionName(SectionName.PRINTOPTION.value());
		printOptionEmpty.setPrintOptionList(new ArrayList<>());

		NotificationFilter notificationFilterEmpty = new NotificationFilter();
		notificationFilterEmpty.setLow(new ArrayList<>());
		notificationFilterEmpty.setMedium(new ArrayList<>());
		String notificationAlertLevelEmpty = "";
		Notification notificationEmpty = setNotificationResponse(notificationFilterEmpty, notificationAlertLevelEmpty);

		if (SectionName.DISPLAY.value().equalsIgnoreCase(sectionName)) {

			roleSettingResponse.setHelper(helperEmpty);
			roleSettingResponse.setPersonalTag(personalTagEmpty);
			roleSettingResponse.setPrintOption(printOptionEmpty);
			roleSettingResponse.setNotification(notificationEmpty);
			roleSettingResponse.setModule(module);
			roleSettingResponse.setWorkspace(workspace);
			roleSettingResponse.setFontSize(fontSize);

		}

		if (SectionName.HELPER.value().equalsIgnoreCase(sectionName)) {

			roleSettingResponse.setModule(moduleEmpty);
			roleSettingResponse.setWorkspace(workspaceEmpty);
			roleSettingResponse.setPersonalTag(personalTagEmpty);
			roleSettingResponse.setPrintOption(printOptionEmpty);
			roleSettingResponse.setNotification(notificationEmpty);
			roleSettingResponse.setHelper(helper);

		}
		if (SectionName.PERSONALTAG.value().equalsIgnoreCase(sectionName)) {

			roleSettingResponse.setModule(moduleEmpty);
			roleSettingResponse.setWorkspace(workspaceEmpty);
			roleSettingResponse.setPrintOption(printOptionEmpty);
			roleSettingResponse.setHelper(helperEmpty);
			roleSettingResponse.setNotification(notificationEmpty);
			roleSettingResponse.setPersonalTag(personalTag);

		}
		if (SectionName.PRINTOPTION.value().equalsIgnoreCase(sectionName)) {

			roleSettingResponse.setModule(moduleEmpty);
			roleSettingResponse.setWorkspace(workspaceEmpty);
			roleSettingResponse.setHelper(helperEmpty);
			roleSettingResponse.setPersonalTag(personalTagEmpty);
			roleSettingResponse.setPrintOption(printOption);
			roleSettingResponse.setNotification(notificationEmpty);

		}

		if (SectionName.NOTIFICATION.value().equalsIgnoreCase(sectionName)) {

			roleSettingResponse.setModule(moduleEmpty);
			roleSettingResponse.setWorkspace(workspaceEmpty);
			roleSettingResponse.setHelper(helperEmpty);
			roleSettingResponse.setPersonalTag(personalTagEmpty);
			roleSettingResponse.setPrintOption(printOptionEmpty);
			roleSettingResponse.setNotification(notification);

		}

		if (StringUtils.isEmpty(sectionName) || ("All").equalsIgnoreCase(sectionName)) {
			roleSettingResponse.setModule(module);
			roleSettingResponse.setWorkspace(workspace);
			roleSettingResponse.setHelper(helper);
			roleSettingResponse.setPersonalTag(personalTag);
			roleSettingResponse.setPrintOption(printOption);
			roleSettingResponse.setNotification(notification);
			roleSettingResponse.setFontSize(fontSize);

		}

		if (SectionName.CaseSampling.value().equalsIgnoreCase(sectionName)) {
			roleSettingResponse.setModule(moduleEmpty);
			roleSettingResponse.setWorkspace(workspaceEmpty);
			roleSettingResponse.setHelper(helperEmpty);
			roleSettingResponse.setPersonalTag(personalTagEmpty);
			roleSettingResponse.setPrintOption(printOptionEmpty);
			roleSettingResponse.setNotification(notificationEmpty);

		}

		if (SectionName.TimeLimit.value().equalsIgnoreCase(sectionName)) {
			roleSettingResponse.setModule(moduleEmpty);
			roleSettingResponse.setWorkspace(workspaceEmpty);
			roleSettingResponse.setHelper(helperEmpty);
			roleSettingResponse.setPersonalTag(personalTagEmpty);
			roleSettingResponse.setPrintOption(printOptionEmpty);
			roleSettingResponse.setNotification(notificationEmpty);

		}

		checkCaseSampleSection(roleObj, roleSettingResponse);
		checkTimeLimitSection(roleObj, roleSettingResponse);

		return roleSettingResponse;
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
	 * @param role
	 *            : RoleDto
	 * @return Module : Module
	 */
	private Module getModuleGlobalResponse(RoleDto role) {

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
	 * @param role:
	 *            RoleDto
	 * @return Workspace : Workspace
	 */
	private Workspace getWorkspaceGlobalResponse(RoleDto role) {
		Workspace workspace = new Workspace();
		workspace.setSectionName(SectionName.DISPLAY.value());
		Set<WorkSpaceDto> workspaceDtoList = role.getWorkSpace();
		List<WorkspaceList> workspaceRespList = new ArrayList<>();
		for (WorkSpaceDto workspaceDto : workspaceDtoList) {
			WorkspaceList workspaceList = new WorkspaceList();
			workspaceList.setWorkspaceId(workspaceDto.getWorkSpaceId());
			workspaceList.setWorkspaceName(workspaceDto.getWorkSpaceName());
			Set<GlobalProfileDto> globalProfileDtoList = role.getGlobalProfile();
			if (!globalProfileDtoList.isEmpty()) {
				for (GlobalProfileDto globalProfileDto : globalProfileDtoList) {
					if (globalProfileDto.getWorkSpace().getWorkSpaceId() == workspaceDto.getWorkSpaceId()) {
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
	 * Method to get Global Font Size
	 * 
	 * @param role:
	 *            RoleDto
	 * @return int : int
	 */
	private int getGlobalFontSize(RoleDto role) {
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
	 * @param role:
	 *            RoleDto
	 * @return Helper : Helper
	 * @throws CustomException:
	 *             CustomException
	 */
	private Helper getHelperGlobalResponse(RoleDto role) throws CustomException {
		Helper helper = new Helper();
		helper.setSectionName(SectionName.HELPER.value());
		Set<HelperGlobalSettingMappingDto> helperGlobalSettingMappingDto = role.getHelperGlobalSettingMapping();
		Map<Long, HelperList> helperMap = new HashedMap<>();
		List<HelperDto> helperDtoList = helperDao.getHelperList();
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
	 * @param role
	 *            : RoleDto
	 * @return PersonalTag : PersonalTag
	 */
	private PersonalTag getGlobalPersonalTagResponse(RoleDto role) {

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
			personalTagList.setPersonalTagGlobalSettingMappingId(
					personalTagGlobalSettingMappingDto.getPersonalTagGlobalSettingMappingId());
			personalTagObjList.add(personalTagList);
		}
		personalTag.setPersonalTagList(personalTagObjList);
		return personalTag;

	}

	/**
	 * @param role
	 *            : RoleDto
	 * @return PrintOption : PrintOption
	 */
	private PrintOption getGlobalPrintOptionResponse(RoleDto role) {
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

}
