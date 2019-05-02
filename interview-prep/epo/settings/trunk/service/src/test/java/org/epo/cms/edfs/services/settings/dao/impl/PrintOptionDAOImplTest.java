package org.epo.cms.edfs.services.settings.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.epo.cms.edfs.services.common.exceptions.CustomException;
import org.epo.cms.edfs.services.dossierpersistence.entity.PrintOption;
import org.epo.cms.edfs.services.settings.dto.PrintOptionDto;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PrintOptionDaoImpl.class)
@PowerMockIgnore("javax.management.*")
public class PrintOptionDAOImplTest {

  @InjectMocks
  PrintOptionDaoImpl printOptionDAO;
  @Mock
  Criteria mockCriteria;
  
  @Mock
  Session session;
  @Mock
  SessionFactory sessionFactory;
  PrintOption printOption;
  @Before
  public void setUp() throws CustomException {
    printOption = new PrintOption();
    printOption.setPrintOptionId(1);
    printOption.setPrintAdditionalInfo("printAdditionalInfo");
    MockitoAnnotations.initMocks(this);
  }
  @Test
  public void testGetPrintOption() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(PrintOption.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.uniqueResult()).thenReturn(printOption);
    PrintOptionDto printOptionDto = printOptionDAO.getPrintOption("printAdditionalOption");
    assertNotNull(printOptionDto);
    assertEquals(printOption.getPrintOptionId(), printOptionDto.getPrintOptionId());
  }
  @Test(expected = NullPointerException.class)
  public void testGetPrintOptionException() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(PrintOption.class)).thenReturn(mockCriteria);
    Mockito.when(mockCriteria.add(Mockito.any())).thenReturn(mockCriteria);
    PrintOptionDto printOptionDto = printOptionDAO.getPrintOption("printAdditionalOption");
    assertNotNull(printOptionDto);
  }
  
  @Test
  public void testGetPrintOptionList() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(PrintOption.class)).thenReturn(mockCriteria);
    List<PrintOption> printOptionList=new ArrayList<>();
    printOptionList.add(printOption);
    Mockito.when(mockCriteria.list()).thenReturn(printOptionList);
    List<PrintOptionDto> printOptionDtoList = printOptionDAO.getPrintOptionList();
    assertEquals(printOption.getPrintOptionId(), printOptionDtoList.get(0).getPrintOptionId());
   
  }
  
  @Test
  public void testGetPrintOptionListEmpty() throws CustomException{
    mockCriteria = Mockito.mock(Criteria.class);
    Mockito.when(sessionFactory.getCurrentSession()).thenReturn(session);
    Mockito.when(session.createCriteria(PrintOption.class)).thenReturn(mockCriteria);
    
    Mockito.when(mockCriteria.list()).thenReturn(new ArrayList<>());
    List<PrintOptionDto> printOptionDtoList = printOptionDAO.getPrintOptionList();
    assertEquals(printOptionDtoList.size(), 0);
   
  }
  
}
