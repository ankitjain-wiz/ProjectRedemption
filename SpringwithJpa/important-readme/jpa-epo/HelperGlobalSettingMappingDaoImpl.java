package org.epo.cms.edfs.services.settings.dao.impl;

import java.util.List;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.impl.GenericDaoImpl;
import org.epo.cms.edfs.services.dossierpersistence.entity.Helper;
import org.epo.cms.edfs.services.dossierpersistence.entity.HelperGlobalSettingMapping;
import org.epo.cms.edfs.services.dossierpersistence.entity.Role;
import org.epo.cms.edfs.services.settings.dao.HelperGlobalSettingMappingDao;
import org.epo.cms.edfs.services.settings.dto.HelperDto;
import org.epo.cms.edfs.services.settings.dto.HelperGlobalSettingMappingDto;
import org.epo.cms.edfs.services.settings.dto.RoleDto;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

/**
 * 
 * @author dinagar
 *
 */
@Repository
public class HelperGlobalSettingMappingDaoImpl extends
    GenericDaoImpl<HelperGlobalSettingMapping, Integer> implements HelperGlobalSettingMappingDao {

  
  
	

/**
 * {@inheritDoc}
 */
@Override
  public HelperGlobalSettingMapping updateHelperGlobalSettingMapping(
      HelperGlobalSettingMappingDto helperGlobalSettingMappingDto) throws CustomException {
    HelperGlobalSettingMapping helperGlobalSettingMapping = getHelperGlobalSettingMapping(
        helperGlobalSettingMappingDto.getHelper(), helperGlobalSettingMappingDto.getRole());
    if (!ObjectUtils.isEmpty(helperGlobalSettingMapping)) {
      remove(helperGlobalSettingMapping);
    } 
    if(helperGlobalSettingMappingDto.isCheck())
    {
      Helper helper = new Helper();
      Role role = new Role();
      role.setRoleId(helperGlobalSettingMappingDto.getRole().getRoleId());
      helperGlobalSettingMapping = new HelperGlobalSettingMapping();
      helper.setHelperId(helperGlobalSettingMappingDto.getHelper().getHelperId());
      helper.setHelperName(helperGlobalSettingMappingDto.getHelper().getHelperName());
      role.setRoleId(helperGlobalSettingMappingDto.getRole().getRoleId());
      helperGlobalSettingMapping.setHelper(helper);
      helperGlobalSettingMapping.setRole(role);
      saveOrUpdate(helperGlobalSettingMapping);
      
    }
    return helperGlobalSettingMapping;
  }

  /**
 * @param helperDto : HelperDto
 * @param roleDto :RoleDto
 * @return HelperGlobalSettingMapping : HelperGlobalSettingMapping
 */
private HelperGlobalSettingMapping getHelperGlobalSettingMapping(HelperDto helperDto,
      RoleDto roleDto) {
    Helper helper = new Helper();
    helper.setHelperId(helperDto.getHelperId());
    Role role = new Role();
    role.setRoleId(roleDto.getRoleId());
    Criteria criteria = currentSession().createCriteria(HelperGlobalSettingMapping.class);
    criteria.add(Restrictions.eq("helper", helper)).add(Restrictions.eq("role", role));
         
    return  (HelperGlobalSettingMapping) criteria.uniqueResult();
  }


/**
 * {@inheritDoc}
 */
  @Override
  public void removeHelperGlobalMapping(long roleId) {
    Role role = new Role();
    role.setRoleId(roleId);
    Criteria criteria = currentSession().createCriteria(HelperGlobalSettingMapping.class);
    criteria.add(Restrictions.eq("role", role));
    List<HelperGlobalSettingMapping> helperGlobalSettingsMappingList = criteria.list();
    for(HelperGlobalSettingMapping helperGlobalSettingMapping : helperGlobalSettingsMappingList){
      remove(helperGlobalSettingMapping);
    }
  }
}
