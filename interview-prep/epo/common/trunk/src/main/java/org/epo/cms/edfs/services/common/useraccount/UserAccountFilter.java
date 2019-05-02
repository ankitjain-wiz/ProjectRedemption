package org.epo.cms.edfs.services.common.useraccount;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.epo.cms.edfs.services.common.beans.UserAccountDetail;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

final class UserAccountFilter {
	private static final Logger LOGGER = LogManager.getLogger();

	/**
	 * Default constructor.
	 */
	public UserAccountFilter() {
	}

	/**
	 * Iterates over the UserAccount element in the userDetails response and
	 * extracts the relevant fields to populate a UserAccountDetail object. All
	 * UserAccountDetail objects are aggregated in a Mpa using as key to the
	 * UserAccountDetails the UserId attribute.
	 * 
	 * @param userDetails
	 *            - The json response which contains the returned
	 *            UserAccountDetails elemnents
	 * @return Map containing all UserAccountDetail instances from the response
	 */
	public Map<String, UserAccountDetail> getUserAccountDetails(final String userDetails) {

		ObjectMapper ob = new ObjectMapper();
		JsonNode node = null;
		Map<String, UserAccountDetail> userAccountDetailsMap = new ConcurrentHashMap<>();

		try {
			node = ob.readValue(userDetails, JsonNode.class);
			int numberOfResults = getNumberOfResults(node);
			JsonNode result = getResultsNode(node);

			if (numberOfResults > 1) {
				userAccountDetailsMap = processMultipleResults(result, numberOfResults);
			} else if (numberOfResults == 1) {
				userAccountDetailsMap = processSingleResult(result);
			}
		} catch (IOException e) {
			LOGGER.error("Exception occured while creating the UserAccountDetails.", e);
		}

		return userAccountDetailsMap;
	}

	private int getNumberOfResults(final JsonNode node) {
		/*
		 * When only one single result is returned as part of the response, the
		 * "_embedded results object seems to be containing 2 items. For the
		 * example see the file listAccountDetailsSingleResult.json in the
		 * test/resources folder. In case there are multiple results, then the
		 * number of results is the number of UserAccount elements in the
		 * result. See the file ListAccountsPage1.json in the test/resources
		 * folder.
		 */
		int numberOfResults = 0;
		JsonNode totalResults = getTotalResultsNode(node);
		if (totalResults != null) {
			numberOfResults = totalResults.get("#text").asInt();
			LOGGER.debug("The number of results contained in the response is {}", numberOfResults);
		}

		if (numberOfResults > 1) {
			JsonNode results = getResultsNode(node);
			numberOfResults = results.size();
		}

		return numberOfResults;
	}

	private JsonNode getTotalResultsNode(final JsonNode node) {
		return node.path("List_user_accounts").path("paging").path("totalresults");
	}

	private JsonNode getResultsNode(final JsonNode node) {
		return node.path("List_user_accounts").path("_embedded").get("results");
	}

	private Map<String, UserAccountDetail> processMultipleResults(final JsonNode node, final int numberOfResults) {
		Map<String, UserAccountDetail> results = new ConcurrentHashMap<>();

		for (int i = 0; i < numberOfResults; i++) {
			JsonNode userAccountNode = node.get(i).path("User_account");
			UserAccountDetail userAccount = createUserAccountDetail(userAccountNode);
			results.put(userAccount.getUserID(), userAccount);
		}

		return results;
	}

	private Map<String, UserAccountDetail> processSingleResult(final JsonNode node) {
		Map<String, UserAccountDetail> results = new ConcurrentHashMap<>();

		JsonNode userAccountNode = node.path("results").path("User_account");
		UserAccountDetail userAccount = createUserAccountDetail(userAccountNode);
		results.put(userAccount.getUserID(), userAccount);

		return results;
	}

	private UserAccountDetail createUserAccountDetail(final JsonNode userAccountNode) {
		UserAccountDetail userAccount = new UserAccountDetail();

		userAccount.setCaseName(stringEncoding(userAccountNode.get("CASENAME").asText()));
		userAccount.setFamilyName(stringEncoding(userAccountNode.get("familyName").asText()));
		userAccount.setUserID(userAccountNode.get("UserID").asText());
		userAccount.setEmail(userAccountNode.get("email").asText());
		userAccount.setGivenName(stringEncoding(userAccountNode.get("givenName").asText()));
		userAccount.setPreferredLanguage(userAccountNode.get("PreferredLanguage").asText());
		userAccount.setOrganisation(userAccountNode.get("Organisation").asText());
		userAccount.setOrganisationID(userAccountNode.get("OrganisationID").asText());
		userAccount.setLocation(userAccountNode.get("location").asText());
		userAccount.setTelephoneNumber(userAccountNode.get("telephoneNumber").asText());
		setRoleFromCache(userAccountNode, userAccount);

		return userAccount;
	}

	private void setRoleFromCache(final JsonNode userAccountNode, UserAccountDetail userAccount) {
		JsonNode roleName = userAccountNode.path("RoleName");
		if (!roleName.isMissingNode()) {
			if (roleName.isArray()) {
				String role = roleName.get(0).asText();
				userAccount.setRole(role);
			} else {
				String role = roleName.get("option").asText();
				userAccount.setRole(role);
			}
		} else {
			userAccount.setRole("Tester");
		}
	}

	private String stringEncoding(String value) {
		if (StringUtils.isEmpty(value)) {
			return value;
		}
		byte[] stringBytes = value.getBytes(StandardCharsets.ISO_8859_1);
		return new String(stringBytes, StandardCharsets.UTF_8);
	}

}
