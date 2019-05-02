package org.epo.cms.edfs.services.settings.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.common.exceptions.ResponseValidator;
import org.epo.cms.edfs.services.common.util.PropertyFileReader;
import org.epo.cms.edfs.services.dossierpersistence.entity.GlobalProfile;
import org.epo.cms.edfs.services.dossierpersistence.entity.HelperGlobalSettingMapping;
import org.epo.cms.edfs.services.dossierpersistence.entity.PersonalTagGlobalSettingMapping;
import org.epo.cms.edfs.services.settings.beans.HelperList;
import org.epo.cms.edfs.services.settings.beans.NotificationFilter;
import org.epo.cms.edfs.services.settings.beans.PersonalTagList;
import org.epo.cms.edfs.services.settings.beans.RoleDetail;
import org.epo.cms.edfs.services.settings.beans.RoleSettingResponse;
import org.epo.cms.edfs.services.settings.beans.SettingsRequest;
import org.epo.cms.edfs.services.settings.beans.UpdaterSettingsResponse;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(PowerMockRunner.class)
@PrepareForTest(GlobalSettingsServiceImpl.class)
@PowerMockIgnore("javax.management.*")
public class GlobalSettingsServiceImplTest {

	private static final String ROLE_DESCRIPTION = "role description";

	@InjectMocks
	GlobalSettingsServiceImpl globalSettingsService;

	@Mock
	private RoleDao roleDao;

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
	private GlobalProfileDao globalProfileDAO;

	@Mock
	private PrintGlobalSettingMappingDao printGlobalSettingMappingDao;

	@Mock
	private HelperGlobalSettingMappingDao helperGlobalSettingMappingDao;

	@Mock
	private PersonalTagGlobalSettingMappingDao personalTagGlobalSettingMappingDAO;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	protected PropertyFileReader propertyFileReader;

	@Mock
	private ResponseValidator responseValidator;

	@Mock
	private HelperDao helperDAO;

	@Mock
	private NotificationUtils notificationUtils;

	RoleSettingResponse roleSettingResponse;
	SettingsRequest settingsRequest;

