package org.epo.cms.edfs.services.settings.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.common.util.PropertyFileReader;
import org.epo.cms.edfs.services.dossierpersistence.entity.HelperUserSettingMapping;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalTagUserSettingMapping;
import org.epo.cms.edfs.services.dossierpersistence.entity.PrintOption;
import org.epo.cms.edfs.services.dossierpersistence.entity.PrintUserSettingMapping;
import org.epo.cms.edfs.services.dossierpersistence.entity.UserProfile;
import org.epo.cms.edfs.services.settings.beans.HelperList;
import org.epo.cms.edfs.services.settings.beans.NotificationFilter;
import org.epo.cms.edfs.services.settings.beans.NotificationFilterRequest;
import org.epo.cms.edfs.services.settings.beans.NotificationStatusResponse;
import org.epo.cms.edfs.services.settings.beans.PersonalTagList;
import org.epo.cms.edfs.services.settings.beans.RoleSettingResponse;
import org.epo.cms.edfs.services.settings.beans.SettingsRequest;
import org.epo.cms.edfs.services.settings.beans.UpdaterSettingsResponse;
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
import org.epo.cms.edfs.services.settings.utils.NotificationUtils;
import org.epo.cms.edfs.services.settings.utils.SettingsConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ UserSettingsServiceImpl.class, HttpStatus.class })
@PowerMockIgnore("javax.management.*")
public class UserSettingsServiceImplTest {

	private static final String ROLE_DESCRIPTION = null;

	@InjectMocks
	UserSettingsServiceImpl userSettingsService;

	@Mock
	private RoleDao roleDao;

	@Mock
	private GlobalProfileDao globalProfileDAO;

	@Mock
	private NotificationUtils notificationUtils;

	@Mock
	private ModuleDao moduleDao;

	@Mock
	private WorkspaceDao workpaceDao;

	@Mock
	private PrintOptionDao printOptionDao;

	@Mock
	private PersonalTagDao personalTagDAO;

	@Mock
	private PersonalLevelDao personalLevelDao;

	@Mock
	private UserProfileDao userProfileDAO;

	@Mock
	private PrintUserSettingMappingDao printUserSettingMappingDao;

	@Mock
	private HelperUserSettingMappingDao helperUserSettingMappingDao;

	@Mock
	private PersonalTagUserSettingMappingDao personalTagUserSettingMappingDAO;

	@Mock
	private HelperDao helperDao;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	protected PropertyFileReader propertyFileReader;

	@Mock
	private ResponseValidator responseValidator;

	ResponseEntity<NotificationStatusResponse> response;

	RoleSettingResponse roleSettingResponse;
	SettingsRequest settingsRequest;
	UpdaterSettingsResponse updaterSettingsResponse;
	UserProfileDto userProfileDto;
	UserProfile userProfile;
	List<UserProfileDto> userProfileList;
	WorkSpaceDto workSpaceDto;
	ModuleDto moduleDto;
	Set<UserProfileDto> userProfileDtoSet;

