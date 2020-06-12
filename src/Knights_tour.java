import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Scanner;

public class Knights_tour {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int total_boxes = 0;
		int total_window_size = 1000;
		
		ArrayList<ArrayList<Knights_square>> squares = new ArrayList<>();
		
		//Input for how many boxes you want
		while (true) {
			System.out.print("How many squares do you want? > ");
			String num_boxes = input.nextLine();
			
			
			try {
				total_boxes = Integer.parseInt(num_boxes);
				break;
				
			} catch (NumberFormatException e) {
				System.out.println();
				continue;
			}
		}
		
		//Figure out side length and box size
		int side_length = (int) Math.sqrt(total_boxes);
		int square_size = (total_window_size - 15) / side_length;
		
		//Reset total_boxes and total_window_size to be accurate to what is shown on screen 
		total_boxes = side_length * side_length;
		total_window_size = side_length * square_size;
		
		System.out.println("Total boxes: " + total_boxes);
		System.out.println("Side length: " + side_length);
		System.out.println("Square size: " + square_size);
		
		Knights_graphics graphics = new Knights_graphics(total_window_size, square_size, total_boxes);
		
		//Create the array for logic side of program
		int curr_square = 1;
		int curr_x = 0;
		int curr_y = 0;
		
		//Creates the square objects
		for (int i = 0; i < side_length; i += 1) {
			ArrayList<Knights_square> temp_squares = new ArrayList<>();
			
			for (int j = 0; j < side_length; j += 1) {
				
				Knights_square square = new Knights_square(curr_x, curr_y, side_length, curr_square);
				temp_squares.add(square);
				
				//Increment curr_square 
				curr_square += 1;
				
				//Increment curr_x
				curr_x += side_length;
			}
			
			squares.add(temp_squares);
			
			//Add side length to the curr_y
			curr_y += side_length;
			
			//Reset curr_x
			curr_x = 0;
		}
		
		//Get the starting point
		int start = 0;
		while (true) {
			System.out.print("What square do you want to start with? > ");
			String start_point = input.nextLine();
			
			
			try {
				start = Integer.parseInt(start_point);
				if (start <= total_boxes && start > 0) {
					//start -= 1;
					break;
				} else {
					continue;
				}
				
			} catch (NumberFormatException e) {
				System.out.println();
				continue;
			}
		}
		
		//graphics.fill_a_square(start);
		
		start_knights_tour(start, squares, graphics);

	}
	
	static void start_knights_tour(int start_point, ArrayList<ArrayList<Knights_square>> square_array, Knights_graphics graphics) {
		int curr_big = 0;
		int curr_little = 0;

		//Find indexes of the starting point
		for (int i = 0; i < square_array.size(); i += 1) {
			for (int j = 0; j < square_array.get(i).size(); j += 1) {
				if (square_array.get(i).get(j).get_id() == start_point) {
					curr_big = i;
					curr_little = j;
				}
			}
		}
		
		if (solve_knights_tour(curr_big, curr_little, square_array, graphics)) {
			graphics.solve_it();
		} else {
			graphics.fail();
		}
		
		System.out.println("Done");
		
		
	}
	
	//Big means outer array, small means inner array
	public static boolean solve_knights_tour(int curr_big, int curr_little, ArrayList<ArrayList<Knights_square>> square_array, Knights_graphics graphics) {
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		boolean solved = false;
		int little_size = square_array.get(0).size();
		int big_size = square_array.size();
		
		int new_big = 0;
		int new_little = 0;
		
		int fill_id = square_array.get(curr_big).get(curr_little).get_id();
		graphics.fill_a_square(fill_id);
		square_array.get(curr_big).get(curr_little).fill();
		
		
		
		//First square to try
		new_big = curr_big - 2;
		new_little = curr_little - 1;
		if (new_big >= 0 && new_little >= 0 && square_array.get(new_big).get(new_little).is_open()) {
			
			if (solve_knights_tour(new_big, new_little, square_array, graphics)) {
				return true;
			}
			
		}
		
		//Second square
		new_big = curr_big - 2;
		new_little = curr_little + 1;
		if (new_big >= 0 && new_little < little_size && square_array.get(new_big).get(new_little).is_open()) {
			
			if (solve_knights_tour(new_big, new_little, square_array, graphics)) {
				return true;
			}
			
		}
		
		//Third square
		new_big = curr_big - 1;
		new_little = curr_little + 2;
		if (new_big >= 0 && new_little < little_size && square_array.get(new_big).get(new_little).is_open()) {
			
			if (solve_knights_tour(new_big, new_little, square_array, graphics)) {
				return true;
			}
			
			
		}
		
		//Fourth square
		new_big = curr_big + 1;
		new_little = curr_little + 2;
		if (new_big < big_size && new_little < little_size && square_array.get(new_big).get(new_little).is_open()) {
			
			if (solve_knights_tour(new_big, new_little, square_array, graphics)) {
				return true;
			}
			
		}
		
		//Fifth square
		new_big = curr_big + 2;
		new_little = curr_little + 1;
		if (new_big < big_size && new_little < little_size && square_array.get(new_big).get(new_little).is_open()) {
			
			if (solve_knights_tour(new_big, new_little, square_array, graphics)) {
				return true;
			}
			
		}
		
		//Sixth square
		new_big = curr_big + 2;
		new_little = curr_little - 1;
		if (new_big < big_size && new_little >= 0 && square_array.get(new_big).get(new_little).is_open()) {
			
			if (solve_knights_tour(new_big, new_little, square_array, graphics)) {
				return true;
			}
			
		}
		
		//Seventh square
		new_big = curr_big + 1;
		new_little = curr_little - 2;
		if (new_big < big_size && new_little >= 0 && square_array.get(new_big).get(new_little).is_open()) {
			
			if (solve_knights_tour(new_big, new_little, square_array, graphics)) {
				return true;
			}
			
		}
		
		//Eighth square
		new_big = curr_big - 1;
		new_little = curr_little - 2;
		if (new_big >= 0 && new_little >= 0 && square_array.get(new_big).get(new_little).is_open()) {
			
			if (solve_knights_tour(new_big, new_little, square_array, graphics)) {
				return true;
			}
			
		}
		
		//Check to see if I've 'won'
		if (is_solved(square_array)) {
			return true;
		}
		
		graphics.unfill_a_square(fill_id);
		square_array.get(curr_big).get(curr_little).unfill();
		
		return false;
	}
	
	static boolean is_solved(ArrayList<ArrayList<Knights_square>> squares) {
		boolean solved = true;
		
		for (int i = 0; i < squares.size(); i += 1) {
			for (int j = 0; j < squares.get(0).size(); j += 1) {
				if (squares.get(i).get(j).is_open()) {
					solved = false;
				}
				
			}
		}
		
		return solved;
	}

}
