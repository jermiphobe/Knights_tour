import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Knights_graphics {
	Knights_frame frame = new Knights_frame();
	int window_size = 0;
	int square_count = 0;
	int square_side = 0;

	Knights_graphics(int size, int square_size, int squares) {
		window_size = size;
		square_count = squares;
		square_side = square_size;
		
		//Initializes the frame 'settings'
		frame.setTitle("Knight's Tour");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setSize(size + 16, size + 39);
		frame.setVisible(true);
		frame.setLocation(400, 100);
		
		frame.create_squares(square_count, square_side);
		
	}  
	
	public void fill_a_square(int id) {
		frame.fill_a_square(id);
	}
	
	public void unfill_a_square(int id) {
		frame.unfill_a_square(id);
	}
	
	public void solve_it(ArrayList<Integer> order, boolean watch) {
		frame.solve_it(order, watch);
	}
	
	public void fail() {
		frame.fail();
	}

	
}

class Knights_frame extends JFrame {
	Knights_canvas canvas = new Knights_canvas();
	
	Knights_frame() {
		add(canvas);
	}
	
	public void create_squares(int total_squares, int box_side) {
		canvas.create_squares(total_squares, box_side);
	}
	
	public void fill_a_square(int id) {
		canvas.fill_a_square(id);
		repaint();
	}
	
	public void unfill_a_square(int id) {
		canvas.unfill_a_square(id);
		repaint();
	}
	
	public void solve_it(ArrayList<Integer> order, boolean watch) {
		canvas.solve_it(order, watch);
	}
	
	public void fail() {
		canvas.fail();
		repaint();
	}
	
}

class Knights_canvas extends JPanel {
	
	ArrayList<Knights_square> squares = new ArrayList<>();
	
	
	Knights_canvas() {}
	
	public void create_squares(int total_squares, int box_side) {
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

class Knights_square {
	int x_origin = 0;
	int y_origin = 0;
	int square_size = 0;
	int id = 0;
	String str_id = "";
	
	boolean filled = false;
	boolean solved = false;
	boolean open = true;
	boolean failed = false;
	
	Knights_square(int x, int y, int size, int square_id) {
		x_origin = x;
		y_origin = y;
		square_size = size;
		id = square_id;
		str_id = String.valueOf(id);
		
	}
	
	//Draw the empty square
	public void draw_square(Graphics g) {
		g.setColor(Color.white);
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
		g.setColor(Color.blue);
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
		g.setColor(Color.green);
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
			g.setColor(Color.red);
			g.fillRect(x_origin, y_origin, square_size, square_size);
			
			//Outline the square
			g.setColor(Color.black);
			g.drawRect(x_origin, y_origin, square_size, square_size);
			
			Font font = new Font("Courier New", Font.BOLD, square_size / 4);
			g.setFont(font);
			g.setColor(Color.black);
			g.drawString(str_id, x_origin + (square_size / 2), y_origin + (square_size / 2));
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
	 
}