	@Before
	public void setUp() throws CustomException {
		roleSettingResponse = new RoleSettingResponse();
		response = (ResponseEntity<NotificationStatusResponse>) Mockito.mock(ResponseEntity.class);
		settingsRequest = new SettingsRequest();
		settingsRequest.setDefaultModule("ModuleName");
		settingsRequest.setDefaultWorkspace("WorkSpaceName");
		settingsRequest.setPrintAdditionalOption("printAdditionalOption");
		List<HelperList> hilperList = new ArrayList<>();
		HelperList heList = new HelperList();
		heList.setCheck(true);
		heList.setHelperId(1);
		heList.setHelperName("helperName");
		heList.setModuleSpace("moduleSpace");
		hilperList.add(heList);
		settingsRequest.setHelperList(hilperList);
		settingsRequest.setNotificationAlertLevel("low");
		List<PersonalTagList> personalTagList = new ArrayList<>();
		PersonalTagList prList = new PersonalTagList();
		prList.setTag("tagName");
		prList.setTagLevel("tagLevel");
		prList.setTitleName("titleName");
		personalTagList.add(prList);
		settingsRequest.setPersonalTagList(personalTagList);
		userProfileDto = new UserProfileDto();
		userProfileDto.setUserProfileId(1);
		userProfileDto.setActive(true);
		userProfileDto.setUserId("DN89578");
		workSpaceDto = new WorkSpaceDto();
		workSpaceDto.setWorkSpaceId(1);
		workSpaceDto.setWorkSpaceName("workSpaceName");
		userProfileDto.setWorkSpace(workSpaceDto);
		moduleDto = new ModuleDto();
		moduleDto.setModuleId(1);
		moduleDto.setModuleName("moduleName");
		userProfileDto.setModule(moduleDto);
		userProfileDto.setAlertLevel("Low");
		userProfile = new UserProfile();
		userProfile.setUserProfileId(1);
		userProfile.setUserId("DN89578");
		userProfile.setActive(true);
		userProfileList = new ArrayList<>();
		userProfileList.add(userProfileDto);
		userProfileDtoSet = new HashSet<>();
		moduleDto.setUserProfile(userProfileDtoSet);

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetUserSettingsDetails() throws Exception {
		RoleDto roleObj = getRoleDto();

		List<HelperDto> helperDtoList = getHelperDtoList();

		Mockito.when(userProfileDAO.checkUserModuleWorkspace("DN89578")).thenReturn(true);

		roleObj.setRoleId(1);

		HelperUserSettingMappingDto helperUserSettingMappingDto = new HelperUserSettingMappingDto();
		helperUserSettingMappingDto.setActive(true);
		helperUserSettingMappingDto.setChecked(false);
		helperUserSettingMappingDto.setUserId("DN89578");
		helperUserSettingMappingDto.setHelperUserSettingMappingId(1);
		HelperDto helperDto = new HelperDto();
		helperDto.setHelperId(1);
		helperDto.setHelperName("helperName");
		helperUserSettingMappingDto.setHelper(helperDto);
		List<HelperUserSettingMappingDto> helperUserSettingMappingDtos = new ArrayList<>();
		helperUserSettingMappingDtos.add(helperUserSettingMappingDto);

		PersonalTagDto personalTagDto = new PersonalTagDto();
		PersonalLevelDto personalLevelDto = new PersonalLevelDto();
		personalTagDto.setPersonalTagId(1);
		personalTagDto.setTagName("tagName");
		personalLevelDto.setPersonalLevelId(1);
		personalLevelDto.setLevelName("levelName");
		PersonalTagUserSettingMappingDto personalTagUserSettingMappingDto = new PersonalTagUserSettingMappingDto();
		personalTagUserSettingMappingDto.setPersonalTagUserSettingMappingId(1);
		personalTagUserSettingMappingDto.setActive(true);
		personalTagUserSettingMappingDto.setPersonalTag(personalTagDto);
		personalTagUserSettingMappingDto.setPersonalLevel(personalLevelDto);
		personalTagUserSettingMappingDto.setTitleName("titleName");
		personalTagUserSettingMappingDto.setUserId("DN89578");

		PersonalTagUserSettingMappingDto personalTagUserSettingMappingDtoNeagtive = new PersonalTagUserSettingMappingDto();
		personalTagUserSettingMappingDtoNeagtive.setPersonalTagUserSettingMappingId(1);
		personalTagUserSettingMappingDtoNeagtive.setActive(true);
		personalTagUserSettingMappingDtoNeagtive.setPersonalTag(personalTagDto);
		personalTagUserSettingMappingDtoNeagtive.setPersonalLevel(personalLevelDto);
		personalTagUserSettingMappingDtoNeagtive.setTitleName("NEGATIVE");
		personalTagUserSettingMappingDtoNeagtive.setUserId("DN89578");

		List<PersonalTagUserSettingMappingDto> personalTagUserSettingMappingDtos = new ArrayList<>();
		personalTagUserSettingMappingDtos.add(personalTagUserSettingMappingDto);
		personalTagUserSettingMappingDtos.add(personalTagUserSettingMappingDtoNeagtive);

		PrintOptionDto printOptionDto = new PrintOptionDto();
		printOptionDto.setPrintOptionId(1);
		printOptionDto.setPrintAdditionalInfo("Print Info");
		List<PrintOptionDto> printOptionDtos = new ArrayList<>();
		printOptionDtos.add(printOptionDto);

		PrintUserSettingMapping printUserSettingMapping = new PrintUserSettingMapping();
		PrintOption printOption = new PrintOption();
		printOption.setPrintOptionId(1);
		printOption.setPrintAdditionalInfo("Print Info");
		printUserSettingMapping.setPrintUserSettingMappingId(1);
		printUserSettingMapping.setPrintOption(printOption);
		printUserSettingMapping.setActive(true);
		printUserSettingMapping.setUserId("DN89578");
		List<PrintUserSettingMapping> printUserSettingMappings = new ArrayList<>();
		printUserSettingMappings.add(printUserSettingMapping);

		Mockito.when(roleDao.getRole(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(roleObj);
		Mockito.when(helperDao.getHelperList()).thenReturn(helperDtoList);
		Mockito.when(userProfileDAO.getUserProfileList("DN89578")).thenReturn(userProfileList);
		Mockito.when(helperUserSettingMappingDao.userHelperList("DN89578")).thenReturn(helperUserSettingMappingDtos);
		Mockito.when(personalTagUserSettingMappingDAO.getPersonalTagUserSettingMapping("DN89578"))
				.thenReturn(personalTagUserSettingMappingDtos);
		Mockito.when(printUserSettingMappingDao.getPrintUserSettingMapping("DN89578"))
				.thenReturn(printUserSettingMappings);
		Mockito.when(printOptionDao.getPrintOptionList()).thenReturn(printOptionDtos);

		Mockito.when(restTemplate.getForObject(Matchers.eq("hostForNotificationFilter"), Matchers.<Class<String>>any()))
				.thenReturn("TestJson");
		Mockito.when(propertyFileReader.getHostForCMS(Mockito.anyString())).thenReturn("hostForNotificationFilter");

		NotificationFilter notificationFilter = Mockito.mock(NotificationFilter.class);
		ObjectMapper objectMapper = PowerMockito.mock(ObjectMapper.class);

		PowerMockito.whenNew(ObjectMapper.class).withNoArguments().thenReturn(objectMapper);
		Mockito.when(objectMapper.readValue(Mockito.anyString(), Matchers.<Class<NotificationFilter>>any()))
				.thenReturn(notificationFilter);

		roleSettingResponse = userSettingsService.getUserSettingsDetails("Chaiman", "DN89578");
		Set<ModuleDto> moduleSet = roleObj.getModule();
		List<ModuleDto> moduleDtoList = new ArrayList<>(moduleSet);
		assertEquals(moduleDtoList.get(0).getModuleName(),
				roleSettingResponse.getModule().getModuleList().get(0).getModuleName());

	}

	@Test
	public void testGetUserSettingsDetailsEmptyJsonResponse() throws Exception {
		RoleDto roleObj = getRoleDto();
		List<HelperDto> helperDtoList = getHelperDtoList();
		Mockito.when(roleDao.getRole(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(roleObj);
		Mockito.when(helperDao.getHelperList()).thenReturn(helperDtoList);

		Mockito.when(userProfileDAO.checkUserModuleWorkspace("DN89578")).thenReturn(false);
		List<HelperUserSettingMappingDto> mockList = new ArrayList<>();
		HelperUserSettingMappingDto mockObject = new HelperUserSettingMappingDto();
		mockList.add(mockObject);
		Mockito.when(helperUserSettingMappingDao.userHelperList("DN89578")).thenReturn(mockList);
		Mockito.when(printUserSettingMappingDao.getPrintUserSettingMapping("DN89578")).thenReturn(new ArrayList<>());
		Mockito.when(personalTagUserSettingMappingDAO.getPersonalTagUserSettingMapping("DN89578"))
				.thenReturn(new ArrayList<>());

		Mockito.when(restTemplate.getForObject(Matchers.eq("hostForNotificationFilter"), Matchers.<Class<String>>any()))
				.thenReturn("");
		Mockito.when(propertyFileReader.getHostForCMS(Mockito.anyString())).thenReturn("hostForNotificationFilter");

		NotificationFilter notificationFilter = Mockito.mock(NotificationFilter.class);
		ObjectMapper objectMapper = PowerMockito.mock(ObjectMapper.class);
		PowerMockito.whenNew(ObjectMapper.class).withNoArguments().thenReturn(objectMapper);
		Mockito.when(objectMapper.readValue(Mockito.anyString(), Matchers.<Class<NotificationFilter>>any()))
				.thenReturn(notificationFilter);

		roleSettingResponse = userSettingsService.getUserSettingsDetails("Chaiman", "DN89578");
		Set<ModuleDto> moduleSet = roleObj.getModule();
		List<ModuleDto> moduleDtoList = new ArrayList<>(moduleSet);
		assertEquals(moduleDtoList.get(0).getModuleName(),
				roleSettingResponse.getModule().getModuleList().get(0).getModuleName());

	}

	@Test(expected = CustomException.class)
	public void testGetUserSettingsDetailsCustomExcep() throws Exception {
		RoleDto roleObj = getRoleDto();
		List<HelperDto> helperDtoList = getHelperDtoList();
		Mockito.when(roleDao.getRole(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(roleObj);
		Mockito.when(helperDao.getHelperList()).thenReturn(helperDtoList);

		Mockito.when(userProfileDAO.checkUserModuleWorkspace("DN89578")).thenReturn(true);
		List<HelperUserSettingMappingDto> mockList = new ArrayList<>();
		HelperUserSettingMappingDto mockObject = new HelperUserSettingMappingDto();
		mockList.add(mockObject);
		Mockito.when(helperUserSettingMappingDao.userHelperList("DN89578")).thenReturn(mockList);
		Mockito.when(printUserSettingMappingDao.getPrintUserSettingMapping("DN89578")).thenReturn(new ArrayList<>());
		Mockito.when(personalTagUserSettingMappingDAO.getPersonalTagUserSettingMapping("DN89578"))
				.thenReturn(new ArrayList<>());

		Mockito.when(notificationUtils.getNotificationFilter()).thenThrow(CustomException.class);

		Mockito.when(propertyFileReader.getHostForCMS(Mockito.anyString())).thenReturn("hostForNotificationFilter");

		roleSettingResponse = userSettingsService.getUserSettingsDetails("Chaiman", "DN89578");
		Set<ModuleDto> moduleSet = roleObj.getModule();
		List<ModuleDto> moduleDtoList = new ArrayList<>(moduleSet);
		assertEquals(moduleDtoList.get(0).getModuleName(),
				roleSettingResponse.getModule().getModuleList().get(0).getModuleName());

	}

	@SuppressWarnings("unchecked")
	@Test(expected = CustomException.class)
	public void testGetUserSettingsDetailsIoCustomExcep() throws Exception {
		RoleDto roleObj = getRoleDto();
		List<HelperDto> helperDtoList = getHelperDtoList();
		Mockito.when(roleDao.getRole(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(roleObj);
		Mockito.when(helperDao.getHelperList()).thenReturn(helperDtoList);

		/**
		 * Only for Running Purpose
		 */
		Mockito.when(userProfileDAO.checkUserModuleWorkspace("DN89578")).thenReturn(true);
		List<HelperUserSettingMappingDto> mockList = new ArrayList<>();
		HelperUserSettingMappingDto mockObject = new HelperUserSettingMappingDto();
		mockList.add(mockObject);
		Mockito.when(helperUserSettingMappingDao.userHelperList("DN89578")).thenReturn(mockList);
		Mockito.when(printUserSettingMappingDao.getPrintUserSettingMapping("DN89578")).thenReturn(new ArrayList<>());
		Mockito.when(personalTagUserSettingMappingDAO.getPersonalTagUserSettingMapping("DN89578"))
				.thenReturn(new ArrayList<>());

		Mockito.when(notificationUtils.getNotificationFilter()).thenThrow(CustomException.class);

		roleSettingResponse = userSettingsService.getUserSettingsDetails("Chaiman", "DN89578");
		Set<ModuleDto> moduleSet = roleObj.getModule();
		List<ModuleDto> moduleDtoList = new ArrayList<>(moduleSet);
		assertEquals(moduleDtoList.get(0).getModuleName(),
				roleSettingResponse.getModule().getModuleList().get(0).getModuleName());

	}

	@Test
	public void testUpdateUserSettingsDetails() throws CustomException {
		RoleDto roleObj = getRoleDetails();
		Mockito.when(roleDao.getRole(Mockito.anyString())).thenReturn(roleObj);
		Set<ModuleDto> moduleDto = getModuleDetails();
		List<ModuleDto> moduleDtoList = new ArrayList<>(moduleDto);
		Mockito.when(moduleDao.getModule(settingsRequest.getDefaultModule(), roleObj.getRoleId()))
				.thenReturn(moduleDtoList.get(0));
		List<NotificationFilterRequest> notificationFilterRequestList = new ArrayList<>();
		NotificationFilterRequest notificationFilterRequest = new NotificationFilterRequest();
		notificationFilterRequest.setEventTypeId("1");
		notificationFilterRequest.setEventTypeCode("EventTypeCode");
		notificationFilterRequest.setIntrusivenessLevel("Medium");
		notificationFilterRequest.setFilterFlag(true);
		notificationFilterRequestList.add(notificationFilterRequest);
		settingsRequest.setNotificationFilterRequest(notificationFilterRequestList);

		Set<WorkSpaceDto> workSpaceDtos = getWorkSpaceDetails();
		List<WorkSpaceDto> workSpaceDtoList = new ArrayList<>(workSpaceDtos);
		Mockito.when(workpaceDao.getWorkSpace(settingsRequest.getDefaultWorkspace(), roleObj.getRoleId()))
				.thenReturn(workSpaceDtoList.get(0));

		userProfileDto.setActive(true);
		userProfileDto.setAlertLevel(settingsRequest.getNotificationAlertLevel());
		userProfileDto.setLastUpdateDateTime(new Date());
		userProfileDto.setModule(moduleDtoList.get(0));
		userProfileDto.setUserId("DN89578");
		userProfileDto.setWorkSpace(workSpaceDtoList.get(0));
		userProfile.setUserProfileId(1);
		Mockito.when(userProfileDAO.updateUserProfile(userProfileDto)).thenReturn(userProfile);

		Set<PrintGlobalSettingMappingDto> dtos = getPrintOptionDetails();
		List<PrintGlobalSettingMappingDto> dtosList = new ArrayList<>(dtos);
		Mockito.when(printOptionDao.getPrintOption(settingsRequest.getPrintAdditionalOption()))
				.thenReturn(dtosList.get(0).getPrintOption());
		PrintGlobalSettingMappingDto printGlobalSettingMappingDto = new PrintGlobalSettingMappingDto();
		printGlobalSettingMappingDto.setPrintOption(dtosList.get(0).getPrintOption());
		printGlobalSettingMappingDto.setRole(roleObj);
		HelperDto helperDto = new HelperDto();
		helperDto.setHelperId(1);
		List<HelperList> helperLists = settingsRequest.getHelperList();
		for (HelperList helperList : helperLists) {
			Mockito.when(helperDao.getHelperDetails(helperList.getHelperName(), helperList.getModuleSpace()))
					.thenReturn(helperDto);
			HelperUserSettingMappingDto helperUserSettingMappingDto = new HelperUserSettingMappingDto();
			helperUserSettingMappingDto.setHelperUserSettingMappingId(1);
			helperUserSettingMappingDto.setHelper(helperDto);
			helperUserSettingMappingDto.setActive(true);
			helperUserSettingMappingDto.setUserId("DN89578");
			HelperUserSettingMapping helperUserSettingMapping = new HelperUserSettingMapping();
			helperUserSettingMapping.setHelperUserSettingMappingId(1);
			Mockito.when(helperUserSettingMappingDao.updateHelperUserSettingMapping(helperUserSettingMappingDto))
					.thenReturn(helperUserSettingMapping);
		}

		List<PersonalTagList> peronalTagLists = settingsRequest.getPersonalTagList();
		for (PersonalTagList personalTagList : peronalTagLists) {
			PersonalTagDto personalTagDto = new PersonalTagDto();
			personalTagDto.setPersonalTagId(1);
			Mockito.when(personalTagDAO.getPersonalTag(personalTagList.getTag())).thenReturn(personalTagDto);
			PersonalLevelDto personalLevelDto = new PersonalLevelDto();
			personalLevelDto.setPersonalLevelId(1);
			Mockito.when(personalLevelDao.getPersonalLevel(personalTagList.getTagLevel())).thenReturn(personalLevelDto);
			PersonalTagUserSettingMappingDto dto = new PersonalTagUserSettingMappingDto();
			dto.setActive(true);
			dto.setTitleName("titleName");
			dto.setPersonalTagUserSettingMappingId(1);
			PersonalTagUserSettingMapping personalTagUserSettingMapping = new PersonalTagUserSettingMapping();
			personalTagUserSettingMapping.setPersonalTagUserSettingMappingId(1);
			personalTagUserSettingMapping.setActive(true);
			personalTagUserSettingMapping.setUserId("DN89578");
			Mockito.when(personalTagUserSettingMappingDAO.updatePersonalTagUserSettingMapping(dto))
					.thenReturn(personalTagUserSettingMapping);
		}

		Mockito.when(restTemplate.postForEntity(Matchers.eq("hostForCMS"), Matchers.<Class<?>>any(),
				Matchers.<Class<NotificationStatusResponse>>any())).thenReturn(response);
		Mockito.when(propertyFileReader.getHostForCMS(Mockito.anyString())).thenReturn("hostForCMS");

		HttpStatus httpStatus = PowerMockito.mock(HttpStatus.class);
		Mockito.when(response.getStatusCode()).thenReturn(httpStatus).thenReturn(HttpStatus.CREATED);
		Mockito.when(httpStatus.getReasonPhrase()).thenReturn("Updated Successfully");

		UpdaterSettingsResponse updaterSettingsResponse = userSettingsService.updateUserSettingsDetails(settingsRequest,
				"Chairman", Mockito.anyString());
		assertEquals(updaterSettingsResponse.getDescription(), SettingsConstants.UPDATE_SUCCESS);
	}

	@Test
	public void testUpdateUserSettingsDetailsWithEmptyNotifcationList() throws CustomException {
		RoleDto roleObj = getRoleDetails();
		Mockito.when(roleDao.getRole(Mockito.anyString())).thenReturn(roleObj);
		Set<ModuleDto> moduleDto = getModuleDetails();
		List<ModuleDto> moduleDtoList = new ArrayList<>(moduleDto);
		Mockito.when(moduleDao.getModule(settingsRequest.getDefaultModule(), roleObj.getRoleId()))
				.thenReturn(moduleDtoList.get(0));
		Set<WorkSpaceDto> workSpaceDtos = getWorkSpaceDetails();
		List<WorkSpaceDto> workSpaceDtoList = new ArrayList<>(workSpaceDtos);
		Mockito.when(workpaceDao.getWorkSpace(settingsRequest.getDefaultWorkspace(), roleObj.getRoleId()))
				.thenReturn(workSpaceDtoList.get(0));

		UserProfileDto userProfileDto = new UserProfileDto();
		userProfileDto.setActive(true);
		userProfileDto.setAlertLevel(settingsRequest.getNotificationAlertLevel());
		userProfileDto.setLastUpdateDateTime(new Date());
		userProfileDto.setModule(moduleDtoList.get(0));
		userProfileDto.setUserId("DN89578");
		userProfileDto.setWorkSpace(workSpaceDtoList.get(0));
		UserProfile userProfile = new UserProfile();
		userProfile.setUserProfileId(1);
		Mockito.when(userProfileDAO.updateUserProfile(userProfileDto)).thenReturn(userProfile);

		Set<PrintGlobalSettingMappingDto> dtos = getPrintOptionDetails();
		List<PrintGlobalSettingMappingDto> dtosList = new ArrayList<>(dtos);
		Mockito.when(printOptionDao.getPrintOption(settingsRequest.getPrintAdditionalOption()))
				.thenReturn(dtosList.get(0).getPrintOption());
		PrintGlobalSettingMappingDto printGlobalSettingMappingDto = new PrintGlobalSettingMappingDto();
		printGlobalSettingMappingDto.setPrintOption(dtosList.get(0).getPrintOption());
		printGlobalSettingMappingDto.setRole(roleObj);
		HelperDto helperDto = new HelperDto();
		helperDto.setHelperId(1);
		List<HelperList> helperLists = settingsRequest.getHelperList();
		for (HelperList helperList : helperLists) {
			Mockito.when(helperDao.getHelperDetails(helperList.getHelperName(), helperList.getModuleSpace()))
					.thenReturn(helperDto);
			HelperUserSettingMappingDto helperUserSettingMappingDto = new HelperUserSettingMappingDto();
			helperUserSettingMappingDto.setHelperUserSettingMappingId(1);
			helperUserSettingMappingDto.setHelper(helperDto);
			helperUserSettingMappingDto.setActive(true);
			helperUserSettingMappingDto.setUserId("DN89578");
			HelperUserSettingMapping helperUserSettingMapping = new HelperUserSettingMapping();
			helperUserSettingMapping.setHelperUserSettingMappingId(1);
			Mockito.when(helperUserSettingMappingDao.updateHelperUserSettingMapping(helperUserSettingMappingDto))
					.thenReturn(helperUserSettingMapping);
		}

		List<PersonalTagList> peronalTagLists = settingsRequest.getPersonalTagList();
		for (PersonalTagList personalTagList : peronalTagLists) {
			PersonalTagDto personalTagDto = new PersonalTagDto();
			personalTagDto.setPersonalTagId(1);
			Mockito.when(personalTagDAO.getPersonalTag(personalTagList.getTag())).thenReturn(personalTagDto);
			PersonalLevelDto personalLevelDto = new PersonalLevelDto();
			personalLevelDto.setPersonalLevelId(1);
			Mockito.when(personalLevelDao.getPersonalLevel(personalTagList.getTagLevel())).thenReturn(personalLevelDto);
			PersonalTagUserSettingMappingDto dto = new PersonalTagUserSettingMappingDto();
			dto.setActive(true);
			dto.setTitleName("titleName");
			dto.setPersonalTagUserSettingMappingId(1);
			PersonalTagUserSettingMapping personalTagUserSettingMapping = new PersonalTagUserSettingMapping();
			personalTagUserSettingMapping.setPersonalTagUserSettingMappingId(1);
			personalTagUserSettingMapping.setActive(true);
			personalTagUserSettingMapping.setUserId("DN89578");
			Mockito.when(personalTagUserSettingMappingDAO.updatePersonalTagUserSettingMapping(dto))
					.thenReturn(personalTagUserSettingMapping);
		}

		UpdaterSettingsResponse updaterSettingsResponse = userSettingsService.updateUserSettingsDetails(settingsRequest,
				"Chairman", Mockito.anyString());
		assertEquals(updaterSettingsResponse.getDescription(), SettingsConstants.UPDATE_SUCCESS);
	}

	@Test
	public void testUpdateUserSettingsDetailsException() throws CustomException {
		RoleDto roleObj = getRoleDetails();
		Mockito.when(roleDao.getRole(Mockito.anyString())).thenReturn(roleObj);
		Set<ModuleDto> moduleDto = getModuleDetails();
		List<ModuleDto> moduleDtoList = new ArrayList<>(moduleDto);
		Mockito.when(moduleDao.getModule(settingsRequest.getDefaultModule(), roleObj.getRoleId()))
				.thenReturn(moduleDtoList.get(0));
		List<NotificationFilterRequest> notificationFilterRequestList = new ArrayList<>();
		NotificationFilterRequest notificationFilterRequest = new NotificationFilterRequest();
		notificationFilterRequest.setEventTypeId("1");
		notificationFilterRequest.setEventTypeCode("EventTypeCode");
		notificationFilterRequest.setIntrusivenessLevel("Medium");
		notificationFilterRequest.setFilterFlag(true);
		notificationFilterRequestList.add(notificationFilterRequest);
		settingsRequest.setNotificationFilterRequest(notificationFilterRequestList);

		Set<WorkSpaceDto> workSpaceDtos = getWorkSpaceDetails();
		List<WorkSpaceDto> workSpaceDtoList = new ArrayList<>(workSpaceDtos);
		Mockito.when(workpaceDao.getWorkSpace(settingsRequest.getDefaultWorkspace(), roleObj.getRoleId()))
				.thenReturn(workSpaceDtoList.get(0));

		userProfileDto.setActive(true);
		userProfileDto.setAlertLevel(settingsRequest.getNotificationAlertLevel());
		userProfileDto.setLastUpdateDateTime(new Date());
		userProfileDto.setModule(moduleDtoList.get(0));
		userProfileDto.setUserId("DN89578");
		userProfileDto.setWorkSpace(workSpaceDtoList.get(0));
		userProfile.setUserProfileId(1);
		Mockito.when(userProfileDAO.updateUserProfile(userProfileDto)).thenReturn(userProfile);

		Set<PrintGlobalSettingMappingDto> dtos = getPrintOptionDetails();
		List<PrintGlobalSettingMappingDto> dtosList = new ArrayList<>(dtos);
		Mockito.when(printOptionDao.getPrintOption(settingsRequest.getPrintAdditionalOption()))
				.thenReturn(dtosList.get(0).getPrintOption());
		PrintGlobalSettingMappingDto printGlobalSettingMappingDto = new PrintGlobalSettingMappingDto();
		printGlobalSettingMappingDto.setPrintOption(dtosList.get(0).getPrintOption());
		printGlobalSettingMappingDto.setRole(roleObj);
		HelperDto helperDto = new HelperDto();
		helperDto.setHelperId(1);
		List<HelperList> helperLists = settingsRequest.getHelperList();
		for (HelperList helperList : helperLists) {
			Mockito.when(helperDao.getHelperDetails(helperList.getHelperName(), helperList.getModuleSpace()))
					.thenReturn(helperDto);
			HelperUserSettingMappingDto helperUserSettingMappingDto = new HelperUserSettingMappingDto();
			helperUserSettingMappingDto.setHelperUserSettingMappingId(1);
			helperUserSettingMappingDto.setHelper(helperDto);
			helperUserSettingMappingDto.setActive(true);
			helperUserSettingMappingDto.setUserId("DN89578");
			HelperUserSettingMapping helperUserSettingMapping = new HelperUserSettingMapping();
			helperUserSettingMapping.setHelperUserSettingMappingId(1);
			Mockito.when(helperUserSettingMappingDao.updateHelperUserSettingMapping(helperUserSettingMappingDto))
					.thenReturn(helperUserSettingMapping);
		}

		List<PersonalTagList> peronalTagLists = settingsRequest.getPersonalTagList();
		for (PersonalTagList personalTagList : peronalTagLists) {
			PersonalTagDto personalTagDto = new PersonalTagDto();
			personalTagDto.setPersonalTagId(1);
			Mockito.when(personalTagDAO.getPersonalTag(personalTagList.getTag())).thenReturn(personalTagDto);
			PersonalLevelDto personalLevelDto = new PersonalLevelDto();
			personalLevelDto.setPersonalLevelId(1);
			Mockito.when(personalLevelDao.getPersonalLevel(personalTagList.getTagLevel())).thenReturn(personalLevelDto);
			PersonalTagUserSettingMappingDto dto = new PersonalTagUserSettingMappingDto();
			dto.setActive(true);
			dto.setTitleName("titleName");
			dto.setPersonalTagUserSettingMappingId(1);
			PersonalTagUserSettingMapping personalTagUserSettingMapping = new PersonalTagUserSettingMapping();
			personalTagUserSettingMapping.setPersonalTagUserSettingMappingId(1);
			personalTagUserSettingMapping.setActive(true);
			personalTagUserSettingMapping.setUserId("DN89578");
			Mockito.when(personalTagUserSettingMappingDAO.updatePersonalTagUserSettingMapping(dto))
					.thenReturn(personalTagUserSettingMapping);
		}

		Mockito.when(restTemplate.postForEntity(Matchers.eq("hostForCMS"), Matchers.<Class<?>>any(),
				Matchers.<Class<NotificationStatusResponse>>any())).thenReturn(response);
		Mockito.when(propertyFileReader.getHostForCMS(Mockito.anyString())).thenReturn("hostForCMS");

		HttpStatus httpStatus = PowerMockito.mock(HttpStatus.class);
		Mockito.when(response.getStatusCode()).thenReturn(httpStatus);
		Mockito.when(httpStatus.getReasonPhrase()).thenReturn("Updated Successfully");

		UpdaterSettingsResponse updaterSettingsResponse = userSettingsService.updateUserSettingsDetails(settingsRequest,
				"Chairman", Mockito.anyString());
		assertEquals(updaterSettingsResponse.getDescription(), SettingsConstants.UPDATE_SUCCESS);
	}

	@Test
	public void testResetDisplayUserSettingsToGlobal() throws Exception {

		final String sectionName = "Display";
		RoleDto roleObj = getRoleDto();
		List<HelperDto> helperDtoList = getHelperDtoList();
		Set<PrintGlobalSettingMappingDto> dtos = getPrintOptionDetails();
		List<PrintGlobalSettingMappingDto> dtosList = new ArrayList<>(dtos);

		Mockito.when(roleDao.getRole(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(roleObj);
		Mockito.when(helperDao.getHelperList()).thenReturn(helperDtoList);
		Mockito.when(printOptionDao.getPrintOption(settingsRequest.getPrintAdditionalOption()))
				.thenReturn(dtosList.get(0).getPrintOption());

		Mockito.when(userProfileDAO.getUserProfileList("DN89578")).thenReturn(userProfileList);

		Mockito.when(restTemplate.getForObject(Matchers.eq("hostForNotificationFilter"), Matchers.<Class<String>>any()))
				.thenReturn("TestJson");
		Mockito.when(propertyFileReader.getHostForCMS(Mockito.anyString())).thenReturn("hostForNotificationFilter");

		NotificationFilter notificationFilter = Mockito.mock(NotificationFilter.class);
		ObjectMapper objectMapper = PowerMockito.mock(ObjectMapper.class);
		PowerMockito.whenNew(ObjectMapper.class).withNoArguments().thenReturn(objectMapper);
		Mockito.when(objectMapper.readValue(Mockito.anyString(), Matchers.<Class<NotificationFilter>>any()))
				.thenReturn(notificationFilter);
		Mockito.when(globalProfileDAO.getGlobalProfileAlertLevel(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn("low");
		Mockito.when(notificationUtils.getNotificationFilter(Mockito.any())).thenReturn(notificationFilter);

		roleSettingResponse = userSettingsService.resetUserSettingsToGlobal(sectionName, "Chaiman", "DN89578");

		Set<ModuleDto> moduleSet = roleObj.getModule();
		List<ModuleDto> moduleDtoList = new ArrayList<>(moduleSet);
		assertEquals(moduleDtoList.get(0).getModuleName(),
				roleSettingResponse.getModule().getModuleList().get(0).getModuleName());

	}

	@Test
	public void testResetHelperUserSettingsToGlobal() throws Exception {

		final String sectionName = "Helpers";
		RoleDto roleObj = getRoleDto();
		List<HelperDto> helperDtoList = getHelperDtoList();
		Set<PrintGlobalSettingMappingDto> dtos = getPrintOptionDetails();
		List<PrintGlobalSettingMappingDto> dtosList = new ArrayList<>(dtos);

		Mockito.when(roleDao.getRole(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(roleObj);
		Mockito.when(helperDao.getHelperList()).thenReturn(helperDtoList);
		Mockito.when(printOptionDao.getPrintOption(settingsRequest.getPrintAdditionalOption()))
				.thenReturn(dtosList.get(0).getPrintOption());

		Mockito.when(userProfileDAO.getUserProfileList("DN89578")).thenReturn(userProfileList);

		Mockito.when(restTemplate.getForObject(Matchers.eq("hostForNotificationFilter"), Matchers.<Class<String>>any()))
				.thenReturn("TestJson");
		Mockito.when(propertyFileReader.getHostForCMS(Mockito.anyString())).thenReturn("hostForNotificationFilter");

		NotificationFilter notificationFilter = Mockito.mock(NotificationFilter.class);
		ObjectMapper objectMapper = PowerMockito.mock(ObjectMapper.class);
		PowerMockito.whenNew(ObjectMapper.class).withNoArguments().thenReturn(objectMapper);
		Mockito.when(objectMapper.readValue(Mockito.anyString(), Matchers.<Class<NotificationFilter>>any()))
				.thenReturn(notificationFilter);
		Mockito.when(globalProfileDAO.getGlobalProfileAlertLevel(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn("low");
		Mockito.when(notificationUtils.getNotificationFilter(Mockito.any())).thenReturn(notificationFilter);

		roleSettingResponse = userSettingsService.resetUserSettingsToGlobal(sectionName, "Chaiman", "DN89578");

		Set<HelperGlobalSettingMappingDto> helperSet = roleObj.getHelperGlobalSettingMapping();
		List<HelperGlobalSettingMappingDto> helper = new ArrayList<>(helperSet);

		assertEquals(helper.get(0).getHelper().getHelperId(),
				roleSettingResponse.getHelper().getHelperList().get(0).getHelperId());

	}

	@Test
	public void testResetPrintOptionUserSettingsToGlobal() throws Exception {

		final String sectionName = "Printing Defaults";
		RoleDto roleObj = getRoleDto();
		List<HelperDto> helperDtoList = getHelperDtoList();

		Set<PrintGlobalSettingMappingDto> dtos = getPrintOptionDetails();
		List<PrintGlobalSettingMappingDto> dtosList = new ArrayList<>(dtos);
		List<PrintOptionDto> printOptionDtoList = new ArrayList<>();
		printOptionDtoList.add(dtosList.get(0).getPrintOption());

		Mockito.when(roleDao.getRole(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(roleObj);
		Mockito.when(helperDao.getHelperList()).thenReturn(helperDtoList);
		Mockito.when(printOptionDao.getPrintOptionList()).thenReturn(printOptionDtoList);

		Mockito.when(userProfileDAO.getUserProfileList("DN89578")).thenReturn(userProfileList);

		Mockito.when(restTemplate.getForObject(Matchers.eq("hostForNotificationFilter"), Matchers.<Class<String>>any()))
				.thenReturn("TestJson");
		Mockito.when(propertyFileReader.getHostForCMS(Mockito.anyString())).thenReturn("hostForNotificationFilter");

		NotificationFilter notificationFilter = Mockito.mock(NotificationFilter.class);
		ObjectMapper objectMapper = PowerMockito.mock(ObjectMapper.class);
		PowerMockito.whenNew(ObjectMapper.class).withNoArguments().thenReturn(objectMapper);
		Mockito.when(objectMapper.readValue(Mockito.anyString(), Matchers.<Class<NotificationFilter>>any()))
				.thenReturn(notificationFilter);
		Mockito.when(globalProfileDAO.getGlobalProfileAlertLevel(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn("low");
		Mockito.when(notificationUtils.getNotificationFilter(Mockito.any())).thenReturn(notificationFilter);

		roleSettingResponse = userSettingsService.resetUserSettingsToGlobal(sectionName, "Chaiman", "DN89578");

		Set<PrintGlobalSettingMappingDto> printerSet = roleObj.getPrintGlobalSettingMapping();
		List<PrintGlobalSettingMappingDto> printer = new ArrayList<>(printerSet);

		assertEquals(printer.get(0).getPrintOption().getPrintOptionId(),
				roleSettingResponse.getPrintOption().getPrintOptionList().get(0).getPrintOptionId());

	}

	@Test
	public void testResetNotificationSettingsToGlobal() throws Exception {

		final String sectionName = "Notifications";
		RoleDto roleObj = getRoleDto();
		List<HelperDto> helperDtoList = getHelperDtoList();
		Set<PrintGlobalSettingMappingDto> dtos = getPrintOptionDetails();
		List<PrintGlobalSettingMappingDto> dtosList = new ArrayList<>(dtos);

		Mockito.when(roleDao.getRole(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(roleObj);
		Mockito.when(helperDao.getHelperList()).thenReturn(helperDtoList);
		Mockito.when(printOptionDao.getPrintOption(settingsRequest.getPrintAdditionalOption()))
				.thenReturn(dtosList.get(0).getPrintOption());

		Mockito.when(userProfileDAO.getUserProfileList("DN89578")).thenReturn(userProfileList);

		Mockito.when(restTemplate.getForObject(Matchers.eq("hostForNotificationFilter"), Matchers.<Class<String>>any()))
				.thenReturn("TestJson");
		Mockito.when(propertyFileReader.getHostForCMS(Mockito.anyString())).thenReturn("hostForNotificationFilter");

		NotificationFilter notificationFilter = Mockito.mock(NotificationFilter.class);
	
		ObjectMapper objectMapper = PowerMockito.mock(ObjectMapper.class);
		PowerMockito.whenNew(ObjectMapper.class).withNoArguments().thenReturn(objectMapper);
		Mockito.when(objectMapper.readValue(Mockito.anyString(), Matchers.<Class<NotificationFilter>>any()))
				.thenReturn(notificationFilter);
		Mockito.when(globalProfileDAO.getGlobalProfileAlertLevel(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn("low");
		Mockito.when(notificationUtils.getNotificationFilter(Mockito.any())).thenReturn(notificationFilter);

		roleSettingResponse = userSettingsService.resetUserSettingsToGlobal(sectionName, "Chaiman", "DN89578");
		 assertNotNull(roleSettingResponse);

	}

	@Test
	public void testResetPersonalTagSettingsToGlobal() throws Exception {

		final String sectionName = "Personal tags";
		RoleDto roleObj = getRoleDto();
		List<HelperDto> helperDtoList = getHelperDtoList();
		Set<PrintGlobalSettingMappingDto> dtos = getPrintOptionDetails();
		List<PrintGlobalSettingMappingDto> dtosList = new ArrayList<>(dtos);

		Mockito.when(roleDao.getRole(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(roleObj);
		Mockito.when(helperDao.getHelperList()).thenReturn(helperDtoList);
		Mockito.when(printOptionDao.getPrintOption(settingsRequest.getPrintAdditionalOption()))
				.thenReturn(dtosList.get(0).getPrintOption());

		Mockito.when(userProfileDAO.getUserProfileList("DN89578")).thenReturn(userProfileList);

		Mockito.when(restTemplate.getForObject(Matchers.eq("hostForNotificationFilter"), Matchers.<Class<String>>any()))
				.thenReturn("TestJson");
		Mockito.when(propertyFileReader.getHostForCMS(Mockito.anyString())).thenReturn("hostForNotificationFilter");

		NotificationFilter notificationFilter = Mockito.mock(NotificationFilter.class);
		ObjectMapper objectMapper = PowerMockito.mock(ObjectMapper.class);
		PowerMockito.whenNew(ObjectMapper.class).withNoArguments().thenReturn(objectMapper);
		Mockito.when(objectMapper.readValue(Mockito.anyString(), Matchers.<Class<NotificationFilter>>any()))
				.thenReturn(notificationFilter);
		Mockito.when(globalProfileDAO.getGlobalProfileAlertLevel(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn("low");
		Mockito.when(notificationUtils.getNotificationFilter(Mockito.any())).thenReturn(notificationFilter);

		roleSettingResponse = userSettingsService.resetUserSettingsToGlobal(sectionName, "Chaiman", "DN89578");

		Set<PersonalTagGlobalSettingMappingDto> personalTagSet = roleObj.getPersonalTagGlobalSettingMapping();
		List<PersonalTagGlobalSettingMappingDto> personalTagList = new ArrayList<>(personalTagSet);
		assertEquals(personalTagList.get(0).getPersonalTag().getTagName(),
				roleSettingResponse.getPersonalTag().getPersonalTagList().get(0).getTag());

	}

	@Test
	public void testResetAllSettingsToGlobal() throws Exception {

		final String sectionName = "All";
		RoleDto roleObj = getRoleDto();
		List<HelperDto> helperDtoList = getHelperDtoList();
		Set<PrintGlobalSettingMappingDto> dtos = getPrintOptionDetails();
		List<PrintGlobalSettingMappingDto> dtosList = new ArrayList<>(dtos);

		Mockito.when(roleDao.getRole(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(roleObj);
		Mockito.when(helperDao.getHelperList()).thenReturn(helperDtoList);
		Mockito.when(printOptionDao.getPrintOption(settingsRequest.getPrintAdditionalOption()))
				.thenReturn(dtosList.get(0).getPrintOption());

		Mockito.when(userProfileDAO.getUserProfileList("DN89578")).thenReturn(userProfileList);

		Mockito.when(restTemplate.getForObject(Matchers.eq("hostForNotificationFilter"), Matchers.<Class<String>>any()))
				.thenReturn("TestJson");
		Mockito.when(propertyFileReader.getHostForCMS(Mockito.anyString())).thenReturn("hostForNotificationFilter");

		NotificationFilter notificationFilter = Mockito.mock(NotificationFilter.class);
		ObjectMapper objectMapper = PowerMockito.mock(ObjectMapper.class);
		PowerMockito.whenNew(ObjectMapper.class).withNoArguments().thenReturn(objectMapper);
		Mockito.when(objectMapper.readValue(Mockito.anyString(), Matchers.<Class<NotificationFilter>>any()))
				.thenReturn(notificationFilter);
		Mockito.when(globalProfileDAO.getGlobalProfileAlertLevel(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn("low");
		Mockito.when(notificationUtils.getNotificationFilter(Mockito.any())).thenReturn(notificationFilter);

		roleSettingResponse = userSettingsService.resetUserSettingsToGlobal(sectionName, "Chaiman", "DN89578");

		Set<ModuleDto> moduleSet = roleObj.getModule();
		List<ModuleDto> moduleDtoList = new ArrayList<>(moduleSet);
		assertEquals(moduleDtoList.get(0).getModuleName(),
				roleSettingResponse.getModule().getModuleList().get(0).getModuleName());

	}

	@Test
	public void testResetEmptySettingsToGlobal() throws Exception {

		final String sectionName = "";
		RoleDto roleObj = getRoleDto();
		List<HelperDto> helperDtoList = getHelperDtoList();
		Set<PrintGlobalSettingMappingDto> dtos = getPrintOptionDetails();
		List<PrintGlobalSettingMappingDto> dtosList = new ArrayList<>(dtos);

		Mockito.when(roleDao.getRole(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(roleObj);
		Mockito.when(helperDao.getHelperList()).thenReturn(helperDtoList);
		Mockito.when(printOptionDao.getPrintOption(settingsRequest.getPrintAdditionalOption()))
				.thenReturn(dtosList.get(0).getPrintOption());

		Mockito.when(userProfileDAO.getUserProfileList("DN89578")).thenReturn(userProfileList);

		Mockito.when(restTemplate.getForObject(Matchers.eq("hostForNotificationFilter"), Matchers.<Class<String>>any()))
				.thenReturn("TestJson");
		Mockito.when(propertyFileReader.getHostForCMS(Mockito.anyString())).thenReturn("hostForNotificationFilter");

		NotificationFilter notificationFilter = Mockito.mock(NotificationFilter.class);
		ObjectMapper objectMapper = PowerMockito.mock(ObjectMapper.class);
		PowerMockito.whenNew(ObjectMapper.class).withNoArguments().thenReturn(objectMapper);
		Mockito.when(objectMapper.readValue(Mockito.anyString(), Matchers.<Class<NotificationFilter>>any()))
				.thenReturn(notificationFilter);
		Mockito.when(globalProfileDAO.getGlobalProfileAlertLevel(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn("low");
		Mockito.when(notificationUtils.getNotificationFilter(Mockito.any())).thenReturn(notificationFilter);

		roleSettingResponse = userSettingsService.resetUserSettingsToGlobal(sectionName, "Chaiman", "DN89578");
		// roleSettingResponse2 =
		// userSettingsService.resetUserSettingsToGlobal(sectionName, "Chaiman",
		// "DN89578");
		Set<ModuleDto> moduleSet = roleObj.getModule();
		List<ModuleDto> moduleDtoList = new ArrayList<>(moduleSet);
		assertEquals(moduleDtoList.get(0).getModuleName(),
				roleSettingResponse.getModule().getModuleList().get(0).getModuleName());

	}

	@Test
	public void testResetEmptySettingsToGlobalWithGlobalData() throws Exception {

		final String sectionName = "";
		RoleDto roleObj = getRoleDto();

		Set<GlobalProfileDto> globalProfileDtos = setGlobalProfileDetails();
		roleObj.setGlobalProfile(globalProfileDtos);

		List<HelperDto> helperDtoList = getHelperDtoList();
		Set<PrintGlobalSettingMappingDto> dtos = getPrintOptionDetails();
		List<PrintGlobalSettingMappingDto> dtosList = new ArrayList<>(dtos);

		Mockito.when(roleDao.getRole(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(roleObj);
		Mockito.when(helperDao.getHelperList()).thenReturn(helperDtoList);
		Mockito.when(printOptionDao.getPrintOption(settingsRequest.getPrintAdditionalOption()))
				.thenReturn(dtosList.get(0).getPrintOption());

		Mockito.when(userProfileDAO.getUserProfileList("DN89578")).thenReturn(userProfileList);

		Mockito.when(restTemplate.getForObject(Matchers.eq("hostForNotificationFilter"), Matchers.<Class<String>>any()))
				.thenReturn("TestJson");
		Mockito.when(propertyFileReader.getHostForCMS(Mockito.anyString())).thenReturn("hostForNotificationFilter");

		NotificationFilter notificationFilter = Mockito.mock(NotificationFilter.class);
		ObjectMapper objectMapper = PowerMockito.mock(ObjectMapper.class);
		PowerMockito.whenNew(ObjectMapper.class).withNoArguments().thenReturn(objectMapper);
		Mockito.when(objectMapper.readValue(Mockito.anyString(), Matchers.<Class<NotificationFilter>>any()))
				.thenReturn(notificationFilter);
		Mockito.when(globalProfileDAO.getGlobalProfileAlertLevel(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn("low");
		Mockito.when(notificationUtils.getNotificationFilter(Mockito.any())).thenReturn(notificationFilter);

		roleSettingResponse = userSettingsService.resetUserSettingsToGlobal(sectionName, "Chaiman", "DN89578");
		// roleSettingResponse2 =
		// userSettingsService.resetUserSettingsToGlobal(sectionName, "Chaiman",
		// "DN89578");
		Set<ModuleDto> moduleSet = roleObj.getModule();
		List<ModuleDto> moduleDtoList = new ArrayList<>(moduleSet);
		assertEquals(moduleDtoList.get(0).getModuleName(),
				roleSettingResponse.getModule().getModuleList().get(0).getModuleName());

	}

	private RoleDto getRoleDetails() {
		RoleDto roleDto = new RoleDto();
		roleDto.setRoleId(1);
		roleDto.setDescription(ROLE_DESCRIPTION);
		roleDto.setRoleName("Chairman");
		return roleDto;
	}

	private RoleDto getRoleDto() throws CustomException {

		RoleDto roleObj = new RoleDto();

		roleObj.setDescription(ROLE_DESCRIPTION);
		Set<ModuleDto> module = getModuleDetails();
		roleObj.setModule(module);
		Set<WorkSpaceDto> workSpace = getWorkSpaceDetails();
		roleObj.setWorkSpace(workSpace);
		Set<PrintGlobalSettingMappingDto> printGlobalSettingMappingDtos = getPrintOptionDetails();
		roleObj.setPrintGlobalSettingMapping(printGlobalSettingMappingDtos);
		Set<PersonalTagGlobalSettingMappingDto> personalTagGlobalSettingMappingDtos = getPersonalTagDetails();

		roleObj.setPersonalTagGlobalSettingMapping(personalTagGlobalSettingMappingDtos);
		Set<HelperGlobalSettingMappingDto> helperGlobalSettingMappingDtos = getHelperDetails();
		roleObj.setHelperGlobalSettingMapping(helperGlobalSettingMappingDtos);
		Set<GlobalProfileDto> globalProfileDtos = getGlobalProfileDetails();
		roleObj.setGlobalProfile(globalProfileDtos);
		return roleObj;
	}

	private List<HelperDto> getHelperDtoList() {
		List<HelperDto> helperDtoList = new ArrayList<>();
		HelperDto helperDto = new HelperDto();
		helperDto.setHelperId(1);
		helperDto.setHelperName("helperName");
		HelperUserSettingMappingDto helperUserSettingMappingDto = new HelperUserSettingMappingDto();
		helperUserSettingMappingDto.setActive(true);
		helperUserSettingMappingDto.setHelperUserSettingMappingId(1);
		Set<HelperUserSettingMappingDto> helperUserSettingMappingDtos = new HashSet<>();
		helperUserSettingMappingDtos.add(helperUserSettingMappingDto);
		helperDto.setHelperUserSettingMapping(helperUserSettingMappingDtos);
		helperDtoList.add(helperDto);
		return helperDtoList;
	}

	private Set<GlobalProfileDto> getGlobalProfileDetails() {
		GlobalProfileDto globalProfile = new GlobalProfileDto();
		globalProfile.setGlobalProfileId(1);
		globalProfile.setAlertLevel("low");
		Set<GlobalProfileDto> globalProfileDtos = new HashSet<>();
		return globalProfileDtos;
	}

	private Set<HelperGlobalSettingMappingDto> getHelperDetails() {
		RoleDto roleObj = new RoleDto();
		roleObj.setRoleId(1);
		HelperDto helperDto = new HelperDto();
		helperDto.setHelperId(1);
		helperDto.setHelperName("helperName");
		HelperUserSettingMappingDto helperUserSettingMappingDto = new HelperUserSettingMappingDto();
		helperUserSettingMappingDto.setActive(true);
		helperUserSettingMappingDto.setHelperUserSettingMappingId(1);
		helperUserSettingMappingDto.setUserId("DN89578");
		Set<HelperUserSettingMappingDto> helperUserSettingMappingDtos = new HashSet<>();
		helperUserSettingMappingDtos.add(helperUserSettingMappingDto);
		helperDto.setHelperUserSettingMapping(helperUserSettingMappingDtos);
		HelperGlobalSettingMappingDto helperGlobalSettingMappingDto = new HelperGlobalSettingMappingDto();
		helperGlobalSettingMappingDto.setHelperGlobalSettingMappingId(1);
		helperGlobalSettingMappingDto.setHelper(helperDto);
		helperGlobalSettingMappingDto.setRole(roleObj);
		Set<HelperGlobalSettingMappingDto> helperGlobalSettingMappingDtos = new HashSet<>();
		helperGlobalSettingMappingDtos.add(helperGlobalSettingMappingDto);
		return helperGlobalSettingMappingDtos;
	}

	private Set<PersonalTagGlobalSettingMappingDto> getPersonalTagDetails() {
		PersonalTagDto personalTagDto = new PersonalTagDto();
		PersonalLevelDto personalLevelDto = new PersonalLevelDto();
		personalTagDto.setPersonalTagId(1);
		personalTagDto.setTagName("tagName");
		personalLevelDto.setPersonalLevelId(1);
		personalLevelDto.setLevelName("levelName");
		PersonalTagUserSettingMappingDto personalTagUserSettingMappingDto = new PersonalTagUserSettingMappingDto();
		personalTagUserSettingMappingDto.setPersonalTagUserSettingMappingId(1);
		personalTagUserSettingMappingDto.setActive(true);
		personalTagUserSettingMappingDto.setPersonalTag(personalTagDto);
		personalTagUserSettingMappingDto.setPersonalLevel(personalLevelDto);
		personalTagUserSettingMappingDto.setTitleName("titleName");
		personalTagUserSettingMappingDto.setUserId("DN89578");
		Set<PersonalTagUserSettingMappingDto> dtos = new HashSet<>();
		dtos.add(personalTagUserSettingMappingDto);
		personalLevelDto.setPersonalTagUserSettingMapping(dtos);
		personalTagDto.setPersonalTagUserSettingMapping(dtos);
		PersonalTagGlobalSettingMappingDto personalTagGlobalSettingMappingDto = new PersonalTagGlobalSettingMappingDto();
		personalTagGlobalSettingMappingDto.setPersonalTag(personalTagDto);
		personalTagGlobalSettingMappingDto.setPersonalLevel(personalLevelDto);
		Set<PersonalTagGlobalSettingMappingDto> personalTagGlobalSettingMappingDtos = new HashSet<>();
		personalTagGlobalSettingMappingDtos.add(personalTagGlobalSettingMappingDto);
		return personalTagGlobalSettingMappingDtos;
	}

	private Set<PrintGlobalSettingMappingDto> getPrintOptionDetails() {
		PrintOptionDto printOptionDto = new PrintOptionDto();
		printOptionDto.setPrintOptionId(1);
		printOptionDto.setPrintAdditionalInfo("Print Info");
		PrintUserSettingMappingDto printUserSettingMappingDto = new PrintUserSettingMappingDto();
		printUserSettingMappingDto.setActive(true);
		printUserSettingMappingDto.setPrintUserSettingMappingId(1);
		printUserSettingMappingDto.setUserId("DN89578");
		Set<PrintUserSettingMappingDto> printUserSettingMappingDtos = new HashSet<>();
		printOptionDto.setPrintUserSettingMapping(printUserSettingMappingDtos);
		PrintGlobalSettingMappingDto printGlobalSettingMappingDto = new PrintGlobalSettingMappingDto();
		printGlobalSettingMappingDto.setPrintGlobalSettingMappingId(0);
		printGlobalSettingMappingDto.setPrintOption(printOptionDto);
		Set<PrintGlobalSettingMappingDto> printGlobalSettingMappingDtos = new HashSet<>();
		printGlobalSettingMappingDtos.add(printGlobalSettingMappingDto);
		return printGlobalSettingMappingDtos;
	}

	private Set<WorkSpaceDto> getWorkSpaceDetails() {
		WorkSpaceDto workSpaceDto = new WorkSpaceDto();
		workSpaceDto.setWorkSpaceId(1);
		workSpaceDto.setWorkSpaceName("workspace");
		Set<UserProfileDto> userProfile = setUserProfileDetails();
		Set<GlobalProfileDto> globalProfile = setGlobalProfileDetails();
		workSpaceDto.setUserProfile(userProfile);
		workSpaceDto.setGlobalProfile(globalProfile);
		Set<WorkSpaceDto> workSpace = new HashSet<>();
		workSpace.add(workSpaceDto);
		return workSpace;
	}

	private Set<ModuleDto> getModuleDetails() {
		ModuleDto moduleDto = new ModuleDto();
		moduleDto.setModuleId(1);
		moduleDto.setModuleName("module");
		// moduleDto.setGlobalProfile(globalProfile);

		userProfileDto.setActive(true);
		userProfileDto.setUserProfileId(1);
		userProfileDto.setModule(moduleDto);
		userProfileDtoSet.add(userProfileDto);
		moduleDto.setUserProfile(userProfileDtoSet);
		Set<ModuleDto> module = new HashSet<>();
		Set<UserProfileDto> userProfile = setUserProfileDetails();
		Set<GlobalProfileDto> globalProfile = setGlobalProfileDetails();
		moduleDto.setUserProfile(userProfile);
		moduleDto.setGlobalProfile(globalProfile);
		module.add(moduleDto);
		return module;
	}

	private Set<UserProfileDto> setUserProfileDetails() {

		ModuleDto moduleDto = new ModuleDto();
		moduleDto.setModuleId(1);
		moduleDto.setModuleName("module");

		WorkSpaceDto workSpaceDto = new WorkSpaceDto();
		workSpaceDto.setWorkSpaceId(1);
		workSpaceDto.setWorkSpaceName("workspace");

		UserProfileDto userProfileDto = new UserProfileDto();
		userProfileDto.setActive(true);
		userProfileDto.setUserId("DN89578");
		userProfileDto.setUserProfileId(1);
		userProfileDto.setModule(moduleDto);
		userProfileDto.setWorkSpace(workSpaceDto);
		Set<UserProfileDto> userProfile = new HashSet<>();
		userProfile.add(userProfileDto);
		return userProfile;
	}

	private Set<GlobalProfileDto> setGlobalProfileDetails() {

		ModuleDto moduleDto = new ModuleDto();
		moduleDto.setModuleId(1);
		moduleDto.setModuleName("module");

		WorkSpaceDto workSpaceDto = new WorkSpaceDto();
		workSpaceDto.setWorkSpaceId(1);
		workSpaceDto.setWorkSpaceName("workspace");

		RoleDto roleObj = new RoleDto();
		roleObj.setRoleId(0);

		GlobalProfileDto globalProfileDto = new GlobalProfileDto();

		globalProfileDto.setModule(moduleDto);
		globalProfileDto.setWorkSpace(workSpaceDto);
		globalProfileDto.setGlobalProfileId(1);
		globalProfileDto.setRole(roleObj);

		Set<GlobalProfileDto> globalProfile = new HashSet<>();
		globalProfile.add(globalProfileDto);
		return globalProfile;

	}

}