	@Before
	public void setUp() throws CustomException {
		roleSettingResponse = new RoleSettingResponse();
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
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetGlobalSettings() throws Exception {
		RoleDto roleObj = getRoleDto();
		List<HelperDto> helperDtoList = getHelperDtoList();
		Mockito.when(roleDao.getRole(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(roleObj);
		Mockito.when(helperDAO.getHelperList()).thenReturn(helperDtoList);

		Mockito.when(restTemplate.getForObject(Matchers.eq("hostForNotificationFilterchairman"),
				Matchers.<Class<String>>any())).thenReturn("TestJson");
		Mockito.when(propertyFileReader.getHostForCMS(Mockito.anyString())).thenReturn("hostForNotificationFilter");
		Mockito.when(globalProfileDAO.getGlobalProfileAlertLevel(Mockito.any(), Mockito.any(), Mockito.any()))
				.thenReturn("low");

		NotificationFilter notificationFilter = Mockito.mock(NotificationFilter.class);
		Mockito.when(notificationUtils.getNotificationFilter(Mockito.any())).thenReturn(notificationFilter);
		ObjectMapper objectMapper = PowerMockito.mock(ObjectMapper.class);
		PowerMockito.whenNew(ObjectMapper.class).withNoArguments().thenReturn(objectMapper);
		Mockito.when(objectMapper.readValue(Mockito.anyString(), Matchers.<Class<NotificationFilter>>any()))
				.thenReturn(notificationFilter);

		roleSettingResponse = globalSettingsService.getGlobalSettings("chairman");
		Set<ModuleDto> moduleSet = roleObj.getModule();
		List<ModuleDto> moduleDtoList = new ArrayList<>(moduleSet);
		assertEquals(moduleDtoList.get(0).getModuleName(),
				roleSettingResponse.getModule().getModuleList().get(0).getModuleName());
	}

	@Test
	public void testGetGlobalSettingsEmptyJsonResp() throws Exception {
		RoleDto roleObj = getRoleDto();
		List<HelperDto> helperDtoList = getHelperDtoList();
		Mockito.when(roleDao.getRole(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(roleObj);
		Mockito.when(helperDAO.getHelperList()).thenReturn(helperDtoList);

		Mockito.when(restTemplate.getForObject(Matchers.eq("hostForNotificationFilterchairman"),
				Matchers.<Class<String>>any())).thenReturn("");
		Mockito.when(propertyFileReader.getHostForCMS(Mockito.anyString())).thenReturn("hostForNotificationFilter");

		NotificationFilter notificationFilter = Mockito.mock(NotificationFilter.class);
		ObjectMapper objectMapper = PowerMockito.mock(ObjectMapper.class);
		PowerMockito.whenNew(ObjectMapper.class).withNoArguments().thenReturn(objectMapper);
		Mockito.when(objectMapper.readValue(Mockito.anyString(), Matchers.<Class<NotificationFilter>>any()))
				.thenReturn(notificationFilter);

		roleSettingResponse = globalSettingsService.getGlobalSettings("chairman");
		Set<ModuleDto> moduleSet = roleObj.getModule();
		List<ModuleDto> moduleDtoList = new ArrayList<>(moduleSet);
		assertEquals(moduleDtoList.get(0).getModuleName(),
				roleSettingResponse.getModule().getModuleList().get(0).getModuleName());
	}

	@SuppressWarnings("unchecked")
	@Test(expected = RestClientException.class)
	public void testGetGlobalSettingsRestException() throws Exception {
		RoleDto roleObj = getRoleDto();
		List<HelperDto> helperDtoList = getHelperDtoList();
		Mockito.when(roleDao.getRole(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(roleObj);
		Mockito.when(helperDAO.getHelperList()).thenReturn(helperDtoList);

		Mockito.when(notificationUtils.getNotificationFilter(Mockito.any())).thenThrow(RestClientException.class);

		Mockito.when(propertyFileReader.getHostForCMS(Mockito.anyString())).thenReturn("hostForNotificationFilter");

		NotificationFilter notificationFilter = Mockito.mock(NotificationFilter.class);
		ObjectMapper objectMapper = PowerMockito.mock(ObjectMapper.class);
		PowerMockito.whenNew(ObjectMapper.class).withNoArguments().thenReturn(objectMapper);
		Mockito.when(objectMapper.readValue(Mockito.anyString(), Matchers.<Class<NotificationFilter>>any()))
				.thenReturn(notificationFilter);

		roleSettingResponse = globalSettingsService.getGlobalSettings("chairman");
		Set<ModuleDto> moduleSet = roleObj.getModule();
		List<ModuleDto> moduleDtoList = new ArrayList<>(moduleSet);
		assertEquals(moduleDtoList.get(0).getModuleName(),
				roleSettingResponse.getModule().getModuleList().get(0).getModuleName());
	}

	@Test(expected = IOException.class)
	public void testGetGlobalSettingsIoException() throws Exception {
		RoleDto roleObj = getRoleDto();
		List<HelperDto> helperDtoList = getHelperDtoList();
		Mockito.when(roleDao.getRole(Mockito.anyString(), Mockito.anyBoolean())).thenReturn(roleObj);
		Mockito.when(helperDAO.getHelperList()).thenReturn(helperDtoList);

		Mockito.when(restTemplate.getForObject(Matchers.eq("hostForNotificationFilterchairman"),
				Matchers.<Class<String>>any())).thenReturn("TestJson");
		Mockito.when(propertyFileReader.getHostForCMS(Mockito.anyString())).thenReturn("hostForNotificationFilter");

		NotificationFilter notificationFilter = Mockito.mock(NotificationFilter.class);
		ObjectMapper objectMapper = PowerMockito.mock(ObjectMapper.class);
		PowerMockito.whenNew(ObjectMapper.class).withNoArguments().thenReturn(objectMapper);
		Mockito.when(notificationUtils.getNotificationFilter(Mockito.any())).thenThrow(IOException.class);

		roleSettingResponse = globalSettingsService.getGlobalSettings("chairman");
		Set<ModuleDto> moduleSet = roleObj.getModule();
		List<ModuleDto> moduleDtoList = new ArrayList<>(moduleSet);
		assertEquals(moduleDtoList.get(0).getModuleName(),
				roleSettingResponse.getModule().getModuleList().get(0).getModuleName());
	}

	@Test
	public void testGetRoleList() throws CustomException {
		RoleDto roleObj = getRoleDto();
		roleObj.setRoleName("chairman");
		RoleDetail roleDetails = new RoleDetail();
		List<RoleDto> roleDtos = new ArrayList<>();
		roleDtos.add(roleObj);
		Mockito.when(roleDao.getRoleList()).thenReturn(roleDtos);
		roleDetails = globalSettingsService.getRoleList();
		assertEquals(roleDetails.getRole().get(0).getRoleName(), roleDtos.get(0).getRoleName());
	}

	@Test
	public void testUpdateSettingsDetails() throws CustomException {
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
		GlobalProfileDto globalProfileDto = new GlobalProfileDto();
		globalProfileDto.setModule(moduleDtoList.get(0));
		globalProfileDto.setWorkSpace(workSpaceDtoList.get(0));
		globalProfileDto.setRole(roleObj);
		globalProfileDto.setAlertLevel(settingsRequest.getNotificationAlertLevel());
		Set<PrintGlobalSettingMappingDto> dtos = getPrintOptionDetails();
		List<PrintGlobalSettingMappingDto> dtosList = new ArrayList<>(dtos);
		Mockito.when(printOptionDao.getPrintOption(settingsRequest.getPrintAdditionalOption()))
				.thenReturn(dtosList.get(0).getPrintOption());
		PrintGlobalSettingMappingDto printGlobalSettingMappingDto = new PrintGlobalSettingMappingDto();
		printGlobalSettingMappingDto.setPrintOption(dtosList.get(0).getPrintOption());
		printGlobalSettingMappingDto.setRole(roleObj);

		for (HelperList helperList : settingsRequest.getHelperList()) {
			HelperGlobalSettingMappingDto helperGlobalSettingMappingDto = new HelperGlobalSettingMappingDto();
			Set<HelperGlobalSettingMappingDto> heDtos = getHelperDetails();
			List<HelperGlobalSettingMappingDto> heDtosList = new ArrayList<>(heDtos);
			Mockito.when(helperDAO.getHelperDetails(helperList.getHelperName(), helperList.getModuleSpace()))
					.thenReturn(heDtosList.get(0).getHelper());

			helperGlobalSettingMappingDto.setHelper(heDtosList.get(0).getHelper());
			helperGlobalSettingMappingDto.setRole(roleObj);
			HelperGlobalSettingMapping helperGlobalSettingMapping = new HelperGlobalSettingMapping();
			helperGlobalSettingMapping.setHelperGlobalSettingMappingId(1);
			Mockito.when(helperGlobalSettingMappingDao.updateHelperGlobalSettingMapping(helperGlobalSettingMappingDto))
					.thenReturn(helperGlobalSettingMapping);
		}
		for (PersonalTagList personalTagList : settingsRequest.getPersonalTagList()) {
			PersonalTagGlobalSettingMappingDto dto = new PersonalTagGlobalSettingMappingDto();
			Set<PersonalTagGlobalSettingMappingDto> personalTagGlobalSettingMappingDtos = getPersonalTagDetails();
			List<PersonalTagGlobalSettingMappingDto> personalTagGlobalSettingMappingDtoList = new ArrayList<>(
					personalTagGlobalSettingMappingDtos);
			Mockito.when(personalTagDAO.getPersonalTag(personalTagList.getTag()))
					.thenReturn(personalTagGlobalSettingMappingDtoList.get(0).getPersonalTag());
			Mockito.when(personalLevelDao.getPersonalLevel(personalTagList.getTagLevel()))
					.thenReturn(personalTagGlobalSettingMappingDtoList.get(0).getPersonalLevel());

			dto.setPersonalLevel(personalTagGlobalSettingMappingDtoList.get(0).getPersonalLevel());
			dto.setPersonalTag(personalTagGlobalSettingMappingDtoList.get(0).getPersonalTag());
			dto.setRole(roleObj);
			dto.setTitleName(settingsRequest.getPersonalTagList().get(0).getTitleName());
			PersonalTagGlobalSettingMapping personalTagGlobalSettingMapping = new PersonalTagGlobalSettingMapping();
			personalTagGlobalSettingMapping.setPersonalTagGlobalSettingMappingId(1);
			Mockito.when(personalTagGlobalSettingMappingDAO.updatePersonalTagGlobalSettingMapping(dto))
					.thenReturn(personalTagGlobalSettingMapping);
		}
		GlobalProfile globalProfile = new GlobalProfile();
		Mockito.when(globalProfileDAO.updateGlobalProfile(globalProfileDto)).thenReturn(globalProfile);
		UpdaterSettingsResponse updaterSettingsResponse = globalSettingsService.updateSettingsDetails(settingsRequest,
				"chirman", Mockito.anyString());
		assertEquals(updaterSettingsResponse.getDescription(), SettingsConstants.UPDATE_SUCCESS);
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

	private Set<GlobalProfileDto> getGlobalProfileDetails() {
		GlobalProfileDto globalProfile = new GlobalProfileDto();
		globalProfile.setGlobalProfileId(1);
		globalProfile.setAlertLevel("low");
		Set<GlobalProfileDto> globalProfileDtos = new HashSet<>();
		return globalProfileDtos;
	}

	private List<HelperDto> getHelperDtoList() {
		List<HelperDto> helperDtoList = new ArrayList<>();
		HelperDto helperDto = new HelperDto();
		helperDto.setHelperId(1);
		helperDto.setHelperName("helperName");
		helperDtoList.add(helperDto);
		return helperDtoList;
	}

	private Set<HelperGlobalSettingMappingDto> getHelperDetails() {
		HelperDto helperDto = new HelperDto();
		helperDto.setHelperId(1);
		helperDto.setHelperName("helperName");
		HelperGlobalSettingMappingDto helperGlobalSettingMappingDto = new HelperGlobalSettingMappingDto();
		helperGlobalSettingMappingDto.setHelperGlobalSettingMappingId(1);
		helperGlobalSettingMappingDto.setHelper(helperDto);
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
		Set<WorkSpaceDto> workSpace = new HashSet<>();
		workSpace.add(workSpaceDto);
		return workSpace;
	}

	private Set<ModuleDto> getModuleDetails() {
		ModuleDto moduleDto = new ModuleDto();
		moduleDto.setModuleId(1);
		moduleDto.setModuleName("module");
		Set<ModuleDto> module = new HashSet<>();
		module.add(moduleDto);
		return module;
	}
}
