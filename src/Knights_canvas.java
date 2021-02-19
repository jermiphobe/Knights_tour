import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

class Knights_canvas extends JPanel {
	
	ArrayList<Knights_square> squares = new ArrayList<>();
	
	
	Knights_canvas(int height) {
		//setSize(height, height);
	}
	
	public void draw_squares(int total_squares, int box_side, int origin) {
		int side_length = (int) Math.sqrt(total_squares);
		int curr_square = 1;
		int curr_x = 0;
		int curr_y = 0;
		
		//Creates the square objects
		for (int i = 0; i < side_length; i += 1) {
			
			for (int j = 0; j < side_length; j += 1) {
				Knights_square square = new Knights_square(curr_x, curr_y, box_side, curr_square);
				squares.add(square);
				
				//Increment curr_square 
				curr_square += 1;
				
				//Increment curr_x
				curr_x += box_side;
			}
			
			//Add side length to the curr_y
			curr_y += box_side;
			
			//Reset curr_x
			curr_x = 0;
		}
		
		repaint();
	}
	
	public void fill_a_square(int id) {
		for (Knights_square square: squares) {
			if (square.get_id() == id) {
				square.fill();
				break;
			}
		}
	}
	
	public void unfill_a_square(int id) {
		for (Knights_square square: squares) {
			if (square.get_id() == id) {
				square.unfill();
				break;
			}
		}
	}
	
	public void solve_it(ArrayList<Integer> order, boolean watch) {
		int sleep_time;
		
		if (watch) {
			sleep_time = 10;
		} else {
			sleep_time = 50;
		}
		
		for (int i = 0; i < order.size(); i += 1) {
			
			try {
				Thread.sleep(sleep_time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			int index = order.get(i) - 1;
			squares.get(index).solve();
			repaint();
			
		}
		
		
	}
	
	public void fail() {
		for (Knights_square square: squares) {
			square.fail();
		}
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		setBackground(Color.BLACK);
		
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
		
		//repaint();
	}
	
}