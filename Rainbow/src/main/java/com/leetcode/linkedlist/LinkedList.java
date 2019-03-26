package com.leetcode.linkedlist;

public class LinkedList {

	Node head = null;

	public void addElement(int element) {

		Node newElement = new Node();
		newElement.value = element;
		newElement.next = null;
		if (head == null) {
			head = newElement;
		}

	}

	public void traverse() {
		Node current = head;
		while (current != null) {
			System.out.println(current.value);
			current = current.next;
		}

	}

	/**
	 * Get the value of the index-th node in the linked list. If the index is
	 * invalid, return -1.
	 */
	public int get(int index) {
		Node traverser = head;
		int position = 0;
		while (traverser != null) {
			if (position == index) {
				return traverser.value;
			} else {
				position++;
				traverser = traverser.next;
			}

		}

		return -1;

	}

	/**
	 * Add a node of value val before the first element of the linked list. After
	 * the insertion, the new node will be the first node of the linked list.
	 */
	public void addAtHead(int val) {
		Node newhead=new Node();
		newhead.value=val;
		newhead.next=head;
		head=newhead;

	}

	/** Append a node of value val to the last element of the linked list. */
	public void addAtTail(int val) {
		Node traverser = head;
		while (traverser != null) {
			if (traverser.next == null) {
				Node newNode = new Node();
				newNode.value = val;
				newNode.next = null;
				traverser.next = newNode;
				break;
			}
			traverser = traverser.next;
		}

	}

	/**
	 * Add a node of value val before the index-th node in the linked list. If index
	 * equals to the length of linked list, the node will be appended to the end of
	 * linked list. If index is greater than the length, the node will not be
	 * inserted.
	 */
	public void addAtIndex(int index, int val) {

		Node traverser = head;
		Node previous=null;
		int position=0;
		while (position<=index)  {
			if(index==position) {
				Node newNode = new Node();
				newNode.value = val;
				newNode.next = traverser;
				if(previous==null) {
					head=newNode;
				}else {
					previous.next=newNode;
				}
			
				break;
				
			}else {
				position++;
				previous=traverser;
				if(previous==null) {
					break;
				}
				traverser = traverser.next;
			}
			
			
			
		}

	

	}

	/** Delete the index-th node in the linked list, if the index is valid. */
	public void deleteAtIndex(int index) {
		Node traverser = head;
		Node previous=null;
		int position=0;
		while (traverser != null) {
			if(index==position) {
				if(previous==null) {
					head=head.next;
				}else {
					previous.next=traverser.next;
				}
			
				break;
			}else {
				position++;
				previous=traverser;
				traverser = traverser.next;
			}
			
		}
	}

	public static void main(String[] args) {
		LinkedList obj = new LinkedList();
		 obj.addAtHead(1);
		 obj.addAtIndex(2, 2);
		 obj.traverse();
		 System.out.println("----------------------------------------------------------------------\n");
		 System.out.println(obj.get(1));
		 System.out.println("----------------------------------------------------------------------\n");
		 obj.addAtHead(10);
		 obj.traverse();
		 System.out.println("----------------------------------------------------------------------\n");
		 obj.addAtTail(12);
		 obj.traverse();
		 System.out.println("----------------------------------------------------------------------\n");
		 obj.addAtIndex(6,100);
		 obj.traverse();
		 System.out.println("----------------------------------------------------------------------\n");
		 obj.deleteAtIndex(7);
		 obj.traverse();
	}

}
