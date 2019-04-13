package com.soap;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.codenotfound.services.helloworld.HelloWorldPortType;
import com.codenotfound.services.helloworld.HelloWorldService;
import com.codenotfound.types.helloworld.Greeting;
import com.codenotfound.types.helloworld.ObjectFactory;
import com.codenotfound.types.helloworld.Person;

public class HelloWorldClient{
	
	public static void main(String[] args) throws Exception {
	   
	URL url = new URL("http://localhost:8086/mockHelloWorld_SoapBinding?wsdl");
	
        //1st argument service URI, refer to wsdl document above
	//2nd argument is service name, refer to wsdl document above
        QName qname = new QName("http://codenotfound.com/services/helloworld", "HelloWorld_Service");

        Service service = Service.create(url, qname);

        HelloWorldPortType hello = service.getPort(HelloWorldPortType.class);
        
        ObjectFactory factory = new ObjectFactory();
    	Person person = factory.createPerson();
        person.setFirstName("sdfs");
        person.setLastName("ada");
    	

        System.out.println(hello.sayHello(person).getGreeting());

    }

}