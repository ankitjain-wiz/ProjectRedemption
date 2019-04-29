package test;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class JAXBJavaToXml {
	public static void main(String[] args) {
 
		Comment comment=new Comment();
		comment.setCommentOne("CommentOne");
		comment.setCommentTwo("CommentTwo");
		
  // creating country object
   DataType dataType=new DataType(); 
   dataType.setAppealApplicationDetails("SIX");
   dataType.setApplicantDetails("SEVEN");
   dataType.setComment(comment);
   dataType.setCurrentAccountApplication("TEN");
   dataType.setDesignApplication("TWO");
   dataType.setInspectionRequestApplication("NINE");
   dataType.setOtherApplication("FOUR");
   dataType.setOtherAttachmentsDetails("TWELVE");
   dataType.setPersonsApplication("FIVE");
   dataType.setProceedingLanguageCode("THIRTEEN");
   dataType.setReimbursementApplication("ELEVEN");
   dataType.setRepresentativeDetails("EIGHT");
   dataType.setRequestType("ONE");
   dataType.setSignatoryDetails("FIFTEEN");
   dataType.setTradeMarkApplication("THREE");
   
  
   
   
   
 
  try {
 
   // create JAXB context and initializing Marshaller
   JAXBContext jaxbContext = JAXBContext.newInstance(DataType.class);
   Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
 
   // for getting nice formatted output
   jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
 
   //specify the location and name of xml file to be created
   File XMLfile = new File("D:\\EUIPO\\ERI_code\\Mar-6-2019\\ContentDownloadStub\\ContentDownloadStub\\src\\main\\java\\xml\\test\\test.xml");
 
   // Writing to XML file
   jaxbMarshaller.marshal(dataType, XMLfile); 
   // Writing to console
   jaxbMarshaller.marshal(dataType, System.out); 
 
  } catch (JAXBException e) {
   // some exception occured
   e.printStackTrace();
  }
 
 }
}