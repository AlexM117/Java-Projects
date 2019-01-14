//Alex Marlow 

import java.util.Comparator;

//comparator class used for the priority Queue
public class TreeComparator implements Comparator<myTree> {
	
	//Compares the two trees weight and returns the appropriate value
   public int compare(myTree one, myTree two) {
	   if ( one.weight > two.weight) {
		   return 1;
	   }
	   if (one.weight < two.weight) {
		   return -1;
	   }
	   return 0;
   }
	
}
