package com.leetcode.linkedlist;

public class LinkListCycle {

	Node head;

	public static void main(String[] args) {
		Node one=new Node();
		Node two=new Node();
		Node three=new Node();
		Node four=new Node();
		Node five=new Node();
		Node six=new Node();
		
		one.value=1;
		one.next=two;
				
		two.value=2;
		two.next=three;
				
		three.value=3;
		three.next=four;
				
		four.value=4;
		four.next=five;
		
		five.value=5;
		five.next=six;
		
		
		six.value=6;
		six.next=three;
		
		LinkListCycle list=new LinkListCycle();
		list.head=one;
		System.out.println("hello"+list.hasCycle(one));
		Node returned=list.detectCycle(one);
		System.out.println("hello"+returned.value);

	}

	public Node detectCycle(Node head) {
		
		Node traverserOne=head;
		Node traverserTwo=head;
		while(traverserOne !=null ||traverserTwo !=null) {
			
			traverserOne=traverserOne.next;
			traverserTwo=traverserTwo.next.next;
			if(traverserOne==traverserTwo) {
				System.out.println(traverserOne.value);
				return traverserOne;
				
			}
		}
		return null;
	}

	public boolean hasCycle(Node head) {
		
		Node traverserOne=head;
		Node traverserTwo=head;
		while(traverserOne !=null ||traverserTwo !=null) {
			
			traverserOne=traverserOne.next;
			traverserTwo=traverserTwo.next.next;
			if(traverserOne==traverserTwo) {
				return true;
			}
		}
		return false;
	}

}
