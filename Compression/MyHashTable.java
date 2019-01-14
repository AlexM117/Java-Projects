//Alex Marlow 

import java.util.ArrayList;
import java.util.LinkedList;

//HashTable class
public class MyHashTable<K,V> {

	//ArrayLists for Keys Values and a int to record capacity
	//Array lists used as they have constant time set gets and end adds
	ArrayList<K> KEYS;
	ArrayList<V> Values;
	int cap;

	

    //Hash Table initializer 
	public MyHashTable(int capacity) {
		
         //initializes Array Lists and fills them with null up to the capacity
		 KEYS = new ArrayList<K>(capacity);
		 Values = new ArrayList<V>(capacity);
		 for(int x = 0; x < capacity; x++) {
			 KEYS.add(null);
			 Values.add(null);
		 }
		 //saves capacity
		 this.cap = capacity;
	}
	
	//Method returns a hashed key of the input Key
	private int hash(K key) {
		int hashed = key.hashCode();
		//keeping the size in the capacity range
		int index = hashed%KEYS.size();
		//Handling negative numbers
		if(index < 0) {
			index = (index*(-1));
		}
		return index;
	}
	
    //places a Key and value into array Lists and replaces value if the same key exists for a different value
	void put(K searchKey, V newValue) {
		boolean done = false;
		//gets hashed key
		int index = hash(searchKey);
		//loops through buckets using a linear probing method
		while(!done) {
			//checks for empty bucket or if Bucket already contains the searcKey
			if((KEYS.get(index) == null) || (KEYS.get(index).equals(searchKey))) {
				//setting values
				KEYS.set(index, searchKey);
				Values.set(index, newValue);
				done = true;
		    //case where the index reaches max capacity while probing
			} else if(index == KEYS.size() - 1) {
				index = 0;
			//increases probe	
			} else {
				index++;
			}
		}
	}
	
	//Method returns value for given Key
	V get(K searchKey) {
		int cnt = 0;
		//get hashed key
		int index = hash(searchKey);
		//loops through buckets until it has found key or looped more than capacity +2 times, using linear probing
		while(cnt < KEYS.size() + 2) {
			//handles error for requested Key not existing
			if(KEYS.get(index) != null) {
				//returns value if key is found
				if(KEYS.get(index).equals(searchKey)) {	
					return Values.get(index);
				//resets index to 0 if capacity reached
				} else if(index == KEYS.size() - 1) {
					index = 0;
					cnt++;
				//increases counter and index	
				} else {
					index++;
					cnt++;
				}
			//error will only output if a key is asked for that doesn't exist
			} else {
				System.out.println("Could not find the item in expected spot");
				return null;
			}
			
		}
		//only called if something messes up
		return null;
		
	}
	
	//method returns if the Buckets contain the given key
	boolean containsKey(K searchKey) {
		int cnt = 0;
		//getting hashed key
		int index = hash(searchKey);
		//loops through all buckets using linear probing
		while(cnt < KEYS.size() + 2) {
			//returns false nothing is in expected place
			if(KEYS.get(index) == null) {
				return false;
			}
			//Returns true if key found
			if(KEYS.get(index).equals(searchKey)) {
				return true;
			//resets index to 0 if capacity limit reached
			} else if(index == KEYS.size() - 1) {
				index = 0;
				cnt++;
			//increases index and counter	
			} else {
				index++;
				cnt++;
			}
		}
		//returns false of all buckets checked and no correct key found
		return false;
		
	}

	//Method prints the statistics of the Hash Table
	void stats() {
		//linked list for data storage
		LinkedList<Integer> Stats = new LinkedList<Integer>();
		//counter and entries counter
		int cnt = 0;
		int ent = 0;
		//boolean used in logic
		boolean found = false;
		//printing beginning
		System.out.println("Hash Table Stats\n===============");
		//loops through all buckets and counts entries
		for(int x = 0; x < Values.size(); x++ ) {
			if(KEYS.get(x) != null) {
				ent++;
			}
		}
		//prints entries and capacity of hash Table
		System.out.println("Number of Entries: " + ent);
		System.out.println("Number of Buckets: " + KEYS.size());
		//loops through keys filling a histogram of Probes into the linked list
		for(int x = 0; x < KEYS.size(); x++ ) {
			//ignores empty Keys
			if(KEYS.get(x) != null) {
				cnt = 0;
				found = false;
				//gets key
				K temp = KEYS.get(x);
				//hashes key
				int t = hash(temp);
				//uses linear probing to find Key
				while(!found) {
					//if key found
					if(KEYS.get(t) == temp) {	
						found = true;
						if(cnt >= Stats.size()) {
							//extends size of linked list and initializes new places to 0
							for(int y = Stats.size(); y < cnt + 1; y++) {
								Stats.add(0);
							}
						}
						//increases count for that number of probings place by 1
						Stats.set(cnt, Stats.get(cnt) + 1);
					//checks if capacity reached and resets index to 0	
					} else if(t == KEYS.size() - 1) {
						t = 0;
						cnt++;
					//increase index and counter	
					} else {
						t++;
						cnt++;
					}
				}
			}
			
			
		}
		//calculates the percent of buckets used
		float perc = ((float)ent)/((float)KEYS.size())*((float)100);
		
		float total = 0;
		
		// calculates Weighted average of probes
		for(int x = 0; x < Stats.size(); x++) {
			total = (float)total + ((float)(Stats.get(x)*(float)x));
		}
		total = (float)total/((float)(ent));
		
		//prints remaining information of hash table
		System.out.println("Histogram of Probes: " + Stats.toString());
		System.out.println("Fill Percentage: " + perc + "%");
		System.out.println("Max Linear Prob: "+ (Stats.size() - 1));
		System.out.println("Avarage Linear Prob: " + total);
		System.out.println();
		System.out.println();
	}
	
	//Method prints a string of the Hash Table
	public String toString() {
		//initializes string builder
		StringBuilder myHash = new StringBuilder();
		myHash.append("[");
		//loops through all keys and values adding them to string builder
		for(int x = 0; x < KEYS.size(); x++) {
			//ignores empty key places
			if(KEYS.get(x) != null) {
				//enters String
				myHash.append("(");
				myHash.append(KEYS.get(x).toString());
				myHash.append(", ");
				myHash.append(Values.get(x).toString());
				myHash.append(")");
				myHash.append(", ");
			}
			
		}
		//removes extra comma and space at end
		if((myHash.charAt(myHash.length() - 1) == ' ') && (myHash.charAt(myHash.length() - 2) == ',')) {
			myHash.delete(myHash.length() - 2, myHash.length());
		}
		//finishes string and returns it
		myHash.append("]");
		return myHash.toString();
	}
	
	
	
}
