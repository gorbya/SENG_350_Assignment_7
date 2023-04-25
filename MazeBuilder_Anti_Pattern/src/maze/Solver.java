package maze;

import java.util.ArrayList;
import java.util.Stack;

public class Solver {

	public static int[][] ourMaze = MazeBuilder.maze;
	
	public static int[][] multiChoice;
	public static boolean left;
	public static boolean right;
	public static boolean up;
	public static boolean down;
	public static ArrayList<String> directions;
	public static Coordinates checkpoint;
	public static Stack<Coordinates> allPathsTaken = new Stack();
	

	public static boolean Attempted(int x, int y, Stack<Coordinates> stack) {
		Stack<Coordinates> testStack = stack;
		Coordinates coords = new Coordinates(x,y);
		boolean b = false;
		while (b == false && !testStack.isEmpty()) {
			Coordinates compare = (Coordinates) testStack.pop();
				if (compare.getX() == coords.getX() && compare.getY() == coords.getY()) {
					b = true;
				}
		}
		return b;
		
	}
	
	
	public static Coordinates multiChoiceChecker(int x,int y) {
		Coordinates coords = new Coordinates(x, y);
		Coordinates nextCoords = new Coordinates();
		int multi = 0;
		//up
		try {
			if(MazeBuilder.inBoundsCheck(coords.getX()-1, coords.getY()) && Attempted(coords.getX()-1, coords.getY(), allPathsTaken) == false ) {
				multi ++;
				nextCoords = new Coordinates(coords.getX()-1, coords.getY());
				up = true;
			}
			
			if(MazeBuilder.inBoundsCheck(coords.getX(), coords.getY()-1) && Attempted(coords.getX(), coords.getY()-1 , allPathsTaken) == false) {
				//left	
				left = true;
				nextCoords = new Coordinates(coords.getX(), coords.getY()-1);
				multi ++;
				
			}
			
			if(MazeBuilder.inBoundsCheck(coords.getX()+1, coords.getY()) && Attempted(coords.getX()+1, coords.getY(), allPathsTaken) == false) {
				//down
				down = true;
				nextCoords = new Coordinates(coords.getX()+1, coords.getY());
				multi ++;	
			}
			
			if(MazeBuilder.inBoundsCheck(coords.getX(), coords.getY()+1) && Attempted(coords.getX(), coords.getY()+1, allPathsTaken) == false) {
				//right
				right = true;
				nextCoords = new Coordinates(coords.getX(), coords.getY()+1);
				multi++;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(multi > 1) {
			checkpoint = coords;
			return nextCoords;
		}
		if(multi< 1) {
			MazeBuilder.path = popBack(MazeBuilder.path, checkpoint);
			return MazeBuilder.path.peek();
		}
		if(multi == 1) {
			return nextCoords;
		}
			//hashmap<coords, ArrayList<String>>
			//have a list somewher that store up
	//then do this for the other three directions and store them all in a list with the coordinate where they came from 
		//plus a counter to know how many to pop back 
		
		return null;
	}
	
	public static Stack<Coordinates> popBack(Stack<Coordinates> stack,Coordinates checkpoint) {

		boolean stackRestored = false;
		
		while(stackRestored == false) {
			Coordinates current = stack.peek();
			
			if (current.getX() == checkpoint.getX() && current.getY() == checkpoint.getY()) {
				stackRestored = true;
				continue;
			}
			
			MazeBuilder.maze[current.getX()][current.getY()] = 3;
			//3 marks an attempted location
			stack.pop();
			
		}
		return stack;
		
	}
	
}
