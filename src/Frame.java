import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Map;

import javax.swing.*;



public class Frame extends JPanel{
	private Timer timmy;
	public JPanel panel;
	public JFrame frame;
	public JPanel gamepanel = new JPanel();
	private JLayeredPane lpane = new JLayeredPane();
	public Game gameInstance = new Game();
	public JPanel directionsPage ;
	private int WIDTH;
	private int HEIGHT;
	
	public Frame(int w, int h) {
		frame = new JFrame("Tank");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WIDTH = w;
		HEIGHT = h;
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					menuSetUp();
					
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
				}
			}
		});
		Game game = new Game();


	}
	public void paintGrid(Graphics g){

		int rows = 20;

		int cols = 30;
		int width = getSize().width;
		int height = getSize().height;

		// draw the rows
		int rowHt = height / (rows);
		for (int i = 0; i < rows; i++)
			g.drawLine(0, i * rowHt, width, i * rowHt);

		// draw the columns
		int rowWid = width / (cols);
		for (int i = 0; i < cols; i++)
			g.drawLine(i * rowWid, 0, i * rowWid, height);
	}
	
	

	public void menuSetUp() {
		panel = new JPanel();
		
		JButton start = new JButton("startGame");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				start();
			}
		});
		start.setPreferredSize(new Dimension(100,50));
		panel.add(start);

		
		
		JButton exit = new JButton("exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exitGame();
			}
		});

		exit.setPreferredSize(new Dimension(100,50));
		panel.add(exit);

		panel.setBackground(new Color(40,42,45));
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.requestFocusInWindow();
	
	}
		
	private void start() {
		
		System.err.println("Starting Game");
		gamepanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				gameInstance.drawGame(g);

				
			}
		};
//		gamepanel.setLayout(new GridLayout(2, 1));
		
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exitGame();
			}
		});
		exit.setPreferredSize(new Dimension(100,50));
		gamepanel.add(exit);

		JButton directions = new JButton("Directions");
		directions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent args) {
				directions();
				frame.repaint();
			}
		});
		directions.setPreferredSize(new Dimension(100,50));
		gamepanel.add(directions);
	
		
		gamepanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		gamepanel.setBackground(new Color(219, 237, 254));

		frame.add(gamepanel);
		frame.pack();
		frame.setVisible(true);
		frame.requestFocusInWindow();
	
		timmy = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameInstance.updateGame();
				gamepanel.repaint();
			}
		});
		timmy.start();

		frame.remove(panel);
		frame.add(gamepanel, BorderLayout.CENTER); 
		gamepanel.setSize(frame.getSize());
		frame.repaint();
		System.err.println(gamepanel.getWidth());
		gamepanel.requestFocus();
		
		gamepanel.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				
				if(key==KeyEvent.VK_W) {
					gameInstance.keyHit("up");
				}else if(key==KeyEvent.VK_A) {
					gameInstance.keyHit("left");
				}else if(key==KeyEvent.VK_S) {
					gameInstance.keyHit("down");
				}else if(key==KeyEvent.VK_D) {
					gameInstance.keyHit("right");
				}else if(key==KeyEvent.VK_E) {
					gameInstance.keyHit("aim");
				}else if(key==KeyEvent.VK_Q) {
					gameInstance.keyHit("resturn");
				}else if(key==KeyEvent.VK_SPACE) {
					gameInstance.keyHit("shot");
				}
			}
		});
		gamepanel.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				gameInstance.updateMousePos(e.getX(),e.getY());
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	

	
	
	
	public void exitGame() {
		System.exit(0);
	}

	public void directions(){
		directionsPage = new JPanel();
		directionsPage.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	frame.remove(gamepanel);
		frame.add(directionsPage);


	JLabel jlabel = new JLabel("To Play The Game,");
	JLabel jlabel2 = new JLabel("Use W,A,S,D Keys To Move Around");
	JLabel jlabel3 = new JLabel("Press 'E' To Turn On The Scope,");
	JLabel jlabel4 = new JLabel("And Press SPACE to shoot the boolats!");
	JLabel jlabel5 = new JLabel(" ");
	JLabel jlabel6 = new JLabel("Have Fun!");
	jlabel.setFont(new Font("Verdana",1,20));
	jlabel.setHorizontalAlignment(JLabel.CENTER);
	jlabel2.setFont(new Font("Verdana",1,20));
	jlabel3.setFont(new Font("Verdana",1,20));
	jlabel4.setFont(new Font("Verdana",1,20));
	jlabel5.setFont(new Font("Verdana",1,20));
	jlabel6.setFont(new Font("Verdana",1,20));


	directionsPage.add(jlabel);
	directionsPage.add(jlabel2);
	directionsPage.add(jlabel3);
	directionsPage.add(jlabel4);
	directionsPage.add(jlabel5);
	directionsPage.add(jlabel6);


		frame.pack();
		frame.setVisible(true);
		frame.requestFocusInWindow();
			}

	}

