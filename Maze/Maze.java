//Alex Marlow 
//Program creates a randomly generated maze.

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

//Maze class
public class Maze {
	
	//width, height, debug, and starting node of Graph
	boolean debg;
	int W;
	int D;
	GraphNode Enter;
	
	//Method constructs maze of specified size and finds solution to maze
	Maze(int width, int depth, boolean debug) {
		//Assigning construction variables
		W = width;
		D = depth;
		this.debg = debug;
		Enter = new GraphNode();
		Enter.setType('V');
		//temporary graph nodes
		GraphNode temp;
		GraphNode temp2;
		temp = Enter;
		temp2 = Enter;
		//constructs first left column of graph nodes
		for(int x = 1; x < depth; x++) {
			GraphNode down = new GraphNode();
			//start with character as a space and connects them with up and down connections
			down.setType(' ');
			down.addTop(temp);
			temp.addBottom(down);
			temp = down;
		}
		//resets temporary node
		temp = Enter;
		//constructs the first top row of nodes with the leftmost being the top of the previous initial column
		for(int x = 1; x < width; x++) {
			GraphNode Right = new GraphNode();
			Right.setType(' ');
			Right.addLeft(temp2);
			temp2.addRight(Right);
			temp2 = Right;
		}
        //fills in the rest of the square of nodes
		for(int y = 1; y < depth; y ++) {
			//resets temporary nodes
			temp = Enter;
			temp2 = Enter;
			//sets temporary nodes to correct height on initial left column
			//temp2 is set to one above the current row being created, on an already created row
			for(int z = 0; z < y; z++) {
				temp = temp.Down;
			}
			for(int z = 1; z < y; z++) {
				temp2 = temp2.Down;
			}
			//temp2 moved over one to be above the next created node on the row below
			temp2 = temp2.Right;
			//creates a row of nodes on the row that temp is at, connecting left and right with the previous horizontal node, as well as up and down with temp2 about it
			for(int x = 1; x < width; x++) {
				GraphNode Right = new GraphNode();
				Right.setType(' ');
				temp.addRight(Right);
				Right.addLeft(temp);
				Right.addTop(temp2);
				temp2.addBottom(Right);
				//moves nodes over one
				temp = Right;
				temp2 = temp2.Right;
			}
		}
		
		
		//resets nodes and creates stack for tree traversal history and backtracking
		temp = Enter;
		temp2 = Enter;
		Stack<GraphNode> myStack = new Stack<GraphNode>();
		temp.visit = true;
		myStack.push(temp);
		//prints initial state of maze if debug is on
		if(debug == true) {
			display();
		}
		//ends when stack is empty which will only be when all possible directions are complete, the following traversal algorithm is based off of the depth first algorithm
		while(!myStack.isEmpty()) {
			//small array used in random number generation
			ArrayList<Integer> myArray = new ArrayList<Integer>();
			//checks if all 4 directions and adds the corresponding number to the array list if traversal in that direction is allowed
			if((temp.Up != null) && !temp.Up.visit) {
				myArray.add(1);
			}
			if((temp.Right != null) && !temp.Right.visit) {
				myArray.add(2);
			}
			if((temp.Down != null) && !temp.Down.visit) {
				myArray.add(3);
			}
			if((temp.Left != null) && !temp.Left.visit) {
				myArray.add(4);
			}
			//chooses a random position in array of allowed direction numbers
			if(myArray.size() > 0) {
				//creates random number of any possible index of the array
				Random rand = new Random();
				int n = rand.nextInt(myArray.size());
				//gets number at index
				n = myArray.get(n);
				//chooses to travel in direction based off of number retrieved
				//upward direction
				if(n == 1) {
					//moves node up and sets the connection between the two used for wall identification in printing
					temp = temp.Up;
					temp.downCon = true;
					//sets the visit tracker and char for visual in print
					temp.visit = true;
					temp.type = 'V';
					//prints if debug on
					if(debug == true) {
						display();
					}
					//adds node to stack
					myStack.push(temp);
				}
				//right direction
				if(n == 2) {
					//moves node right and sets the connection between the two used for wall identification in printing
					temp.rightCon = true;
					temp = temp.Right;
					//sets the visit tracker and char for visual in print
					temp.visit = true;
					temp.type = 'V';
					//prints if debug on
					if(debug == true) {
						display();
					}
					//adds node to stack
					myStack.push(temp);
				}
				//down direction
				if(n == 3) {
					//moves node down and sets the connection between the two used for wall identification in printing
					temp.downCon = true;
					temp = temp.Down;	
					//sets the visit tracker and char for visual in print
					temp.visit = true;
					temp.type = 'V';
					//prints if debug on
					if(debug == true) {
						display();
					}
					//adds node to stack
					myStack.push(temp);
				}
				//left direction
				if(n == 4) {
					//moves node left and sets the connection between the two used for wall identification in printing
					temp = temp.Left;
					temp.rightCon = true;
					//sets the visit tracker and char for visual in print
					temp.visit = true;
					temp.type = 'V';
					//prints if debug on
					if(debug == true) {
						display();
					}
					//adds node to stack
					myStack.push(temp);
				}
				//if no direction is possible the node backtracks using the stack
			} else {
				myStack.pop();
				if(!myStack.isEmpty()) {
					temp = myStack.peek();
				}
			}
			
		}
		
		//resets temporary nodes
		temp = Enter;
		temp2 = Enter;
		//sets temp2 in the position of the maze end node
		for( int x = 1; x < D; x++) {
			temp2 = temp2.Down;
		}
		for(int x = 1; x < W; x++) {
			temp2 = temp2.Right;
		}
		//temporary stack for traversal
		Stack<GraphNode> myStack2 = new Stack<GraphNode>();
		//traverses maze tree until temp reaches the end node represented by temp2
		while(!temp.equals(temp2)) {
			//travels in any direction allowed by connections of maze tree
			//up direction
			if((temp.Up != null) && temp.Up.downCon && temp.Up.visit) {
				//adds node to stack and resets visit to false so direction is not re done on backtracks
				myStack2.push(temp);
				temp = temp.Up;
				temp.visit = false;
				//right direction
			} else if(temp.rightCon && temp.Right.visit) {
				//adds node to stack and resets visit to false so direction is not re done on backtracks
				myStack2.push(temp);
				temp = temp.Right;
				temp.visit = false;
				//down direction
			} else if(temp.downCon && temp.Down.visit) {
				//adds node to stack and resets visit to false so direction is not re done on backtracks
				myStack2.push(temp);
				temp = temp.Down;
				temp.visit = false;
				//left direction
			} else if((temp.Left != null) && temp.Left.rightCon && temp.Left.visit) {
				//adds node to stack and resets visit to false so direction is not re done on backtracks
				myStack2.push(temp);
				temp = temp.Left;
				temp.visit = false;
				// else backtracks the tree using the stack
			} else {
				temp = myStack2.pop();
			}
		}
		//once final node is found the stack consists of the direct path from beginning, so nodes backtrack using stack setting character of node to + 
		while(!myStack2.isEmpty()) {
			temp.type = '+';
			temp = myStack2.pop();
		}
		//setting Enter node char to +
		temp.type = '+';
		//resets temporary nodes	
		temp = Enter;
		temp2 = Enter;
		//travels through all nodes using original graph connections because order does not matter, reseting any V character nodes that weren't turned to + back to a space
		for(int x = 0; x < D; x++) {
			for(int y = 0; y < W; y++) {
				if(temp.type == 'V') {
					temp.type = ' ';
				}
				//moves to right node
				temp = temp.Right;
			}
			//moves down a row and resets horizontal node position
			temp2 = temp2.Down;
			temp = temp2;
		}	
	}
	
	
	
	
	
	
	
	
	//Method that prints the current state of the maze
	void display() {
		//temporary node
		GraphNode temp = Enter;
		//prints initial maze opening
		System.out.print("X   ");
		//then prints the remaining X's depending on width
		for(int x = 0; x < W*2 -1; x++) {
			System.out.print("X ");
		}
		//new line
		System.out.println();
		//prints remaining rows two at a time(node row then wall row) and stop when last node row is printed
		for(int x = 0; x < D; x++) {
			//array list used to keep track of up and down connections that break the horizontal wall
			ArrayList<Character> myList = new ArrayList<Character>();
			//initializes temp to correct position of graph
			temp = Enter;
			for(int z = 0; z < x; z++) {
				temp = temp.Down;
			}
			//prints left wall X
			System.out.print("X ");
			//travels right printing node row and tracking what nodes have down connections that break the wall below them
			for(int y = 0; y < W; y++) {
				//prints node char
				System.out.print(temp.type);
				//breaks vertical walls if node has connection
				if(temp.rightCon) {
					System.out.print("   ");
				} else {
					System.out.print(" X ");
				}
				//adds char to list depending on down connections
				if(temp.downCon) {
					myList.add(' ');
				} else {
					myList.add('X');
				}
				//moves node right
				temp = temp.Right;
			}
			//moves to next line which is horizontal wall and prints
			System.out.println();
			//checks if not last node row
			if(x != D - 1) {
				//prints initial left wall X
				System.out.print("X ");
				//iterates through the list printing the correct recorded wall breaks
				for(int y = 0; y < W; y++) {
					System.out.print(myList.get(y));
					System.out.print(" X ");
				}
				//modes to next line
				System.out.println();
				//if last node row, just prints the bottom X row
			} else {
				//prints X's
				for(int z = 0; z < W*2 -1; z++) {
					System.out.print("X ");
				}
				//prints maze ending opening
				System.out.print("  X ");
			}
		}
		//creates gap for next maze print
		System.out.println();
		System.out.println();
		System.out.println();
	}
}
