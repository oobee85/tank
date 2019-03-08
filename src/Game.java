import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.AbstractAction;
import javax.swing.Timer;

public class Game {

	public static Game game = new Game();
	private static int ticks = 0;

	Tank tank;
	Rectangle rect;
	List<Wall> walls = new ArrayList<>();
	List<Tank> tanks = new ArrayList<>();

	public Game() {

		Wall wall = new Wall(450, 350, 100, 100, Color.black);
		walls.add(wall);
		
		
		tank = new Tank(500, 500, 10, 10, "RED", Color.RED);
		tanks.add(tank);
	}

//	public void moveObjects(){
//		for(Wall w:walls) {
//			if(tank.hit(w)) {
//				System.out.println("hit");
//				stop();
//			}
//		}
//	}
	public static void stop(Graphics g) {
		System.out.println("stopped");
		
	}

	// 10 milliseconds = 100 times per second
	protected static void updateGame() {

		ticks++;// track number times timer gone off
		if (ticks % 100 == 0) {
			System.out.println(ticks / 100 + " seconds");
		}
		if (ticks % 10 == 0) {
//				game.moveObjects();
		}

	}

	// What do you want to do when a key is hit?
	public void keyHit(String s) {
		System.out.println("(keyHit): " + s);
		Tank testtank = tank;
		
		if (s.equals("up")) {
			testtank = new Tank(tank.getRect().x,tank.getRect().y-tank.getMoveS()
					,tank.getRect().width,tank.getRect().height, tank.getTeam(),tank.getColor());
			for (Wall w : walls) {
				if (testtank.hit(w) == true) {
					System.out.println("hit");
				} else {
					tank.moveUp();
				}
			}
		}

		if (s.equals("down")) {
			testtank = new Tank(tank.getRect().x,tank.getRect().y+tank.getMoveS()
					,tank.getRect().width,tank.getRect().height, tank.getTeam(),tank.getColor());
			for (Wall w : walls) {
				if (testtank.hit(w) == true) {
					System.out.println("hit");
				} else {
					tank.moveDown();
				}
			}
		}

		if (s.equals("left")) {
			testtank = new Tank(tank.getRect().x-tank.getMoveS(),tank.getRect().y
					,tank.getRect().width,tank.getRect().height, tank.getTeam(),tank.getColor());
			for (Wall w : walls) {
				if (testtank.hit(w) == true) {
					System.out.println("hit");
				} else {
					tank.moveLeft();
				}
			}
		}
		
		if (s.equals("right")) {
			testtank = new Tank(tank.getRect().x+tank.getMoveS(),tank.getRect().y
					,tank.getRect().width,tank.getRect().height, tank.getTeam(),tank.getColor());
			for (Wall w : walls) {
				if (testtank.hit(w) == true) {
					System.out.println("hit");
				} else {
					tank.moveRight();
				}
			}
		}
		
	}

	public static void hit(String s) {
		game.keyHit(s);
	}

	protected static void drawGame(Graphics g) {
		game.draw(g);
		stop(g);
		Toolkit.getDefaultToolkit().sync();
	}

	public void draw(Graphics g) {
		for (Wall w : walls) {
			w.draw(g);
		}
		for (Tank t : tanks) {
			g.setColor(t.getColor());
			t.draw(g);
		}
	}

}
