//Alex Marlow 

//tests the maze generator with two different maze sizes
public class Main {

	public static void main(String[] args) {
		//smaller maze with debug on
		Maze s = new Maze(5,5,true);
		s.display();
		//larger maze with debug off
		Maze l = new Maze(10,10,false);
		l.display();

	}

}
