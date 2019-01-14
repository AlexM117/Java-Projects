//Alex Marlow

//Tree class that instantiates a node with a weight and can make attachments
public class myTree implements Comparable<myTree> {
	//direct is to keep track of if this node is a right of left node
	char direct;
	//item is for the String of this node, will be blank if this is a combo node
    String item;
    
    //Weight is the number of the occurrences, or sum of occurrences 
	int weight;
	//parent is a link to parent node, which is most likely note used in CodingTree class
	myTree Parent;
	//links to children
	myTree left = null;
	myTree right = null;
	
	//sets the direction of the node
	public void adddir(char in) {
		this.direct = in;
	}
	//adds a character for the node
	public void addItem(String it) {
		this.item = it;
		
	}
	//initiates a tree with a weight
	public myTree(int data) {
		this.weight = data;
		
	}
	//adds links to children, will never have to only add one child so no errors in that regard
	public void addChildren(myTree childone, myTree childtwo) {
		this.left = childone;
		this.right = childtwo;
		
	}
	//adds parent to node
	public void addParent(myTree Par) {
		this.Parent = Par;
	}
	//compare method that did not end up being used
	public int compareTo(myTree comp) {
		return this.weight - comp.weight;
		
	}
	
	
}
