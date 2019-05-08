package com.hash;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Hashing {

	public static void main(String[] args) {
		
		Set<String> ankitset=new HashSet<>();
		System.out.println(ankitset.add("first"));
		System.out.println(ankitset.add("second"));
		System.out.println(ankitset.add(null));   // allows storing of null element
		System.out.println(ankitset.add("first"));
		System.out.println("\n");
		ankitset.stream().forEach(System.out::print);  // donot maintains order
		
		
		Map<String,Integer> ankitMap=new HashMap<>();
		System.out.println("\n");
		System.out.println(ankitMap.put("first", 10));
		System.out.println(ankitMap.put("second", 2));
		System.out.println(ankitMap.put(null, 3));
		System.out.println(ankitMap.put(null, 4)); // single null key and any number of null values
		System.out.println(ankitMap.put("first", 5));
		
		ankitMap.entrySet().stream().forEach(x->{
			System.out.println(x.getKey() +"--"+	x.getValue());
			});
		
		
		ankitMap.entrySet().stream().forEach(x->{
			System.out.println(x.getKey() +"--"+	x.getValue());
			});
		
		ankitMap.forEach((k,v)->{
			System.out.println(k +"--"+	v);
		});
		
		//https://www.novixys.com/blog/java-hashmap-examples/
		//https://www.geeksforgeeks.org/internal-working-of-hashmap-java/
		//https://dzone.com/articles/hashmap-custom-implementation
		//https://www.javamadesoeasy.com/2015/02/hashmap-custom-implementation-put-get.html
		
		
		
		

	}

}
