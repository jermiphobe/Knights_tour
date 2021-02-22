import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

class Knights_canvas extends JPanel {
	
	static ArrayList<ArrayList<Knights_square>> squares = new ArrayList<>();
	
	//Starting Point
	int start = 7;
	
	//Watch it get solved or not
	boolean watch = true;
	
	Knights_canvas(int height) {
		//setSize(height, height);
	}
	
	public void create_squares(int side_length, int origin, int box_size) {
		//Create the array for logic side of program
		int curr_square = 1;
		int curr_x = origin;
		int curr_y = 0;
		
		System.out.println(side_length + " " + box_size);
		System.out.println();
				
		//Creates the square objects
		for (int i = 0; i < side_length; i += 1) {
			ArrayList<Knights_square> temp_squares = new ArrayList<>();
			
			for (int j = 0; j < side_length; j += 1) {
				
				Knights_square square = new Knights_square(curr_x, curr_y, box_size, curr_square);
				temp_squares.add(square);
				
				//Increment curr_square 
				curr_square += 1;
				
				//Increment curr_x
				curr_x += box_size;
			}
			
			squares.add(temp_squares);
			
			//Add side length to the curr_y
			curr_y += box_size;
			
			//Reset curr_x
			curr_x = origin;
		}
		
		repaint();
	}
	
	void start_knights_tour() {
		
		//Keeps track of order of path
		ArrayList<Integer> finish_order = new ArrayList<>();
		
		int curr_big = 0;
		int curr_little = 0;

		//Find indexes of the starting point
		for (int i = 0; i < squares.size(); i += 1) {
			for (int j = 0; j < squares.get(i).size(); j += 1) {
				if (squares.get(i).get(j).get_id() == start) {
					curr_big = i;
					curr_little = j;
				}
			}
		}
		
		if (solve_knights_tour(curr_big, curr_little, finish_order)) {
			solve_it(finish_order, watch);
			
		} else {
			fail();
		}
		
	}
	
