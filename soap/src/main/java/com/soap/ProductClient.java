package com.soap;

import com.codenotfound.services.helloworld.HelloWorldPortType;
import com.codenotfound.services.helloworld.HelloWorldService;
import com.codenotfound.types.helloworld.Greeting;
import com.codenotfound.types.helloworld.ObjectFactory;
import com.codenotfound.types.helloworld.Person;

public class ProductClient {

    public static void main(String[] args) {
        try {
        	HelloWorldService productServiceSvc = new HelloWorldService();
        	HelloWorldPortType productService = productServiceSvc.getHelloWorldPort();
        	ObjectFactory factory = new ObjectFactory();
        	Person person = factory.createPerson();
            person.setFirstName("sdfs");
            person.setLastName("ada");
        	Greeting product = productService.sayHello(person);
        	System.out.println(product.getGreeting());
            } catch (Exception e) {
            e.printStackTrace();
        }
    }

}