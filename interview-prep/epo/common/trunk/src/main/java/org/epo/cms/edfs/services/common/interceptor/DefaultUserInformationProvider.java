package org.epo.cms.edfs.services.common.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 
 * @author PV53311
 *
 */
@Component
final class DefaultUserInformationProvider implements UserInformationProvider {

    private static final Logger logger = LogManager.getLogger();
    
    /**
     * {@inheritDoc}
     */
    @Override
    public RequestUserInfo getUserInfo(final HttpServletRequest request) {
        logger.debug("Using DefaultUserInformationProvider... Extracting user principal from httprequest : " + request.getUserPrincipal());
        String userId = "UNKNOWN";
        
        if (request.getUserPrincipal() != null && (!StringUtils.isEmpty(request.getUserPrincipal().getName()))) {
            userId = request.getUserPrincipal().getName();
            logger.debug("User Id from header: " + userId);
        } 
        
        return new RequestUserInfo(userId);
    }

}
