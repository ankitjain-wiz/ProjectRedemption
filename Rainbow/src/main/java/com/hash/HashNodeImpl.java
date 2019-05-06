package com.hash;

public class HashNodeImpl<K, V> {

	private HashNode<K, V>[] table; // Array of Entry.
	private int capacity = 4; // Initial capacity of HashMap

	public void put(K key, V value) {

		if (key == null) {
			return;
		}

		// calculate hash of key.
		int hash = generateHash(key);
		// create new entry.
		HashNode<K, V> elementTobeInserted = new HashNode<K, V>(key, value, null);

		//insertion of new element
		if (table[hash] == null) {
			table[hash] = elementTobeInserted;
		}else {
			HashNode<K, V> previous=null;
			HashNode<K, V> current=table[hash];
			while(current!=null) {
				if(current.getKey()==key) {
					if(previous==null) {
						table[hash]=elementTobeInserted;
						elementTobeInserted.next=current.next;
						return;
					}else{
						previous.next=elementTobeInserted;
						elementTobeInserted.next=current.next;
						
					}
				}
				previous=current;
				current=current.next;
				
			}
			previous.next=elementTobeInserted;
			
		}

	}

	public int generateHash(K key) {
		return Math.abs(key.hashCode()) % capacity;
	}
	
	public V get(K key) {
		int hash = generateHash(key);
		HashNode<K, V> elementTobereturned = table[hash];
		if (elementTobereturned == null) {
			return null;
		} else {
			while (elementTobereturned != null) {
				if (elementTobereturned.getKey() == key) {
					return elementTobereturned.getValue();
				}
				elementTobereturned = elementTobereturned.next;
			}
			return null;
		}

	}

}
