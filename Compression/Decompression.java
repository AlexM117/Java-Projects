//Alex Marlow   This code is used to uncompress the text file

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.BitSet;

public class Decompression {

    public static void main(String[] args) {
    	
    	//hash table for the keys and strings for the file names
    	MyHashTable<String, String> codes = new MyHashTable<String,String>(32768);
        String fileName = "outComp.txt";
        String keysName = "keys.txt";
        
        BitSet bitSet = new BitSet(); 
        StringBuilder myString = new StringBuilder();
        
        try {
       	 //Accessing files and changing them to desired format of bitset and stringbuilder
   			String text = new String(Files.readAllBytes(Paths.get(fileName)));
   			String keys = new String(Files.readAllBytes(Paths.get(keysName)));
   			byte[] text2 = text.getBytes();
   			bitSet  = BitSet.valueOf(text2);
   			myString = new StringBuilder(keys);
   			
   			//error handling 
   		} catch (IOException e) {
   			e.printStackTrace();
   		}
        
        //fill the hash table with the keys
        codes = fillTable(codes,myString);

        //uncompress the text
        String text = decompress(codes,bitSet);
        
        //write text to file
        PrintWriter outputStream;
		try {
			outputStream = new PrintWriter("uncompressed.txt");
			outputStream.println(text);
	    	outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	
        
        
        
        
	}
    
    //takes in the hash table with keys, an the bitset of compressed text and uncompresses the text
    public static String decompress(MyHashTable<String, String> codes, BitSet bitSet) {
    	StringBuilder deComp = new StringBuilder();
    	int stopIndx = 0;
    	StringBuilder temp = new StringBuilder();
    	//loops through each index of the bitset
    	while(stopIndx != bitSet.length()) {
    		//checks if bit is set and writes the number to a temporary stringbuilder
    		if(bitSet.get(stopIndx) == true) {
    			temp.append("1");
    		} else {
    			temp.append("0");
    		}
 
    		//checks if temporary string builder is a key to a word
    		if(codes.containsKey(temp.toString())) {
    			//append the word and clear the temporary string builder
    			deComp.append(codes.get(temp.toString()));
    			temp = new StringBuilder();
    			stopIndx++;
    		} else {
    			//add another bit the to string builder
    			stopIndx++;
    		}

    	}
    	//return the final uncompressed string
    	return deComp.toString();
    }
    
    //takes in the empty hash table and the string of keys and fills the hash table with the key value pairs. 
    public static MyHashTable<String, String> fillTable(MyHashTable<String, String> codes, StringBuilder myString) {
    	 int cnt = 2;
         int temp = 2;
         boolean comaPass = false;
         //Infinite loop goes though each character of the string
         while(true) {
        	 //checks if the final ')' of the key value pair has been reached, commaPass is to make sure the value ')' did not trigger as
        	 // a comma separates the key and the value and it must be passed first before finding the ')' that ends the key value pair
        	 while(myString.charAt(cnt) != ')' || comaPass == false) {
        		 //checks if key value separating comma has been passed
        		 if(myString.charAt(cnt) == ',' ) {
        			 comaPass = true;
        		 }
        		 cnt++;
        	 }
        	 comaPass = false;
        	 //makes a segment of the key value pair
        	 StringBuilder segment =  new StringBuilder(myString.substring(temp,cnt));
        	 
        	 temp = segment.length() - 1;
        	 //finds the key value separating comma
        	 while(segment.charAt(temp) != ',') {
        		 temp--;
        	 }
        	 //splits up the segment into key and value
        	 String bits = segment.substring(temp+2,segment.length());
        	 String word = segment.substring(0,temp);
        	 //Enter the key and value into the hash table
        	 codes.put(bits, word);
        	 
        	 //check for end of keys
        	 if(myString.charAt(cnt + 1) == ']') {
        		 return codes;
        	 } else {
        		 cnt = cnt + 4;
        		 temp = cnt;
        	 }
         }
         
         
    }
	
}
