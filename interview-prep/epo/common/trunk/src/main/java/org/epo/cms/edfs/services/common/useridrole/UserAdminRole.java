package org.epo.cms.edfs.services.common.useridrole;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Testing Assigns userid and roles to different user This class is used in
 * testing env and should be removed in epo env
 * 
 * @author ankitjain2
 *
 */
@Component
@Qualifier("userAdminRole")
public class UserAdminRole {

	
	private String userId;
	private String role;
	private String user;
	private String organizationId;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	

}
