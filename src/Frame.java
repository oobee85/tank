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
	public Game targPractice;
	public JPanel targPanel;
	
	public Frame(int w, int h) {
		frame = new JFrame("Tank");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WIDTH = w;
		HEIGHT = h;
		gameInstance = new Game(WIDTH/2, HEIGHT/2);
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
		
		JButton start = new JButton("Start Game");
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

		JButton target = new JButton("Target Practice");
		target.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				targetPrac();
			}
		});
		target.setPreferredSize(new Dimension(200,50));
		panel.add(target);
		
		panel.setBackground(new Color(255,255,255));
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.requestFocusInWindow();
	
	}
	private void backToMenu(JPanel gp) {
		frame.remove(gp);
		menuSetUp();
		
	}
	private void targetPrac() {
		System.err.println("Starting Game");
		targPractice = new Game(WIDTH/2, HEIGHT/2, 1);
		targPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				gameInstance.drawGame(g);
				
			}
		};
		
		JButton exit = new JButton("exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exitGame();
			}
		});
		exit.setPreferredSize(new Dimension(100,50));
		targPanel.add(exit);
		
	
		
		targPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		targPanel.setBackground(new Color(255, 255, 255));

		frame.add(targPanel);
		frame.pack();
		frame.setVisible(true);
		frame.requestFocusInWindow();
	
		timmy = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				targPractice.updateGame();
				targPanel.repaint();
			}
		});
		timmy.start();

		frame.remove(panel);
		frame.add(targPanel, BorderLayout.CENTER); 
		targPanel.setSize(frame.getSize());
		frame.repaint();
		System.err.println(targPanel.getWidth());
		targPanel.requestFocus();
		
		targPanel.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				
				if(key==KeyEvent.VK_M) {
					gameInstance.keyHit("debug");
				}
				if(key==KeyEvent.VK_W) {
					gameInstance.keyHit("up1");
				}else if(key==KeyEvent.VK_A) {
					gameInstance.keyHit("left1");
				}else if(key==KeyEvent.VK_S) {
					gameInstance.keyHit("down1");
				}else if(key==KeyEvent.VK_D) {
					gameInstance.keyHit("right1");
				}else if(key==KeyEvent.VK_E) {
					gameInstance.keyHit("aim1");
				}else if(key==KeyEvent.VK_Z) {
					gameInstance.keyHit("resturn1");
				}else if(key==KeyEvent.VK_Q) {
					gameInstance.keyHit("shot1");
				}
				
			}
		});
		targPanel.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				targPractice.updateMousePos(e.getX(),e.getY());
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				
			}
		});
		
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
		
		JButton exit = new JButton("exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exitGame();
			}
		});
		exit.setPreferredSize(new Dimension(100,50));
		gamepanel.add(exit);
		
		
		JButton bToMenu = new JButton("Back To Main Menu");
		bToMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				backToMenu(gamepanel);
			}
		});
		bToMenu.setPreferredSize(new Dimension(100,50));
		gamepanel.add(bToMenu);
	
		
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
				
				if(key==KeyEvent.VK_M) {
					gameInstance.keyHit("debug");
				}
				if(key==KeyEvent.VK_W) {
					gameInstance.keyHit("up1");
				}else if(key==KeyEvent.VK_A) {
					gameInstance.keyHit("left1");
				}else if(key==KeyEvent.VK_S) {
					gameInstance.keyHit("down1");
				}else if(key==KeyEvent.VK_D) {
					gameInstance.keyHit("right1");
				}else if(key==KeyEvent.VK_E) {
					gameInstance.keyHit("aim1");
				}else if(key==KeyEvent.VK_Z) {
					gameInstance.keyHit("resturn1");
				}else if(key==KeyEvent.VK_Q) {
					gameInstance.keyHit("shot1");
				}
				if(key==KeyEvent.VK_I) {
					gameInstance.keyHit("up2");
				}else if(key==KeyEvent.VK_J) {
					gameInstance.keyHit("left2");
				}else if(key==KeyEvent.VK_K) {
					gameInstance.keyHit("down2");
				}else if(key==KeyEvent.VK_L) {
					gameInstance.keyHit("right2");
				}else if(key==KeyEvent.VK_O) {
					gameInstance.keyHit("aim2");
				}else if(key==KeyEvent.VK_M) {
					gameInstance.keyHit("resturn2");
				}else if(key==KeyEvent.VK_U) {
					gameInstance.keyHit("shot2");
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

