package org.epo.cms.edfs.services.settings.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PersonalTagList response to PersonalTag section response
 * @author dinagar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonalTagList {

	@JsonProperty("tag")
	private String tag;

	@JsonProperty("tagLevel")
	private String tagLevel;

	@JsonProperty("titleName")
	private String titleName;

	@JsonProperty("personalTagGlobalSettingMappingId")
	private long personalTagGlobalSettingMappingId;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTagLevel() {
		return tagLevel;
	}

	public void setTagLevel(String tagLevel) {
		this.tagLevel = tagLevel;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public long getPersonalTagGlobalSettingMappingId() {
		return personalTagGlobalSettingMappingId;
	}

	public void setPersonalTagGlobalSettingMappingId(long personalTagGlobalSettingMappingId) {
		this.personalTagGlobalSettingMappingId = personalTagGlobalSettingMappingId;
	}

	@Override
	public String toString() {
		return "PersonalTagList [tag=" + tag + ", tagLevel=" + tagLevel + ", titleName=" + titleName
				+ ", personalTagGlobalSettingMappingId=" + personalTagGlobalSettingMappingId + "]";
	}
}
