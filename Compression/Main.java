//Alex Marlow 
//method calls for CodingTree and HashTable Testing are commented out at bottom of Main method

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.BitSet;

//main method for text compression
public class Main {

	//Main method for calling data compression 
	public static void main(String[] args) {
		//start time analyzer 
		final long startTime = System.currentTimeMillis();
		//creating input file, StringBuilder and an instance of codeingtree
        String fileName = "WarAndPeace.txt";
        StringBuilder myString = new StringBuilder(); 
        CodingTree myTree = new CodingTree();
        
        
        String text = "";
       try {
    	 //Accessing file and reading it to string then moving it to stringbuilder
			text = new String(Files.readAllBytes(Paths.get(fileName)));
			myString = new StringBuilder(text); 
			
			//error handling 
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        //Deleting the UTF-8 BOM on the WarAndPeace file or any other file with the same 3 char BOM
        if((myString.charAt(0) == 'ï') && (myString.charAt(1) == '»') && (myString.charAt(2) == '¿') ) {
        	myString.deleteCharAt(0);
        	myString.deleteCharAt(0);
        	myString.deleteCharAt(0);
        }
        //moving the stringbuilder back to a string and calling the compression method
        String Final = myString.toString();
        myTree.CodingTree(Final);
        
        //creating new bitset for the final compressed code 
        BitSet bitSet  = new BitSet(myTree.bits.length());
		int bitCounter = 0;
		//iterates through character array of the final 0's and 1's string and creates appropriate bitset
        for(Character c : myTree.bits.toCharArray()) {
			if(c.equals('1')) {
				bitSet.set(bitCounter);
			}
			bitCounter++;
		} 
        
        try {
        	//writes the bitset to the file out.txt
        	String fileOut = "outComp.txt";
        	Files.write(Paths.get(fileOut), bitSet.toByteArray());
        	//making a file of the set of codes for the compressed file
        	PrintWriter outputStream = new PrintWriter("keys.txt");
        	outputStream.println(myTree.codes.toString());
        	outputStream.close();
        	
        	//Information calculation output to console
        	File aFile = new File(fileName);
        	File aFile2 = new File(fileOut);
        	float one = aFile2.length()*100;
        	float two = aFile.length();
        	float size = (one/two);
        	System.out.println("The size of the original input file (" + fileName + ") is " + aFile.length()/1024 + " KB");
        	System.out.println("The output file of the Codes/decopresion keys is called keys.txt\n");
        	System.out.println("The target size is 1002 KB" );
        	System.out.println("The size of my compressed file (" + fileOut + ") is: " + aFile2.length()/1024 + " KB (this size will be very different from target if WarAndPeace is not the file being anylized)\n");
        	System.out.println("The Compressed file is  " + size + "% the size of the original file.");	
        	
        	//error handling for file creation
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
        
        //end time and total time passed analyzer 
        final Long endTime = System.currentTimeMillis();
		System.out.println("Total time: " + (endTime - startTime) + " milliseconds" );
	
		//Calling the CodingTree test method
		//testCodingTree();
		//testMyHashTable();
		
	}
	
	//method for testing the CodingTreeclass
	public static void testCodingTree() {
		System.out.println();
		System.out.println("CodingTree Testing");
		System.out.println();
		//creating string and passing it to new coding tree
		String testString = "Testing Compression for words huffman encoding";
		CodingTree myTree = new CodingTree();
		myTree.CodingTree(testString);
		//printing tested string and the codes and the Binary string representation of the compressed string
		System.out.println("String for testing is: " +testString);
		System.out.println(myTree.codes.toString());
		System.out.println(myTree.bits);
		System.out.println();
		System.out.println();
	}
	
	//method for testing MyHashTable
	public static void testMyHashTable() {
		//Outputting labeling text
		System.out.println("MyHashTable testing");
		System.out.println();
		//creating Hash table and entering one key twice with two different values and another Key.
		MyHashTable<String, Integer> mah = new MyHashTable<String,Integer>(32768);
		mah.put("yeet", 420);
		mah.put("yeet", 620);
		mah.put("cow",  1337);
		//testing get, toString and containsKey
		System.out.println(mah.get("yeet"));
		System.out.println(mah.toString());
		System.out.println(mah.containsKey("yeet"));
		//The following code tests possible combinations of casting string builders to string and such, which during code
		//debugging process gave me some trouble(was fixed) with hashCode() return different values for the same String
		StringBuilder B = new StringBuilder();
		B.append('s');
		mah.put(B.toString(), 1);
		String G = B.toString();
		mah.put(G, 12);
		System.out.println(B.toString());
		System.out.println(mah.containsKey("s"));
		System.out.println(mah.containsKey(G));
		System.out.println(mah.containsKey(B.toString()));
		System.out.println(mah.toString());
		//asking for Key that does not exist
		System.out.println(mah.get("sheesh"));
		//testing stats method
		mah.stats();
		
		
	}
	
	
}
