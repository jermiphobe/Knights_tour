import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class Knights_frame extends JFrame {
	JPanel menu;
	JPanel main;
	Knights_canvas board;
	Info_frame rules;
	
	//Main icon
	ImageIcon image = new ImageIcon("knight_2.png");
	
	//Size variables
	int menu_width = 300;
	int total_boxes = 64;
	
	//Figure out side length and box size
	int side_length;
	int height;
	int square_size;
	
	//List and variables for simulation
	ArrayList<ArrayList<Knights_square>> squares = new ArrayList<>();
	ArrayList<Knights_square> path = new ArrayList<>();
	ArrayList<int[]> legal_moves = new ArrayList<>();
	
	//Starting Point
	int start = 37;
	
	//Wait time for timer
	int watch_wait = 90;
	
	//Optimized or not 
	boolean opt = true;
	
	//Solved or not / cleared or not
	boolean solved = false;
	boolean cleared = true;
	boolean active = false;
	boolean paused = true;
	
	//Timer and timer task for doing one iteration of the solution process
    Timer timer;
    TimerTask simulate;
    
    //Action listener to move the starting point
    MouseAdapter move_start = new MouseAdapter() {

		public void mouseClicked(MouseEvent e) {
			boolean found = false;

			//Get mouse location
    		int mouse_x = e.getX();
    		int mouse_y = e.getY();
    		
    		//Loop through all of the squares
    		for (ArrayList<Knights_square> row: squares) {
    			for (Knights_square square: row) {
    				//Get the origin of the square
    				int x_origin = square.get_x_origin();
    				int y_origin = square.get_y_origin();
    				
    				//Check if the mouse is within the sides of the square
    				if (mouse_x >= x_origin && mouse_x <= x_origin + square_size) {
    					//Check if the mouse is between the top and bottom of the square
    					if (mouse_y >= y_origin && mouse_y <= y_origin + square_size) {
    						
    						try {
	    						//Remove old start
	    						board.unset_start(path.get(0));
	    						path.remove(0);
    						} catch (Exception e1) {
    							
    						}
    						
    						//Set start variable as the new square id and re-initialize the start
    						start = square.get_id();
    						initialize_start();
    						
    						found = true;
    						break;
    					}
    				}
    			}
    			
    			if (found) {
    				break;
    			}
    			
    		}
			
		}
    	
    };
    
    //A button to play/pause the simulation
    	//It's up here so I can reset the text with the simulate/clear buttons
    RoundedButton pause_button;
    
    //The size slider
    JSlider size_slider;
    JLabel size_label;
    
    //Change listener for the size slider
    ChangeListener change_size;
    
    //The speed slider
    JSlider speed_slider;
    
    //The listener for the speed slider
    ChangeListener change_speed;
    
    //The optimization check box
    JCheckBox opt_box;
    ImageIcon opt_on = new ImageIcon("On_switch_final.png");
    ImageIcon opt_off = new ImageIcon("Off_switch_final.png");
    
    //The action listener for the optimized check box
    ActionListener change_opt;
    
	
	Knights_frame() {
		
		//Set up all of the sizes based on screen size
		set_sizes();
		
		//Initializes the frame 'settings'
		setTitle("Knight's Tour");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(height + menu_width + 17, height + 40);
		setResizable(true);
		setLocation(400, 15);
		setIconImage(image.getImage());
		
		//Set layout and add panels
		setLayout(new BorderLayout());
		add_menu();
		add_board();
		
		//Initialize the starting point -> Want to add this to its own function
		generate_legal_moves();
		
		//Add the action listener to select starting point
		board.addMouseListener(move_start);
		size_slider.addChangeListener(change_size);
		speed_slider.addChangeListener(change_speed);
		
		setVisible(true);
		
		rules = new Info_frame(height);

	}
	
	public void set_sizes() {
		//Get the screen height to determine the frame size
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		double screen_height = screenSize.getHeight();
		
		//Calculate frame size
		height = (int) (screen_height * .85);
		
		//Reset total_boxes and total_window_size to be accurate to what is shown on screen 
		side_length = (int) Math.sqrt(total_boxes);
		square_size = height / side_length;
	}
	
	//Creates a panel for the menu and adds it to the frame
	public void add_menu() {
		menu = new JPanel();
		
		//Set the main panel layout and add borders
		menu.setPreferredSize(new Dimension(menu_width, height));
		menu.setBackground(new Color(242, 243, 244));
		menu.setLayout(new BorderLayout());
		
		JPanel border_north = new JPanel();
		border_north.setBackground(new Color(242, 243, 244));
		border_north.setPreferredSize(new Dimension(10, 10));
		menu.add(border_north, BorderLayout.NORTH);
		
		JPanel border_east = new JPanel();
		border_east.setBackground(new Color(242, 243, 244));
		border_east.setPreferredSize(new Dimension(10, 10));
		menu.add(border_east, BorderLayout.EAST);
		
		JPanel border_south = new JPanel();
		border_south.setBackground(new Color(242, 243, 244));
		border_south.setPreferredSize(new Dimension(10, 10));
		menu.add(border_south, BorderLayout.SOUTH);
		
		JPanel border_west = new JPanel();
		border_west.setBackground(new Color(242, 243, 244));
		border_west.setPreferredSize(new Dimension(10, 10));
		menu.add(border_west, BorderLayout.WEST);
		
		//Create the panel with the buttons
		main = new JPanel();
		menu.add(main, BorderLayout.CENTER);
		
		main.setLayout(new GridLayout(0, 1, 10, 10));
		add_buttons();
		add(menu, BorderLayout.WEST);
	}
	
	//Creates the grid array and then creates the board panel and adds it to the frame
	public void add_board() {
		create_squares();
		
		//Board panel creation
		board = new Knights_canvas(height);
		board.setPreferredSize(new Dimension(height, height));
		board.create_squares(squares);
		
		add(board, BorderLayout.CENTER);
		initialize_start();
	}
	
	//Adds buttons/functionality to the menu
	public void add_buttons() {
		//Simulate button -> Will start/restart the simulation
		RoundedButton start_btn = new RoundedButton("Simulate");
		start_btn.setBackground(new Color(145, 163, 176));
		
		//Adds function to the simulate button
		start_btn.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		    	
		    	//Check that starting point is on the board
		    	if (start <= total_boxes) {
		    		//Remove the mouse listener so you can't change the start during the simulation
			    	board.removeMouseListener(move_start);
			    	
			    	//Remove the size slider listener so the board can't change
			    	size_slider.removeChangeListener(change_size);
		    		
			    	//Check to see if a simulation is active
			    	if (active) {
			    		
			    		//Check if paused or not
			    		if (paused) {
			    			paused = false;
			    			pause_button.setLabel("Pause");
			    			
			    			reset();
			    			start_knights_tour();
			    			
			    		} else {
				    		//Stop the timer, clear the board, and restart the simulation
				    		timer.cancel();
				    		reset();
				    		start_knights_tour();
			    		}
			    		
			    	} else {
			    		//Set active to true
				    	active = true;
				    	paused = false;
				    	pause_button.setLabel("Pause");
			    		
				    	//If the board is not solved, run the simulation
				    	if (solved) {
				    		reset();
				    		start_knights_tour();
				    	} else {
				    		start_knights_tour();
				    		
				    	}
			    	}
		    	}
			    	
		    	
		    }
		});
		
		main.add(start_btn);
		
		//Add a clear button -> Will clear a solve board or cancel a simulation
		RoundedButton clear = new RoundedButton("Clear");
		clear.setBackground(new Color(145, 163, 176));
		
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (active || solved) {
					board.addMouseListener(move_start);
					size_slider.addChangeListener(change_size);
					timer.cancel();
					
					cleared = true;
					reset();
					
					//Set the pause button up to restart the simulation
	    			active = false;
	    			pause_button.setLabel("Play");
				}
				
			}
		});
		
		main.add(clear);
		
		//A button to pause the simulation
		pause_button = new RoundedButton("Play");
		pause_button.setBackground(new Color(145, 163, 176));
		
		pause_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Only do anything if the simulation isn't solved
				if (!solved && active) {
					
					//Decide what to do based on if the simulation is active or not
					if (paused) {
						paused = false;
						pause_button.setLabel("Pause");
						start_knights_tour();
					} else {
						paused = true;
						pause_button.setLabel("Play");
						timer.cancel();
					}
				}
			}
		});
		
		//Add the pause button
		main.add(pause_button);
		
		//Create a check box for selecting if the simulation is optimized or not
		JPanel opt_panel = new JPanel();
		JLabel opt_label = new JLabel("Optimize the Simulation");
		opt_panel.setBackground(new Color(145, 163, 176));
		opt_panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		opt_box = new JCheckBox();
		opt_box.setHorizontalAlignment(JCheckBox.CENTER);
		opt_box.setBackground(new Color(145, 163, 176));
		opt_box.setIcon(opt_off);
		opt_box.setSelectedIcon(opt_on);
		opt_box.setSelected(true);
		opt_box.setFocusable(false);
		
		opt_label.setHorizontalAlignment(JLabel.CENTER);
		
		opt_panel.add(opt_label);
		opt_panel.add(opt_box);
		
		//Initialize the action listener for the check box
		change_opt = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (opt_box.isSelected()) {
					opt = true;
				} else {
					opt = false;
				}
			}
		};
		
		opt_box.addActionListener(change_opt);
		
		main.add(opt_panel);
		
		//Create the slider to change the size -> Will only work when the board is cleared
		size_slider = new JSlider(3, 28, 8);
		JLabel slider_label = new JLabel("Select Side Length");
		size_label = new JLabel();
		
		//The panel to hold them both
		JPanel slider_panel = new JPanel();
		slider_panel.setBackground(new Color(145, 163, 176));
		slider_panel.setLayout(new GridLayout(0, 1, 0, 0));
		size_slider.setBackground(new Color(145, 163, 176));
		
		size_slider.setPaintTrack(true);
		size_slider.setMajorTickSpacing(5);
		size_slider.setPaintLabels(true);
		
		slider_label.setHorizontalAlignment(JLabel.CENTER);
		size_label.setText(size_slider.getValue() + " X " + size_slider.getValue());
		size_label.setHorizontalAlignment(JLabel.CENTER);
		
		slider_panel.add(slider_label);
		slider_panel.add(size_slider);
		slider_panel.add(size_label);
		
		//Initialize the change size change listener
		change_size = new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				//Get the value of the slider
				int side_length = size_slider.getValue();
				total_boxes = side_length * side_length;

				size_label.setText(size_slider.getValue() + " X " + size_slider.getValue());
				
				set_sizes();
				reset();
				
			}
	    	
	    };
		
		main.add(slider_panel);
		
		//Create a slider to control the simulation speed
		speed_slider = new JSlider(10, 130, 90);
		JLabel speed_label = new JLabel("Select Simulation Speed");
		
		//The panel to hold them both
		JPanel speed_panel = new JPanel();
		speed_panel.setBackground(new Color(145, 163, 176));
		speed_panel.setLayout(new GridLayout(0, 1, 0, 0));
		speed_slider.setBackground(new Color(145, 163, 176));
		
		speed_slider.setPaintTrack(true);
		speed_slider.setMajorTickSpacing(20);
		speed_slider.setPaintLabels(true);
		
		speed_label.setHorizontalAlignment(JLabel.CENTER);
		
		speed_panel.add(speed_label);
		speed_panel.add(speed_slider);
		
		//Initializes the change speed change listener
		change_speed = new ChangeListener() {
	    	
	    	@Override 
	    	public void stateChanged(ChangeEvent e) {
	    		watch_wait = speed_slider.getValue();
	    	}
	    };
		
		main.add(speed_panel);
		
		//A button to close the simulation because the x on the frame isn't enough
		RoundedButton exit = new RoundedButton("Exit Program");
		exit.setBackground(new Color(145, 163, 176));
		
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				rules.dispose();
				
			}
			
		});
		
		main.add(exit);
		
	}
	
	//Creates the timer and the timer task for the main loop
	public void start_knights_tour() {
		//Creates and runs the timer
		timer = new Timer();
		simulate = new TimerTask() { 

	        public void run() 
	        {   
	        	
		        //Need to figure out and track possible moves (only if not visited yet)
	        	Knights_square current_stop = path.get(path.size() -1);
	        	if (current_stop.is_open()) {
	        		get_move_set(current_stop.get_id());
	        		
	        		//Sort move set if optimized
	        		if (opt) {
	        			current_stop.order_moves();
	        		}
	        		
	        		//Fill the current square
	        		board.fill_a_square(current_stop);
	        		
	        	}
	        	
	        	//If more moves exist, add next move to path and remove from current stop move list
	        	if (current_stop.more_moves()) {
	        		int next_id = current_stop.get_next(opt);
	        		
	        		//Loop through to find square based on id
	        		for (int i = 0; i < squares.size(); i += 1) {
	        			for (int j = 0; j < squares.get(i).size(); j += 1) {
	        				Knights_square temp = squares.get(i).get(j);
	        				if (temp.get_id() == next_id) {
	        					
	        					//Add the current stop's first available move to the path
	        					path.add(temp);
	        				}
	        			}
	        		}
	        		
	        	} else {
	        		//Check if the board failed
	        		if (path.size() == 1) {
	        			board.fail();
	        			timer.cancel();
	        			
	        			//Set the pause button up to restart the simulation
	        			active = false;
	        			pause_button.setLabel("Play");
	        		}
	        		
	        		//If no more moves, remove this square from the path list
	        		path.remove(path.size() -1);
	        		board.unfill_a_square(current_stop);
	        		
	        	}
	        	
	        	//Check for solved -> if solved cancel the timer and start the timer to draw in the solution
	        	if (path.size() == total_boxes) {  //If the path is the same size as the total amount of squares, then it's solved
	        		solved = true;
	        		
	        		//Fill in the last square with the working color
	        		Knights_square last_square = path.get(path.size() - 1);
	        		board.end(last_square);
	        		board.solve_it_at_once();
	        		timer.cancel();
	        		
	        		//Set the pause button up to restart the simulation
        			active = false;
        			pause_button.setLabel("Play");
	        			        		
	        	}
		        	
	        }; 
	        
	    }; 
	    
		timer.schedule(simulate, 0, watch_wait);

	}
	
	//Sets the starting point
	public void initialize_start() {
		
		//Finds the starting point and adds it to the path list
		for (ArrayList<Knights_square> row: squares) {
			for (Knights_square square: row) {
				if (square.get_id() == start) {
					
					path.add(square);
					board.start(square);
				}
			}
		}
	}
	
	//Makes a list of moves and the count of moves from there
	public void get_move_set(int id) {
		//Get the indexes of the current square
		Knights_square curr = path.get(path.size() -1);
		
		//Indexes for the square
		int x = curr.get_x_loc();
		int y = curr.get_y_loc();
		
		for (int[] move: legal_moves) {
			//Get the square that corresponds to the move
			int new_x = x + move[0];
			int new_y = y + move[1];
			
			try {
				Knights_square tmp = squares.get(new_x).get(new_y);
				
				if (tmp.is_open()) {			
					int next_count = get_move_count(new_x, new_y);
					curr.add_to_future_moves(tmp.get_id(), next_count);
				}
				
			} catch (Exception e) {
				
			}
			
		}
		
		
	}
	
	//Counts possible moves from a given index
	public int get_move_count(int x, int y) {
		int count = 0;
		
		for (int[] move: legal_moves) {
			//Try to access each new location.  If it doesn't error, increment count
			try {
				Knights_square temp = squares.get(x + move[0]).get(y + move[1]);
				if (temp.is_open()) {
					count += 1;
				}
			} catch (Exception e) {
				continue;
			}
			
		}
		
		return count;
	}
	
	public void create_squares() {
		//Create the array for logic side of program
		int curr_square = 1;
		
		//Keeps track of coordinates for drawing the squares
		int curr_x = 0;
		int curr_y = 0;
				
		//Creates the square objects
		for (int i = 0; i < side_length; i += 1) {
			ArrayList<Knights_square> temp_squares = new ArrayList<>();
			
			for (int j = 0; j < side_length; j += 1) {
				
				Knights_square square = new Knights_square(curr_x, curr_y, square_size, curr_square, i, j);
				temp_squares.add(square);
				
				//Increment curr_square 
				curr_square += 1;
				
				//Increment curr_x
				curr_x += square_size;
			}
			
			squares.add(temp_squares);
			
			//Add side length to the curr_y
			curr_y += square_size;
			
			//Reset curr_x
				curr_x = 0;
		}
		
	}
	
	//Resets the squares array, path array, and redraws the grid
	public void reset() {
		solved = false;
		
		squares = new ArrayList<>();
		path = new ArrayList<>();
		
		create_squares();
		board.create_squares(squares);
		board.reset_canvas();
		
		initialize_start();
	}
	
	//A list of legal moves you can make 
	public void generate_legal_moves() {
		legal_moves.add(new int[]{-2, -1});
		legal_moves.add(new int[]{-2, 1});
		legal_moves.add(new int[]{-1, -2});
		legal_moves.add(new int[]{-1, 2});
		legal_moves.add(new int[]{1, -2});
		legal_moves.add(new int[]{1, 2});
		legal_moves.add(new int[]{2, -1});
		legal_moves.add(new int[]{2, 1});
		
	}		
	
}