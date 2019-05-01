package com.wahisplus.in.dao.outbreak;

import java.util.List;

/**
 * This interface is used to save and update the quatitive date.
 * 
 * @author soprasteria.
 *
 */
public interface QuantitiveDao {

  /**
   * Save and update quantitive details.
   *
   * @param epiEventId the epi event id
   */
  void saveAndUpdateQuantitiveDetails(List<Long> epiEventIdList);

}
