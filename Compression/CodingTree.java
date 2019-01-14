//Alex Marlow 
//prints stats of the table myHash which is the occurrence counting hashtable, at the end of the CodingTreeMethod

import java.util.Comparator;
import java.util.PriorityQueue;

//Compresses text File using Huffman method on words
public class CodingTree  {
	
	//HashTable used for codes and String for final bit String
	MyHashTable<String, String> codes = new MyHashTable<String,String>(32768);
	String bits = "";
	
	 //compression method with string input
	 public void CodingTree(String fulltext) {  

		//hash table used for counting occurrences of words in input string
		MyHashTable<String, Integer> myHash = new MyHashTable<String,Integer>(32768);
		//variables used in word construction
		StringBuilder tem = new StringBuilder();
		//for tracking if char is a word splitter char
		boolean split = true;
		//iterates through entire input message 
		for(int x = 0; x < fulltext.length(); x++ ) {
			//initializes assumption that char is a splitter
			split = true;
			char T = fulltext.charAt(x);
			//checks if char is a number
			for(int y = 48; y < 58 ; y++) {
				if(T == y) {
					split =  false;
				}
			}
			//checks if char is a upper case letter
			for(int y = 65; y < 91 ; y++) {
				if(T == y) {
					split =  false;
				}
			}
			//checks if char is a lower case letter
			for(int y = 97; y < 123 ; y++) {
				if(T == y) {
					split =  false;
				}
			}
			//checks if char is apostrophe or dash
			if((T == 39) || T == 45) {
				split = false;
			}
			//adds char to string builder if it is not a word splitter
			if(split == false) {
				tem.append(T);
			//if char is not a word splitter, adds current built word and the splitter char individual	
			} else {
				String Z = tem.toString();
				//if the building word has a size add it as a occurrence of the key
				if(Z.length() > 0) {
					//if key already exists
					if(myHash.containsKey(Z)) {
						myHash.put(Z, (myHash.get(Z) + 1));	
					//if Key is new	
					} else {
						myHash.put(Z, 1);
					}
					//resets word
					tem.delete(0, tem.length());
				}
				//adds the splitter char as a key
				StringBuilder S = new StringBuilder();
				S.append(T);
				String Y = S.toString();
				//if key already exists
				if(myHash.containsKey(Y)) {
					myHash.put(Y, (myHash.get(Y) + 1));	
					//if Key is new
				} else {
					myHash.put(Y, 1);
				}
			}
			
			
		}

		
				
		//implementing priority Queue
		//constructing comparator and priority Queue using the comparator and filling Queue
		Comparator<myTree> comparator = new TreeComparator();
		PriorityQueue<myTree> myQueue = new PriorityQueue<myTree>(myHash.cap, comparator);
	
		
		//filling Queue with trees filed with a key and its Values
		for(int x = 0; x < myHash.cap; x++) {
			if(myHash.Values.get(x) != null) {
			myTree aTree = new myTree(myHash.Values.get(x));
			aTree.addItem(myHash.KEYS.get(x));
			myQueue.add(aTree);
			}
		}
		
		
		//attaching tree nodes
		//Iterates until only root is left in Queue
		while(myQueue.size() > 1) {
			//remove two priority nodes which have lowest occurrence count
			myTree aTree1 = myQueue.remove();
			myTree aTree2 = myQueue.remove();
			//creating new node with weight of both removed and children of both
			myTree aTree3 = new myTree(aTree1.weight + aTree2.weight);
			aTree3.addChildren(aTree1, aTree2);
			//Assigning direction to each child
			aTree1.adddir('0');
			aTree2.adddir('1');
			//Assigning new node to the parent node of children
			aTree1.addParent(aTree3);
			aTree2.addParent(aTree3);
			// place new node into Queue
			myQueue.add(aTree3);
		}
		
		//creates a string builder for building codes and a tree node for root
		StringBuilder mahString = new StringBuilder();
		myTree root = myQueue.remove();
		//calls the findCodes to iterate through the tree and set up the codes Hash Table
		findCodes(root, mahString);
		
		//Iterates through original input string, building new string with binary String codes
		StringBuilder Binary = new StringBuilder();
		//loops through original input String
		tem.delete(0, tem.length());
		//checks the entire input message letter by letter
		for(int x = 0; x < fulltext.length(); x++ ) {
			//variable for tracking if the letter was a word splitter or not
			split = true;
			char T = fulltext.charAt(x);
			//checks ascii values of integers
			for(int y = 48; y < 58 ; y++) {
				if(T == y) {
					split =  false;
				}
			}
			//checks Upper case ascii values
			for(int y = 65; y < 91 ; y++) {
				if(T == y) {
					split =  false;
				}
			}
			//checks lower case ascii values
			for(int y = 97; y < 123 ; y++) {
				if(T == y) {
					split =  false;
				}
			}
			//checks apostrophe and dash
			if((T == 39) || T == 45) {
				split = false;
			}
			//if any of the previous cases were true, adds char to a string builder
			if(split == false) {
				tem.append(T);
			//if char is a word splitter	
			} else {
				String Z = tem.toString();
				//if current string builder has a size add it as a finished word and reset the string builder
				if(Z.length() > 0) {
					Binary.append(codes.get(Z));
					tem.delete(0, tem.length());
				}
				//creates string for the word splitter char and adds it as a finished word
				StringBuilder S = new StringBuilder();
				S.append(T);
				String Y = S.toString();
				Binary.append(codes.get(Y));
			}
			
			
		}
		
		//Assigns the string builder to the bits String
		this.bits = Binary.toString();
		myHash.stats();
		
	}
	 
	 //iterates through the tree using PreOrder and ads char and code to tree when leaf node is found
	 public void findCodes(myTree aTree, StringBuilder code) {
		 //adds a 1 or a 0 to the current code depending in if this node is left or right
		 if(aTree.direct == '0') {
		 	 code.append('0');
		 } else if (aTree.direct == '1') {
			 code.append('1');
		 }
		 
		 //travels down the left node if one exists
		 if(aTree.left != null) {
			findCodes(aTree.left, code);
			 
		 }	 
		 //travels down right node if one exists (after left node)
		 if(aTree.right != null) {
			 findCodes(aTree.right, code);
		 
		 
		 } else {
	     //in the case of no left or right children	 
		 //puts the char of the leaf and the current code in the tree	 
		 this.codes.put(aTree.item, code.toString());
		 }
		 //removes a 0 or 1 from the node code before iterating back up, but tests if there is a direction, because
		 //the root node has no direction and no code so we do not want to remove a code bit on the root node finish
		 if((aTree.direct == '1') || (aTree.direct =='0')) {
		 code.deleteCharAt(code.length() - 1);
		 }
	 }
	
}
