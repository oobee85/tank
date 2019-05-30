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
	public JPanel gamepanel;
	private int WIDTH;
	private int HEIGHT;
	public Game gameInstance;
	public JPanel controls;
	
	public Frame(int w, int h, int f) {
		frame = new JFrame("Tank");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WIDTH = w;
		HEIGHT = h;
		gameInstance = new Game(w, h);
		
		if(f == 1) {
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
			gameInstance = new Game(JFrame.MAXIMIZED_HORIZ, JFrame.MAXIMIZED_VERT);
		}
		
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
	}
	
	
	
	public void menuSetUp() {
		panel = new JPanel();
		
		JButton start = new JButton("Start");
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				start();
			}
		});
		start.setPreferredSize(new Dimension(100,50));
		panel.add(start);
		
		
		
		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exitGame();
			}
		});
		exit.setPreferredSize(new Dimension(100,50));
		panel.add(exit);
		
		JButton controls = new JButton("View Controls");
		controls.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controlPage(1);
			}
		});
		controls.setPreferredSize(new Dimension(100,50));
		panel.add(controls);
		
		panel.setBackground(new Color(255,255,255));
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.requestFocusInWindow();
	
	}
	private void controlPage(int x) {
		controls = new JPanel();
		if(x == 1) {
			
			JButton btoMenu = new JButton("Return");
			btoMenu.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					backToMenu(x);
				}
			});
			btoMenu.setPreferredSize(new Dimension(100,50));
			controls.add(btoMenu);
			frame.remove(panel);
		}
		
		if(x == 0) {
			JButton start = new JButton("Back To Game");
			start.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					frame.remove(controls);
					start();
				}
			});
			start.setPreferredSize(new Dimension(100,50));
			controls.add(start);
			
			frame.remove(gamepanel);
		}
		
		
		JButton exit = new JButton("Close Game");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exitGame();
			}
		});
		exit.setPreferredSize(new Dimension(100,50));
		controls.add(exit);
		
		
		
		

		
		JLabel jlabel = new JLabel("To Play The Game,");
		JLabel jlabel2 = new JLabel("Use 'W,A,S,D' To Move Around");
		JLabel jlabel3 = new JLabel("Press 'E' To Aim,");
		JLabel jlabel4 = new JLabel("And 'Space' to shoot!");
		JLabel jlabel5 = new JLabel(" ");
		JLabel jlabel6 = new JLabel("Have Fun!");
		JLabel jlabel7 = new JLabel("Press 'Q' to reset your tank's turn.");
		JLabel jlabel8 = new JLabel("The wheel shows which way the tank is facing.");
		JLabel jlabel9 = new JLabel("Click 'M' for debug mode.");
		JLabel jlabel10 = new JLabel("Shoot the targets!");
		JLabel jlabel11 = new JLabel("Read the tips!");
		
		jlabel.setFont(new Font("Verdana",1,20));
		jlabel.setHorizontalAlignment(JLabel.CENTER);
		jlabel2.setFont(new Font("Verdana",1,20));
		jlabel3.setFont(new Font("Verdana",1,20));
		jlabel4.setFont(new Font("Verdana",1,20));
		jlabel5.setFont(new Font("Verdana",1,20));
		jlabel6.setFont(new Font("Verdana",1,20));
		jlabel7.setFont(new Font("Verdana",1,20));
		jlabel8.setFont(new Font("Verdana",1,20));
		jlabel9.setFont(new Font("Verdana",1,20));
		jlabel10.setFont(new Font("Verdana",1,20));
		jlabel11.setFont(new Font("Verdana",1,20));
	
		controls.add(jlabel);
		controls.add(jlabel2);
		controls.add(jlabel3);
		controls.add(jlabel4);
		controls.add(jlabel5);
		controls.add(jlabel7);
		controls.add(jlabel8);
		controls.add(jlabel9);
		controls.add(jlabel10);
		controls.add(jlabel11);
		controls.add(jlabel6);

		controls.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.add(controls);
		frame.pack();
		frame.setVisible(true);
		frame.requestFocusInWindow();
	}
	
	
	private void backToMenu(int x) {
		if(x == 1) {
			frame.remove(controls);
		}
		if(x==0) {
			frame.remove(gamepanel);	
		}
		
		menuSetUp();
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
		
		JButton exit = new JButton("Close Game");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exitGame();
			}
		});
		exit.setPreferredSize(new Dimension(100,50));
		gamepanel.add(exit);
		
		JButton btoMenu = new JButton("Back To Menu");
		btoMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				backToMenu(0);
			}
		});
		btoMenu.setPreferredSize(new Dimension(100,50));
		gamepanel.add(btoMenu);
		
		JButton controls = new JButton("Controls");
		controls.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controlPage(0);
			}
		});
		controls.setPreferredSize(new Dimension(100,50));
		gamepanel.add(controls);
		
		gamepanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		gamepanel.setBackground(new Color(255, 255, 255));

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
				}else if(key==KeyEvent.VK_M) {
					gameInstance.keyHit("debug");
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
	
	
	
	
}

