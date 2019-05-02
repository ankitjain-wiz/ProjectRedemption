package org.epo.cms.edfs.services.settings.beans;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * PrintOptionList pojo object.
 * 
 * @author dinagar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrintOptionList {

  private boolean selected;

  private String printAdditionalOption;

  private long printOptionId;

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public String getPrintAdditionalOption() {
    return printAdditionalOption;
  }

  public void setPrintAdditionalOption(String printAdditionalOption) {
    this.printAdditionalOption = printAdditionalOption;
  }

  public long getPrintOptionId() {
    return printOptionId;
  }

  public void setPrintOptionId(long printOptionId) {
    this.printOptionId = printOptionId;
  }

  @Override
  public String toString() {
    return "PrintOptionList [selected=" + selected + ", printAdditionalOption="
        + printAdditionalOption + ", printOptionId=" + printOptionId + "]";
  }
}
