//Alex Marlow 

//class stores information for a node of the graph
public class GraphNode {

	//char for printing, graph connections to other nodes
	char type;
	GraphNode Up;
	GraphNode Down;
	GraphNode Right;
	GraphNode Left;
	//boolean connections for tree connections
	boolean downCon;
	boolean rightCon;
	//tracks if visited
	boolean visit;
	
	
	//initializes node
	public GraphNode () {
		this.Up = null;
		this.Down = null;
		this.Right = null;
		this.Left = null;
		this.downCon = false;
		this.rightCon = false;
		this.visit = false;
	}
	
	//adds a node above current
	public void addTop(GraphNode Top) {
		this.Up = Top;
	}
	
	//adds a node below current
	public void addBottom(GraphNode Bottom) {
		this.Down = Bottom;
	}
	
	//adds a node to the right of current
	public void addRight(GraphNode R) {
		this.Right = R;
	}
	
	//adds a node to the left of current
	public void addLeft(GraphNode L) {
		this.Left = L;
	}
	
	//sets the character for printing
	public void setType(char T) {
		this.type = T;
	}
	
}
