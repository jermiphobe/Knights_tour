import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeMap;

class Knights_square {
	int x_origin = 0;
	int y_origin = 0;
	int square_size = 0;
	int id = 0;
	String str_id = "";
	int x_index = 0;
	int y_index = 0;
	
	boolean filled = false;
	boolean solved = false;
	boolean open = true;
	boolean failed = false;
	boolean start = false;
	boolean end = false;
	
	Color success = new Color(76, 187, 23);
	Color fail = new Color(205,92,92); //(255, 0, 56);
	Color working = new Color(33, 171, 205);
	Color blank = new Color(242, 243, 244);
	Color first = new Color(218, 165, 32);
	Color last = new Color(184, 134, 32);	
	
	//List of next possible moves
	ArrayList<ArrayList<Integer>> future_moves = new ArrayList<>();
	
	//Constructor 
	Knights_square(int x, int y, int size, int square_id, int x_loc, int y_loc) {
		x_origin = x;
		y_origin = y;
		x_index = x_loc;
		y_index = y_loc;
		square_size = size;
		id = square_id;
		str_id = String.valueOf(id);
		
	}
	
	//This will add a list of a square and it's possible moves to the array of possible moves this square can make
	public void add_to_future_moves(int id, int total_moves) {
		ArrayList<Integer> temp_move = new ArrayList<>();
		temp_move.add(id);
		temp_move.add(total_moves);
		future_moves.add(temp_move);
	}
	
	//Checks if there are moves left in the future moves array
	public boolean more_moves() {
		if (future_moves.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	//Returns and removes the next move in the future moves array
	public int get_next(boolean optimized) {
		int id = future_moves.get(0).get(0);
		future_moves.remove(0);
		return id;
		
	}
	
	//Sorts the future moves from least possible next moves to most
	public void order_moves() {
		for (int i = 0; i < future_moves.size(); i += 1) {
			ArrayList<Integer> curr_smallest = future_moves.get(i);
			int smallest = i;
			
			for (int j = i; j < future_moves.size(); j += 1) {
				ArrayList<Integer> curr_checking = future_moves.get(j);
				
				if (curr_checking.get(1) < curr_smallest.get(1)) {
					curr_smallest = curr_checking;
					smallest = j;
				}
				
			}
			
			//Swap the current and the smallest
			future_moves.set(smallest, future_moves.get(i));
			future_moves.set(i, curr_smallest);
		}
	}
	
	//Draw the empty square
	public void draw_square(Graphics g) {
		g.setColor(blank);
		g.fillRect(x_origin, y_origin, square_size, square_size);
		
		g.setColor(Color.black);
		g.drawRect(x_origin, y_origin, square_size, square_size);
		
		Font font = new Font("Courier New", Font.BOLD, square_size / 4);
		g.setFont(font);
		g.setColor(Color.black);
		g.drawString(str_id, x_origin + (square_size / 2), y_origin + (square_size / 2));
	}
	
	//Draw a blue square
	public void fill_square(Graphics g) {
		g.setColor(working);
		g.fillRect(x_origin, y_origin, square_size, square_size);
		
		//Outline the square
		g.setColor(Color.black);
		g.drawRect(x_origin, y_origin, square_size, square_size);
		
		Font font = new Font("Courier New", Font.BOLD, square_size / 4);
		g.setFont(font);
		g.setColor(Color.black);
		g.drawString(str_id, x_origin + (square_size / 2), y_origin + (square_size / 2));
		
	}
	
	//Draw a green square
	public void solved_square(Graphics g) {
		g.setColor(success);
		g.fillRect(x_origin, y_origin, square_size, square_size);
		
		//Outline the square
		g.setColor(Color.black);
		g.drawRect(x_origin, y_origin, square_size, square_size);
		
		Font font = new Font("Courier New", Font.BOLD, square_size / 4);
		g.setFont(font);
		g.setColor(Color.black);
		g.drawString(str_id, x_origin + (square_size / 2), y_origin + (square_size / 2));
	}
	
	//Draw a red square
	public void fail_square(Graphics g) {
		g.setColor(fail);
		g.fillRect(x_origin, y_origin, square_size, square_size);
		
		//Outline the square
		g.setColor(Color.black);
		g.drawRect(x_origin, y_origin, square_size, square_size);
		
		Font font = new Font("Courier New", Font.BOLD, square_size / 4);
		g.setFont(font);
		g.setColor(Color.black);
		g.drawString(str_id, x_origin + (square_size / 2), y_origin + (square_size / 2));
	}
	
	//Draw first square
	public void draw_start(Graphics g) {
		g.setColor(first);
		g.fillRect(x_origin, y_origin, square_size, square_size);
		
		//Outline the square
		g.setColor(Color.black);
		g.drawRect(x_origin, y_origin, square_size, square_size);
		
		Font font = new Font("Courier New", Font.BOLD, square_size / 4);
		g.setFont(font);
		g.setColor(Color.black);
		g.drawString(str_id, x_origin + (square_size / 2), y_origin + (square_size / 2));
	}
	
	//Draw last square
	public void draw_end(Graphics g) {
		g.setColor(last);
		g.fillRect(x_origin, y_origin, square_size, square_size);
		
		//Outline the square
		g.setColor(Color.black);
		g.drawRect(x_origin, y_origin, square_size, square_size);
		
		Font font = new Font("Courier New", Font.BOLD, square_size / 4);
		g.setFont(font);
		g.setColor(Color.black);
		g.drawString(str_id, x_origin + (square_size / 2), y_origin + (square_size / 2));
	}
	
	public void set_as_start() {
		start = true;
	}
	
	public void set_as_end() {
		end = true;
	}
	
	public boolean is_start() {
		return start;
	}
	
	public boolean is_end() {
		return end;
	}
	
	public void fill() {
		filled = true;
		open = false;
	}
	
	public void unfill() {
		filled = false;
		open = true;
	}
	
	public boolean filled() {
		return filled;
	}
	
	public int get_id() {
		return id;
	}
	
	public void solve() {
		solved = true;
	} 
	
	public void fail() {
		failed = true;
	}
	
	public boolean solved() {
		return solved;
	}
	
	public boolean failed() {
		return failed;
	}
	
	public boolean is_open() {
		return open;
	}
	
	public int get_x_loc() {
		return x_index;
	}
	
	public int get_y_loc() {
		return y_index;
	}
	 
}