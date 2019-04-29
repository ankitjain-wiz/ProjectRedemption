package test;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class JAXBXMLToJava {
	public static void main(String[] args) {

		try {

			// create JAXB context and initializing Marshaller
			JAXBContext jaxbContext = JAXBContext.newInstance(DataType.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			// specify the location and name of xml file to be read
			File XMLfile = new File(
					"D:\\EUIPO\\ERI_code\\Mar-6-2019\\ContentDownloadStub\\ContentDownloadStub\\src\\main\\java\\xml\\test\\test.xml");

			// this will create Java object - country from the XML file
			DataType dataType = (DataType) jaxbUnmarshaller.unmarshal(XMLfile);
			
			System.out.println(dataType.toString());

		} catch (JAXBException e) {
			// some exception occured
			e.printStackTrace();
		}

	}
}