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
		
		String ans = "";
		boolean watch = true;
		
		while (true) {
			System.out.print("Would you like to watch it get solved? > ");
			ans = input.nextLine();
			
			
			try {
				ans = ans.toLowerCase();
				
				if (ans.equals("yes") || ans.equals("no")) {
					break;
				}
				
			} catch (NumberFormatException e) {
				System.out.println();
				continue;
			}
		}
		
		if (ans.equals("yes")) {
			watch = true;
		} else {
			watch = false;
		}
		
		//graphics.fill_a_square(start);
		
		start_knights_tour(start, squares, graphics, watch);

	}
	
	static void start_knights_tour(int start_point, ArrayList<ArrayList<Knights_square>> square_array, Knights_graphics graphics, boolean simulate) {
		
		//Keeps track of order of path
		ArrayList<Integer> finish_order = new ArrayList<>();
		boolean watch = simulate;
		
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
		
		if (solve_knights_tour(curr_big, curr_little, square_array, graphics, finish_order, watch)) {
			graphics.solve_it(finish_order, watch);
			
		} else {
			graphics.fail();
		}
		
		System.out.println("Done");
		
		
	}
	
	//Big means outer array, small means inner array
	public static boolean solve_knights_tour(int curr_big, int curr_little, ArrayList<ArrayList<Knights_square>> square_array, Knights_graphics graphics, ArrayList<Integer> order, boolean watch) {
		
		int sleep_time = 100;
		
		if (watch) {
			try {
				Thread.sleep(sleep_time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		ArrayList<int[]> move_order = new ArrayList<>();
		int possible_moves = 0;
		
		int little_size = square_array.get(0).size();
		int big_size = square_array.size();
		
		int new_big = 0;
		int new_little = 0;
		
		int fill_id = square_array.get(curr_big).get(curr_little).get_id();
		square_array.get(curr_big).get(curr_little).fill();
		order.add(fill_id);
		
		if (watch) {
			graphics.fill_a_square(fill_id);
		}
		
		//First square to try
		new_big = curr_big - 2;
		new_little = curr_little - 1;
		if (new_big >= 0 && new_little >= 0 && square_array.get(new_big).get(new_little).is_open()) {
			
			possible_moves = get_total_moves(new_big, new_little, square_array);
			int[] move = {possible_moves, square_array.get(new_big).get(new_little).get_id()};
			move_order.add(move);
			
		}
		
		//Second square
		new_big = curr_big - 2;
		new_little = curr_little + 1;
		if (new_big >= 0 && new_little < little_size && square_array.get(new_big).get(new_little).is_open()) {
			
			possible_moves = get_total_moves(new_big, new_little, square_array);
			int[] move = {possible_moves, square_array.get(new_big).get(new_little).get_id()};
			move_order.add(move);
			
		}
		
		//Third square
		new_big = curr_big - 1;
		new_little = curr_little + 2;
		if (new_big >= 0 && new_little < little_size && square_array.get(new_big).get(new_little).is_open()) {
			
			possible_moves = get_total_moves(new_big, new_little, square_array);
			int[] move = {possible_moves, square_array.get(new_big).get(new_little).get_id()};
			move_order.add(move);
			
		}
		
		//Fourth square
		new_big = curr_big + 1;
		new_little = curr_little + 2;
		if (new_big < big_size && new_little < little_size && square_array.get(new_big).get(new_little).is_open()) {
			
			possible_moves = get_total_moves(new_big, new_little, square_array);
			int[] move = {possible_moves, square_array.get(new_big).get(new_little).get_id()};
			move_order.add(move);
			
		}
		
		//Fifth square
		new_big = curr_big + 2;
		new_little = curr_little + 1;
		if (new_big < big_size && new_little < little_size && square_array.get(new_big).get(new_little).is_open()) {
			
			possible_moves = get_total_moves(new_big, new_little, square_array);
			int[] move = {possible_moves, square_array.get(new_big).get(new_little).get_id()};
			move_order.add(move);
			
		}
		
		//Sixth square
		new_big = curr_big + 2;
		new_little = curr_little - 1;
		if (new_big < big_size && new_little >= 0 && square_array.get(new_big).get(new_little).is_open()) {
			
			possible_moves = get_total_moves(new_big, new_little, square_array);
			int[] move = {possible_moves, square_array.get(new_big).get(new_little).get_id()};
			move_order.add(move);
			
		}
		
		//Seventh square
		new_big = curr_big + 1;
		new_little = curr_little - 2;
		if (new_big < big_size && new_little >= 0 && square_array.get(new_big).get(new_little).is_open()) {
			
			possible_moves = get_total_moves(new_big, new_little, square_array);
			int[] move = {possible_moves, square_array.get(new_big).get(new_little).get_id()};
			move_order.add(move);
			
		}
		
		//Eighth square
		new_big = curr_big - 1;
		new_little = curr_little - 2;
		if (new_big >= 0 && new_little >= 0 && square_array.get(new_big).get(new_little).is_open()) {
			
			possible_moves = get_total_moves(new_big, new_little, square_array);
			int[] move = {possible_moves, square_array.get(new_big).get(new_little).get_id()};
			move_order.add(move);
			
		}
		
		//Order the move list
		for (int i = 0; i < move_order.size(); i += 1) {
			
			int[] smallest = move_order.get(i);
			int smallest_index = i;
			
			for (int j = i; j < move_order.size(); j += 1) {
				if (move_order.get(j)[0] <= smallest[0]) {
					smallest = move_order.get(j);
					smallest_index = j;
				}
			}
			
			move_order.set(smallest_index, move_order.get(i));
			move_order.set(i, smallest);
			
		}
		
		//Loop to try the moves in order
		boolean found = false;
		for (int i = 0; i < move_order.size(); i += 1) {
			
			//get indexes for the next move based off id
			for (int j = 0; j < square_array.size(); j += 1) {
				
				for (int k = 0; k < square_array.get(i).size(); k += 1) {
					
					if (square_array.get(j).get(k).get_id() == move_order.get(i)[1]) {
						found = true;
						new_big = j;
						new_little = k;
						break;
					}
				}
				
				if (found) {
					break;
				}
			}
			
			//if statement to run the recursive function and return true if true
			if (new_big >= 0 && new_little >= 0 && square_array.get(new_big).get(new_little).is_open()) {
				
				if (solve_knights_tour(new_big, new_little, square_array, graphics, order, watch)) {
					return true;
				}
				
			}
			
			
		}
		
		//Check to see if I've 'won'
		if (is_solved(square_array)) {
			return true;
		}
		
		if (watch) {
			graphics.unfill_a_square(fill_id);
		}
			
		square_array.get(curr_big).get(curr_little).unfill();
		order.remove(order.size() - 1);
		
		return false;
	}
	
	static int get_total_moves(int curr_big, int curr_little, ArrayList<ArrayList<Knights_square>> square_array) {
		int moves = 0;
		
		int new_big = 0;
		int new_little = 0;
		
		int little_size = square_array.get(0).size();
		int big_size = square_array.size();
		
		//First square to try
		new_big = curr_big - 2;
		new_little = curr_little - 1;
		if (new_big >= 0 && new_little >= 0 && square_array.get(new_big).get(new_little).is_open()) {
			
			moves += 1;
			
		}
		
		//Second square
		new_big = curr_big - 2;
		new_little = curr_little + 1;
		if (new_big >= 0 && new_little < little_size && square_array.get(new_big).get(new_little).is_open()) {
			
			moves += 1;
			
		}
		
		//Third square
		new_big = curr_big - 1;
		new_little = curr_little + 2;
		if (new_big >= 0 && new_little < little_size && square_array.get(new_big).get(new_little).is_open()) {
			
			moves += 1;
			
		}
		
		//Fourth square
		new_big = curr_big + 1;
		new_little = curr_little + 2;
		if (new_big < big_size && new_little < little_size && square_array.get(new_big).get(new_little).is_open()) {
			
			moves += 1;
			
		}
		
		//Fifth square
		new_big = curr_big + 2;
		new_little = curr_little + 1;
		if (new_big < big_size && new_little < little_size && square_array.get(new_big).get(new_little).is_open()) {
			
			moves += 1;
			
		}
		
		//Sixth square
		new_big = curr_big + 2;
		new_little = curr_little - 1;
		if (new_big < big_size && new_little >= 0 && square_array.get(new_big).get(new_little).is_open()) {
			
			moves += 1;
			
		}
		
		//Seventh square
		new_big = curr_big + 1;
		new_little = curr_little - 2;
		if (new_big < big_size && new_little >= 0 && square_array.get(new_big).get(new_little).is_open()) {
			
			moves += 1;
			
		}
		
		//Eighth square
		new_big = curr_big - 1;
		new_little = curr_little - 2;
		if (new_big >= 0 && new_little >= 0 && square_array.get(new_big).get(new_little).is_open()) {
			
			moves += 1;
			
		}
		
		return moves;
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