	//Big means outer array, small means inner array
	public boolean solve_knights_tour(int curr_big, int curr_little, ArrayList<Integer> order) {
		
		int sleep_time = 50;
		
		
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
		
		int little_size = squares.get(0).size();
		int big_size = squares.size();
		
		int new_big = 0;
		int new_little = 0;
		
		int fill_id = squares.get(curr_big).get(curr_little).get_id();
		squares.get(curr_big).get(curr_little).fill();
		order.add(fill_id);
		
		if (watch) {
			fill_a_square(fill_id);
		}
		
		//First square to try
		new_big = curr_big - 2;
		new_little = curr_little - 1;
		if (new_big >= 0 && new_little >= 0 && squares.get(new_big).get(new_little).is_open()) {
			
			possible_moves = get_total_moves(new_big, new_little, squares);
			int[] move = {possible_moves, squares.get(new_big).get(new_little).get_id()};
			move_order.add(move);
			
		}
		
		//Second square
		new_big = curr_big - 2;
		new_little = curr_little + 1;
		if (new_big >= 0 && new_little < little_size && squares.get(new_big).get(new_little).is_open()) {
			
			possible_moves = get_total_moves(new_big, new_little, squares);
			int[] move = {possible_moves, squares.get(new_big).get(new_little).get_id()};
			move_order.add(move);
			
		}
		
		//Third square
		new_big = curr_big - 1;
		new_little = curr_little + 2;
		if (new_big >= 0 && new_little < little_size && squares.get(new_big).get(new_little).is_open()) {
			
			possible_moves = get_total_moves(new_big, new_little, squares);
			int[] move = {possible_moves, squares.get(new_big).get(new_little).get_id()};
			move_order.add(move);
			
		}
		
		//Fourth square
		new_big = curr_big + 1;
		new_little = curr_little + 2;
		if (new_big < big_size && new_little < little_size && squares.get(new_big).get(new_little).is_open()) {
			
			possible_moves = get_total_moves(new_big, new_little, squares);
			int[] move = {possible_moves, squares.get(new_big).get(new_little).get_id()};
			move_order.add(move);
			
		}
		
		//Fifth square
		new_big = curr_big + 2;
		new_little = curr_little + 1;
		if (new_big < big_size && new_little < little_size && squares.get(new_big).get(new_little).is_open()) {
			
			possible_moves = get_total_moves(new_big, new_little, squares);
			int[] move = {possible_moves, squares.get(new_big).get(new_little).get_id()};
			move_order.add(move);
			
		}
		
		//Sixth square
		new_big = curr_big + 2;
		new_little = curr_little - 1;
		if (new_big < big_size && new_little >= 0 && squares.get(new_big).get(new_little).is_open()) {
			
			possible_moves = get_total_moves(new_big, new_little, squares);
			int[] move = {possible_moves, squares.get(new_big).get(new_little).get_id()};
			move_order.add(move);
			
		}
		
		//Seventh square
		new_big = curr_big + 1;
		new_little = curr_little - 2;
		if (new_big < big_size && new_little >= 0 && squares.get(new_big).get(new_little).is_open()) {
			
			possible_moves = get_total_moves(new_big, new_little, squares);
			int[] move = {possible_moves, squares.get(new_big).get(new_little).get_id()};
			move_order.add(move);
			
		}
		
		//Eighth square
		new_big = curr_big - 1;
		new_little = curr_little - 2;
		if (new_big >= 0 && new_little >= 0 && squares.get(new_big).get(new_little).is_open()) {
			
			possible_moves = get_total_moves(new_big, new_little, squares);
			int[] move = {possible_moves, squares.get(new_big).get(new_little).get_id()};
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
			for (int j = 0; j < squares.size(); j += 1) {
				
				for (int k = 0; k < squares.get(i).size(); k += 1) {
					
					if (squares.get(j).get(k).get_id() == move_order.get(i)[1]) {
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
			if (new_big >= 0 && new_little >= 0 && squares.get(new_big).get(new_little).is_open()) {
				
				if (solve_knights_tour(new_big, new_little, order)) {
					return true;
				}
				
			}
			
			
		}
		
		//Check to see if I've 'won'
		if (is_solved()) {
			return true;
		}
		
		if (watch) {
			unfill_a_square(fill_id);
		}
			
		squares.get(curr_big).get(curr_little).unfill();
		order.remove(order.size() - 1);
		
		return false;
	}
	
	int get_total_moves(int curr_big, int curr_little, ArrayList<ArrayList<Knights_square>> square_array) {
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
	
	boolean is_solved() {
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
	
	public void fill_a_square(int id) {
		for (ArrayList<Knights_square> squares: squares) {
			for (Knights_square square: squares) {
				if (square.get_id() == id) {
					square.fill();
					break;
				}
			}
		}
		
		repaint();
		
	}
	
	public void unfill_a_square(int id) {
		for (ArrayList<Knights_square> squares: squares) {
			for (Knights_square square: squares) {
				if (square.get_id() == id) {
					square.unfill();
					break;
				}
			}
		}
		
		repaint();
	}
	
	public void solve_it(ArrayList<Integer> order, boolean watch) {
		int sleep_time;
		
		if (watch) {
			sleep_time = 10;
		} else {
			sleep_time = 50;
		}
		
		System.out.println(order);
		
		for (int i = 0; i < order.size(); i += 1) {
			
			try {
				Thread.sleep(sleep_time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			int index = order.get(i); 
			int index_check = 0;
			for (ArrayList<Knights_square> squares: squares) {
				for (Knights_square square: squares) {
					if (index == index_check) {
						square.solve();
						
						repaint();
						
					} else {
						index_check += 1;
					}
				}
			}
			
		}
		
		
		
		
	}
	
	public void fail() {
		for (ArrayList<Knights_square> squares: squares) {
			for (Knights_square square: squares) {
				square.fail();
			}
		}
		
		repaint();
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		setBackground(new Color(242, 243, 244));
		
		for (ArrayList<Knights_square> squares: squares) {
			for (Knights_square square: squares) {
			
				if (square.solved()) {
					square.solved_square(g);
					continue;
				}
				
				if (square.failed()) {
					square.fail_square(g);
					continue;
				}
				
				if (square.filled()) {
					square.fill_square(g);
				} else {
					square.draw_square(g);
				}
			}
		}
	}
	
}