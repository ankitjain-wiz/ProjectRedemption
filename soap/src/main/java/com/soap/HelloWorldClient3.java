package com.soap;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.codenotfound.services.helloworld.HelloWorldPortType;
import com.codenotfound.services.helloworld.HelloWorldService;
import com.codenotfound.types.helloworld.Greeting;
import com.codenotfound.types.helloworld.ObjectFactory;
import com.codenotfound.types.helloworld.Person;

public class HelloWorldClient3 {

	public static void main(String[] args) throws Exception {

		URL url = new URL("http://localhost:8086/mockHelloWorld_SoapBinding?wsdl");

		// 1st argument service URI, refer to wsdl document above
		// 2nd argument is service name, refer to wsdl document above
		// QName qname = new QName("http://codenotfound.com/services/helloworld",
		// "HelloWorld_Service");

		// Service service = Service.create(url, qname);

		JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
		jaxWsProxyFactoryBean.setServiceClass(HelloWorldPortType.class);
		jaxWsProxyFactoryBean.setAddress("http://localhost:8086/mockHelloWorld_SoapBinding?wsdl");
		HelloWorldPortType h = (HelloWorldPortType) jaxWsProxyFactoryBean.create();

		// HelloWorldPortType hello = service.getPort(HelloWorldPortType.class);

		ObjectFactory factory = new ObjectFactory();
		Person person = factory.createPerson();
		person.setFirstName("sdfs");
		person.setLastName("ada");

		System.out.println(h.sayHello(person).getGreeting());

	}

}