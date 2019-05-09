package org.epo.cms.edfs.services.casesampling.util;

import java.time.LocalDate;
import org.springframework.stereotype.Component;

/**
 * This class is used to get Current Month and year.
 * 
 * @author amigarg
 *
 */
@Component
public class DateTime {

  LocalDate currentDate;

  public DateTime() {
    currentDate = LocalDate.now();
  }

  /**
   * Get the current month.
   * 
   * @return String as value
   */
  public String getMonth() {
    currentDate = LocalDate.now();
    return currentDate.getMonth().name();
  }
  
  /**
 * @return int : int
 */
public int getMonthNumber() {
	    currentDate = LocalDate.now();
	    return currentDate.getMonthValue();
	  }

  /**
   * Get the current Year.
   * 
   * @return int as value
   */
  public int getYear() {
    int year;
    year = currentDate.getYear();
    return year;
  }

}
