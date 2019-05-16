package test;

import java.util.Optional;

public class OptionTut {

	public static void main(String[] args) {
		Optional<String> first=Optional.empty();
		
		
		
		String name="ANKIT";
		String nameNul=null;
		Optional<String> second=Optional.of(name);
		Optional<String> third=Optional.ofNullable(nameNul);
		
		
		System.out.println(first);
		System.out.println(second);
		System.out.println(third);
		
		if(first.isPresent()) {
			System.out.println(first.get());
		}else {
			System.out.println("first is empty");
		}
		
		if(second.isPresent()) {
			System.out.println(second.get());
		}else {
			System.out.println("third is empty");
		}
		
		if(third.isPresent()) {
			System.out.println(third.get());
		}else {
			System.out.println("third is empty");
		}
		
		
		second.ifPresent(a->{
			System.out.println(a);
		});
		
		
		
		//System.out.println(third.get());
		
		
		String s="ankit";
		String s2="ankit";
		System.out.println(s==s2);
				

	}

}
