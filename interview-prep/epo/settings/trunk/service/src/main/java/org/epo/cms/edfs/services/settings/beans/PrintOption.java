package org.epo.cms.edfs.services.settings.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * printOption response obiect class.
 * 
 * @author dinagar
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrintOption {

  private boolean resetToDefault;

  private String sectionName;

  private List<PrintOptionList> printOptionList;


  public List<PrintOptionList> getPrintOptionList() {
    return printOptionList;
  }

  public void setPrintOptionList(List<PrintOptionList> printOptionList) {
    this.printOptionList = printOptionList;
  }

  public boolean isResetToDefault() {
    return resetToDefault;
  }

  public void setResetToDefault(boolean resetToDefault) {
    this.resetToDefault = resetToDefault;
  }

  public String getSectionName() {
    return sectionName;
  }

  public void setSectionName(String sectionName) {
    this.sectionName = sectionName;
  }

  @Override
  public String toString() {
    return "PrintOption [resetToDefault=" + resetToDefault + ", sectionName=" + sectionName
        + ", printOptionList=" + printOptionList + "]";
  }

}
