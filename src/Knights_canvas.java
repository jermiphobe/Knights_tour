import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

class Knights_canvas extends JPanel {
	
	static ArrayList<ArrayList<Knights_square>> squares;
	
	Knights_canvas() {}
	
	//Sets the squares list to the list created in the frame
	public void create_squares(ArrayList<ArrayList<Knights_square>> boxes) {
		squares = boxes;
		
		repaint();
	}
	
	//Sets a square to filled once it's been visited
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
	
	//Sets a square to unfilled - in case of backtracking
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
	
	//Color the whole board red because no solution was found
	public void fail() {
		for (ArrayList<Knights_square> squares: squares) {
			for (Knights_square square: squares) {
				square.fail();
			}
		}
		
		repaint();
		
	}
	
	//Set a square as the start of the solution - then color it light gold
	public void start(int id) {
		//Sets the starting square on the board
		for (ArrayList<Knights_square> squares: squares) {
			for (Knights_square square: squares) {
				if (square.get_id() == id) {
					square.set_as_start();
					break;
				}
			}
		}
		
		repaint();
	}
	
	//Set a square as the end of the solution - then color it dark gold
	public void end(int id) {
		//Sets the starting square on the board
		for (ArrayList<Knights_square> squares: squares) {
			for (Knights_square square: squares) {
				if (square.get_id() == id) {
					square.set_as_end();
					break;
				}
			}
		}
		
		repaint();
	}
	
	//Turn the whole board green once a solution is found
	public void solve_it_at_once() {
		for (ArrayList<Knights_square> squares: squares) {
			for (Knights_square square: squares) {
				square.solve();
			}
		}
		repaint();
	}
	
	//Set the whole canvas as white so we can draw a new board, then draw the board
	public void reset_canvas() {
		
	}
	
	//Order to draw the squares
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		setBackground(new Color(242, 243, 244));
		
		for (ArrayList<Knights_square> squares: squares) {
			for (Knights_square square: squares) {

				
				if (square.is_start()) {
					square.draw_start(g);
					continue;
				}
				
				if (square.is_end()) {
					square.draw_end(g);
					continue;
				}
				
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