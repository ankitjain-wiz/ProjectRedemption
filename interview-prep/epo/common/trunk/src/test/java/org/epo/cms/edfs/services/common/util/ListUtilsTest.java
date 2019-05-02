package org.epo.cms.edfs.services.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

public class ListUtilsTest {

    
    @Test
    public void listOfIntegerTest() {
        List<Integer> result = ListUtils.listOfIntegers(2, 10);
        assertNotNull(result);
        assertEquals(9, result.size());
    }
    
    
    @Test
    public void chopIntoNonEqualPartsTest () {
        List<Integer> source = ListUtils.listOfIntegers(2, 188);
        
        List<List<Integer>> parts = ListUtils.chopIntoParts(source, 20);
        assertNotNull(parts);
        assertEquals(20, parts.size());
        
        //The first 7 parts have 10 size
        //The other have 9 size
        assertEquals(10, parts.get(0).size());
        assertEquals(10, parts.get(6).size());
        assertEquals(9, parts.get(7).size());
        assertEquals(9, parts.get(19).size());
    }
    
    @Test
    public void chopIntoEqualsPartsTest () {
        List<Integer> source = ListUtils.listOfIntegers(1, 20);
        
        List<List<Integer>> parts = ListUtils.chopIntoParts(source, 4);
        assertNotNull(parts);
        assertEquals(4, parts.size());
        
        //All parts have 5 elements.
        assertEquals(5, parts.get(0).size());
        assertEquals(5, parts.get(1).size());
        assertEquals(5, parts.get(2).size());
        assertEquals(5, parts.get(3).size());
    }
}
