import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

class Knights_frame extends JFrame {
	JPanel menu;
	static Knights_canvas board;
	int menu_width = 300;
	
	int total_boxes = 64;
	int height = 1000;
	
	//Figure out side length and box size
	int side_length = (int) Math.sqrt(total_boxes);
	int square_size = (height - 15) / side_length;
	
	//Starting Point
	int start = 7;
	
	//Watch it get solved or not
	boolean watch = true;
	
	//Square objects
	ArrayList<ArrayList<Knights_square>> squares = new ArrayList<>();
	
	Knights_frame() {
		
		//Reset total_boxes and total_window_size to be accurate to what is shown on screen 
		total_boxes = side_length * side_length;
		height = side_length * square_size;
		
		//Initializes the frame 'settings'
		setTitle("Knight's Tour");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setSize(height + menu_width + 17, height + 40);
		setVisible(true);
		setResizable(true);
		setLocation(400, 15);
		
		menu = new JPanel();
		menu.setSize(menu_width, height);
		menu.setBackground(new Color(242, 243, 244));
		menu.setVisible(true);
		//menu.setLayout(new GridLayout(0, 1, 0, 10));
		//add_buttons();
		
		board = new Knights_canvas(height);
		
		add(menu);
		add(board);
		
		menu.setBounds(height + 1, 0, menu_width, height);
		board.setBounds(-1, 0, height, height);
		
		draw_squares(total_boxes, square_size);
		//create_square_objects();
		//start_knights_tour(start, squares, watch);
	}
	
//	public void add_buttons() {
//		//Adds buttons/functionality to the menu
//		RoundedButton start_btn = new RoundedButton("Simulate");
//		start_btn.setBackground(new Color(145, 163, 176));
//		
//		//Adds function to the play/pause button
//		start_btn.addActionListener(new ActionListener() {
//		    public void actionPerformed(ActionEvent e)
//		    {
//		    	
//		    	start_knights_tour(start, squares, watch);
//		    }
//		});
//		
//		menu.add(start_btn);
//		repaint();
//	}
	
	public void create_square_objects() {
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
	}
	
	static void start_knights_tour(int start_point, ArrayList<ArrayList<Knights_square>> square_array, boolean simulate) {
		
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
		
		if (solve_knights_tour_optimized(curr_big, curr_little, square_array, finish_order, watch)) {
			board.solve_it(finish_order, watch);
			
		} else {
			board.fail();
		}
		
	}
	
	//Big means outer array, small means inner array
	public static boolean solve_knights_tour_optimized(int curr_big, int curr_little, ArrayList<ArrayList<Knights_square>> square_array, ArrayList<Integer> order, boolean watch) {
		
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
		
		int little_size = square_array.get(0).size();
		int big_size = square_array.size();
		
		int new_big = 0;
		int new_little = 0;
		
		int fill_id = square_array.get(curr_big).get(curr_little).get_id();
		square_array.get(curr_big).get(curr_little).fill();
		order.add(fill_id);
		
		if (watch) {
			board.fill_a_square(fill_id);
			board.repaint();
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
				
				if (solve_knights_tour_optimized(new_big, new_little, square_array, order, watch)) {
					return true;
				}
				
			}
			
			
		}
		
		//Check to see if I've 'won'
		if (is_solved(square_array)) {
			return true;
		}
		
		if (watch) {
			board.unfill_a_square(fill_id);
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
	
	public void draw_squares(int total_squares, int box_side) {
		board.draw_squares(total_squares, box_side, menu_width);
	}
	
	public void fill_a_square(int id) {
		board.fill_a_square(id);
		repaint();
	}
	
	public void unfill_a_square(int id) {
		board.unfill_a_square(id);
		repaint();
	}
	
	public void solve_it(ArrayList<Integer> order, boolean watch) {
		board.solve_it(order, watch);
	}
	
	public void fail() {
		board.fail();
		repaint();
	}
	
}