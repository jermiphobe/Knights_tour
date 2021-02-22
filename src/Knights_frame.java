import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
		
		//add_menu();
		add_board();
		setVisible(true);
		
		board.start_knights_tour();

	}
	
	public void add_menu() {
		menu = new JPanel();
		menu.setSize(menu_width, height);
		menu.setBackground(new Color(242, 243, 244));
		menu.setLayout(new GridLayout(0, 1, 10, 10));
		add_buttons();
		menu.setVisible(true);
		add(menu);
	}
	
	public void add_board() {
		board = new Knights_canvas(height);
		board.create_squares(side_length, menu_width, square_size);
		add(board);
	}
	
	public void add_buttons() {
		//Adds buttons/functionality to the menu
		RoundedButton start_btn = new RoundedButton("Simulate");
		start_btn.setBackground(new Color(145, 163, 176));
		
		//Adds function to the play/pause button
		start_btn.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		    	board.start_knights_tour();
		    }
		});
		
		menu.add(start_btn);
	}
	
}