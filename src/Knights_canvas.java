import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

class Knights_canvas extends JPanel {
	
	static ArrayList<ArrayList<Knights_square>> squares = new ArrayList<>();
	
	Knights_canvas(int height) {
		//setSize(height, height);
	}
	
	public void create_squares(int side_length, int origin, int box_size) {
		//Create the array for logic side of program
		int curr_square = 1;
		int curr_x = 0;
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
			curr_x = 0;
		}
		
		repaint();
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