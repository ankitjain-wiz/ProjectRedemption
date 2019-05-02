package org.epo.cms.edfs.services.settings.enums;

/**
 * Enum to hold Section Names
 * @author ankitjain2
 *
 */
public enum SectionName {
	DISPLAY("Display"), HELPER("Helpers"), PERSONALTAG("Personal tags"), PRINTOPTION(
					"Printing Defaults"), NOTIFICATION("Notifications"), CaseSampling("Case Sampling"), TimeLimit("Time Limit");

	private final String value;

	/**
	 * @param value : String
	 */
	private SectionName(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

}
