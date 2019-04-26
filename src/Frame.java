import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

import javax.swing.*;



public class Frame extends JPanel{
	private Timer timmy;
	public JPanel panel;
	public JFrame frame;
	public JPanel gamepanel;
	public Game gameInstance = new Game();
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
		
		panel.setBackground(new Color(255,255,255));
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
		
		JButton exit = new JButton("exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				exitGame();
			}
		});
		exit.setPreferredSize(new Dimension(100,50));
		gamepanel.add(exit);
	
		
		gamepanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		gamepanel.setBackground(new Color(255, 255, 255));
		mapKeyStrokesToActions(gamepanel);

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
		
	}
	
	

	private void mapKeyStrokesToActions(JPanel gpanel) {

		// A map is an Data storage interface which defines
		// an association of a key with a value
		// to "add" to a map you use the "put" method
		// to "get" from a map you use "get(key)" and the 
		// value associated with the key is returned (or null)
		ActionMap map = gpanel.getActionMap();
		InputMap inMap = gpanel.getInputMap();

		// code below associates pressing the up arrow with the command "up"
		// essentially creating the command "up" being broadcast any time the 
		// up key is hit
		inMap.put(KeyStroke.getKeyStroke("pressed UP"), "up");
		inMap.put(KeyStroke.getKeyStroke("pressed DOWN"), "down");
		inMap.put(KeyStroke.getKeyStroke("pressed LEFT"), "left");
		inMap.put(KeyStroke.getKeyStroke("pressed RIGHT"), "right");
		
		inMap.put(KeyStroke.getKeyStroke("pressed W"), "up");
		inMap.put(KeyStroke.getKeyStroke("pressed S"), "down");
		inMap.put(KeyStroke.getKeyStroke("pressed A"), "left");
		inMap.put(KeyStroke.getKeyStroke("pressed D"), "right");
		inMap.put(KeyStroke.getKeyStroke("pressed Q"), "resTurn");
		inMap.put(KeyStroke.getKeyStroke("pressed E"), "aim");
		inMap.put(KeyStroke.getKeyStroke("pressed SPACE"), "shot");
		// code below associates the "up" action with anything in the 
		// actionPerformed method.  Right now, it just prints something
		map.put("up", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameInstance.hit("up");
			}
		});
		map.put("down", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameInstance.hit("down");
			}
		});
		map.put("left", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameInstance.hit("left");
			}
		});
		map.put("right", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameInstance.hit("right");
			}
		});
		map.put("resTurn", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameInstance.hit("resTurn");
			}
		});
		map.put("aim", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameInstance.hit("aim");
			}
		});
		map.put("shot", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameInstance.hit("shot");
			}
		});
	}
	
	
	
	
	
	public void exitGame() {
		System.exit(0);
	}
	
	
	
	
}
