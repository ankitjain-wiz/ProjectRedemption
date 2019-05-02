package org.epo.cms.edfs.services.common.useridrole;

/**
 * Testing Assigns userid and roles to different user 
 * This class is used in testing env and should be removed in epo env
 * 
 * @author ankitjain2
 *
 */
public enum UserAdminInfo {
	
	
	user1("ZT52731", "Director", "4"),user2("ZS88734", "Director", "4421"),user3("ZS51487", "Director","1751"),user4("ZS50957", "Director", "0610"),
	user5("ZZ53150", "Examiner", "1371"), user6("ZY51607", "Examiner", "1377"), user7("ZW89577","Examiner", "2832"),user8("ZV52297", "Examiner", "1653"),
	user9("ZN53461", "Stock Manager", "4"),user10("ZM89131", "Stock Manager", "4422"), user11("ZM52253", "Stock Manager", "1731"), user12("ZM50782", "Stock Manager", "1251"),
	user13("ZM50163", "Formalities Officer", "10005176"), user14("ZL89302", "Formalities Officer", "483"), user15("ZL51400", "Formalities Officer", "1554"),user16("ZK85039", "Formalities Officer", "4421"),
	user17("ZR53539", "Team Manager", "1952"),user18("ZR52045", "Team Manager", "1731"),user19("ZP86041", "Team Manager", "4421"),user20("ZO23734", "Team Manager", "1972"),
	user21("ZTL", "TimeLimitUser","");

	private final String userId;
	private final String role;
	private final String organizationId;
	

	/**
	 * @param userId:String
	 * @param role:String
	 */
	private UserAdminInfo(String userId, String role, String organizationId) {
		this.userId = userId;
		this.role = role;
		this.organizationId = organizationId;
		
	}

	/**
	 * @return String
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @return String
	 */
	public String getRole() {
		return role;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	
}
