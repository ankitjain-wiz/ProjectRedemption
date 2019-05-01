package com.wahisplus.in.dao.outbreak;

import com.wahisplus.in.dto.review.EnterDto;
import com.wahisplus.in.dto.review.SendReportDto;

/**
 * This is dao repositary for report table saving the data in database wherein.
 * 
 * @author soprasteria
 * @version 1.0
 *
 */
public interface ReviewDao {

  /**
   * Populate datafor sender.
   *
   * @param country the country
   * @return the send report dto
   */
  SendReportDto populateDataforSender(int country);

  /**
   * Populate datafor enter.
   *
   * @param userId the user id
   * @return the enter dto
   */
  EnterDto populateDataforEnter(int userId);

}
