package com.inner;
public class MainClass {

	static {
		class Foo {
			
		}
		Foo f = new Foo();
	}
	
	public void bar() {
		if(1 < 2) {
			class Test {
				
			}
			Test t1 = new Test();
		}
		// Below will throw error because of the scope of the class
		//Test t = new Test();
		//Foo f = new Foo();
	}
	
	public static void main(String[] args) {
		MainClass m=new MainClass();
		m.bar();
	}
}