package org.epo.cms.edfs.services.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection of utility methods for operating on lists.
 * 
 * @author PV53311
 *
 */
public final class ListUtils {

  /**
   * Private constructor.
   */
  private ListUtils() {}


  /**
   * Creates a list containing the number starting from the provided from value upto the provided to
   * value. Both values included.
   * 
   * @param from - The value to start from,l is included in the resulting list
   * @param to - The value to end with, is included in hte resulting list
   * @return - List of Integers includuding the numbers between fromn and to both inclusive [from,
   *         to]
   */
  public static List<Integer> listOfIntegers(final int from, final int to) {
    List<Integer> results = new ArrayList<>();

    for (int cnt = from; cnt <= to; cnt++) {
      results.add(cnt);
    }

    return results;
  }


  /**
   * Chunks up the given list into numberOfParts sublists. The aim is for the sublists to have as
   * equal as possible size.
   * 
   * @param sourceList - The list to chopIntoParts
   * @param numberOfParts - The number of desidered parts
   * @return - Lists of choppedup lists.
   */
  public static <T> List<List<T>> chopIntoParts(final List<T> sourceList, final int numberOfParts) {
    final List<List<T>> lsParts = new ArrayList<>();
    final int chunkSize = sourceList.size() / numberOfParts;
    int leftOver = sourceList.size() % numberOfParts;
    int toTake;
    for (int i = 0, iT = sourceList.size(); i < iT; i += toTake) {
      if (leftOver > 0) {
        leftOver--;

        toTake = chunkSize + 1;
      } else {
        toTake = chunkSize;
      }

      lsParts.add(new ArrayList<T>(sourceList.subList(i, Math.min(iT, i + toTake))));
    }

    return lsParts;
  }

}
