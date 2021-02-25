import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

class Knights_canvas extends JPanel {
	
	static ArrayList<ArrayList<Knights_square>> squares;
	
	Knights_canvas(int height) {
		//setSize(height, height);
	}
	
	public void create_squares(ArrayList<ArrayList<Knights_square>> boxes) {
		squares = boxes;
		
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
	
	public void solve_it_at_once() {
		for (ArrayList<Knights_square> squares: squares) {
			for (Knights_square square: squares) {
				square.solve();
			}
		}
		repaint();
	}
	
	public void solve_slowly(int id) {
		for (ArrayList<Knights_square> squares: squares) {
			for (Knights_square square: squares) {
				if (square.get_id() == id) {
					square.solve();
					repaint();
					break;
				}
			}
		}
	}
	
	public void clear_fill() {
		for (ArrayList<Knights_square> squares: squares) {
			for (Knights_square square: squares) {
				square.unfill();
			}
		}
	}
	
	public void reset_canvas() {
		
	}
	
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