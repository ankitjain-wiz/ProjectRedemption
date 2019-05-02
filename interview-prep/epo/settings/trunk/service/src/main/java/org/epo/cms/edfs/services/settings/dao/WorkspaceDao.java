package org.epo.cms.edfs.services.settings.dao;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.dao.GenericDao;
import org.epo.cms.edfs.services.dossierpersistence.entity.WorkSpace;
import org.epo.cms.edfs.services.settings.dto.WorkSpaceDto;

/**
 * workspace data in workspaceDTO format.
 * 
 * @author dinagar
 *
 */
public interface WorkspaceDao extends GenericDao<WorkSpace, Integer> {

  /**
 * @param workspace : String
 * @param roleId : long
 * @return WorkSpaceDto : WorkSpaceDto
 * @throws CustomException : CustomException
 */
WorkSpaceDto getWorkSpace(String workspace, long roleId) throws CustomException;

}
