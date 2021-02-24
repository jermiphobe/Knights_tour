import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

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
	
	Color success = new Color(76, 187, 23);
	Color fail = new Color(255, 0, 56);
	Color working = new Color(33, 171, 205);
	Color blank = new Color(242, 243, 244);
	
	//List of next possible moves
	ArrayList<Knights_square> move_set = new ArrayList<>();
	
	Knights_square(int x, int y, int size, int square_id) {
		x_origin = x;
		y_origin = y;
		square_size = size;
		id = square_id;
		str_id = String.valueOf(id);
		
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