import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Knights_frame extends JFrame {
	JPanel menu;
	static Knights_canvas board;
	
	//Size variables
	int menu_width = 300;
	int total_boxes = 64;
	int height = 1000;
	
	//Figure out side length and box size
	int side_length = (int) Math.sqrt(total_boxes);
	int square_size = (height - 15) / side_length;
	
	//List and variables for simulation
	ArrayList<ArrayList<Knights_square>> squares = new ArrayList<>();
	ArrayList<ArrayList<Knights_square>> path = new ArrayList<>();
	
	//Starting Point
	int start = 7;
	
	//Watch it get solved or not
	boolean watch = true;
	int solve_wait = 250;
	int watch_wait = 500;
	
	//Timer and timer task for doing one iteration of the solution process
    Timer timer;
    TimerTask simulate = new TimerTask() { 

        public void run() 
        { 
            //Need to figure out and track possible moves (only if not visited yet) -> order the moves based on optimization if optimization is enabled
        	
        	//Check if anymore legal moves left
        	
        	//If not remove this square from the list of moves done
        	
        	//Check for solved -> if solved cancel the timer and start the timer to draw in the solution
        }; 
    }; 
	
	Knights_frame() {
		
		//Reset total_boxes and total_window_size to be accurate to what is shown on screen 
		total_boxes = side_length * side_length;
		height = side_length * square_size;
		
		//Initializes the frame 'settings'
		setTitle("Knight's Tour");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setSize(height + menu_width + 17, height + 40);
		setResizable(true);
		setLocation(400, 15);
		
		setLayout(new BorderLayout());
		add_menu();
		add_board();
		
		setVisible(true);

	}
	
	public void add_menu() {
		menu = new JPanel();
		menu.setPreferredSize(new Dimension(menu_width, height));
		menu.setBackground(new Color(242, 243, 244));
		menu.setLayout(new GridLayout(0, 1, 10, 10));
		add_buttons();
		add(menu, BorderLayout.WEST);
	}
	
	public void add_board() {
		board = new Knights_canvas(height);
		board.setPreferredSize(new Dimension(height, height));
		board.create_squares(side_length, menu_width, square_size);
		add(board, BorderLayout.CENTER);
	}
	
	public void add_buttons() {
		//Adds buttons/functionality to the menu
		RoundedButton start_btn = new RoundedButton("Simulate");
		start_btn.setBackground(new Color(145, 163, 176));
		
		//Adds function to the play/pause button
		start_btn.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		    	start_knights_tour();
		    }
		});
		
		menu.add(start_btn);
	}
	
	public void start_knights_tour() {
		timer = new Timer();
		
		if (watch) {
			timer.schedule(simulate, 10, watch_wait);
		} else {
			timer.schedule(simulate, 10, 0);
		}
	}
	
	public void get_move_set(int id) {
		ArrayList<int[]> legal_moves = new ArrayList<>();
	}
		
	
}