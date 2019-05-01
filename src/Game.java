import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.dnd.MouseDragGestureRecognizer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.AbstractAction;
import javax.swing.Timer;

public class Game {

	private static int ticks = 0;

	private Tank tank;
	private List<Wall> walls = new ArrayList<>();
	private List<Tank> tanks = new ArrayList<>();
	private List<Projectile> proj = new ArrayList();
	private List<Wheel> wheels = new ArrayList();
	protected static int turn = 0;
	static boolean aiming = false;
	private int mx;
	private int my;

	public Game() {
		Wheel wheel = new Wheel(300, 200, 10, 5, Color.gray, 100, 10);
		wheels.add(wheel);

		Wall wall = new Wall(0, 0, 100, 100, Color.black);
		walls.add(wall);

		// Projectile a = new Projectile(100, 100, 10, 10, 5, -2, Color.BLUE);
		// proj.add(a);

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

	// 10 milliseconds = 100 times per second
	protected void updateGame() {
		ticks++;// track number times timer gone off

		if (ticks % 2 == 0) {
			PointerInfo a = MouseInfo.getPointerInfo();
			Point b = a.getLocation();
			mx = (int) b.getX();// -68
			my = (int) b.getY();// -59

		}

		if (ticks % 100 == 0) {
//			System.out.println(ticks / 100 + " seconds");

		}
		if (ticks % 10 == 0) {
			for (Projectile p : proj) {
				p.move();
			}
			for (Wheel w : wheels) {
				w.refresh(turn);
			}

//			System.out.println(mx + "x;" + my + "y");
//			System.out.println(aiming);
		}

	}

	// What do you want to do when a key is hit?
	public void keyHit(String str) {
//		System.out.println("(keyHit): " + s);
		Tank testtank = tank;
		
		if (str.equals("up")) {
			testtank = new Tank(tank.getRect().x, tank.getRect().y - tank.getMoveS(), tank.getRect().width,
					tank.getRect().height, tank.getTeam(), tank.getColor());
			for (Wall w : walls) {
				if (testtank.hit(w) == true) {
					System.out.println("hit");
				} else {
					tank.moveForward();
				}
			}
			return;
		}

		if (str.equals("down")) {
			testtank = new Tank(tank.getRect().x, tank.getRect().y + tank.getMoveS(), tank.getRect().width,
					tank.getRect().height, tank.getTeam(), tank.getColor());
			for (Wall w : walls) {
				if (testtank.hit(w) == true) {
					System.out.println("hit");
				} else {
					tank.moveBack();
				}
			}
			return;
		}

		if (str.equals("left")) {
			turn += -1;
			System.out.println(turn + "turnL");
			return;
		}

		if (str.equals("right")) {
			turn += 1;
			System.out.println(turn + "turnR");
			return;
		}
		if (str.equals("resTurn")) {
			turn = 0;
			System.out.println(turn + "resTurn");
			return;
		}
		if (str.equals("aim") && aiming == false) {
			aiming = true;
		} else if (str.equals("aim") && aiming == true) {
			aiming = false;
		}
		if (str.equals("shot")) {
			int dx = (int) tank.getRect().getX() - mx;
			int dy = (int) tank.getRect().getY() - my;
			
			Point p = new Point((int) (tank.getRect().getX() + tank.getRect().getWidth()) + 2 * dx,
					(int) (tank.getRect().getY() + tank.getRect().getHeight()) + 2 * dy);
			//Projectile(x,y,w,h speedx, speedy, color, point of explosion, time to deteriorate, explode size)
			
			Projectile asdf = new Projectile((int) (tank.getRect().getX() + tank.getRect().getWidth()) + 2 * dx,
					(int) (tank.getRect().getY() + tank.getRect().getHeight()) + 2 * dy, 5, 5, 0, 0, Color.BLACK, p,
					50, 5);
			Projectile test = new Projectile((int) (tank.getRect().getX()) ,
					(int) (tank.getRect().getY()),5,5, 0,0,Color.RED,p,50,5);
			
//			Projectile asdf = new Projectile((int)tank.getRect().getX(), (int)tank.getRect().getY(),5, 5, 		//old moving line projectile
//					((int)tank.getRect().getX()-mx)/10, ((int)tank.getRect().getY()-my)/10, Color.BLACK, p);
			
			proj.add(asdf);
			proj.add(test);
//			System.out.println(p.getX()+":"+p.getY()+" :point");
			return;
		}

	}

	public void hit(String s) {
		keyHit(s);
	}

	protected void drawGame(Graphics g) {
		draw(g);
		Toolkit.getDefaultToolkit().sync();
	}

	public void draw(Graphics g) {
		for (Wall w : walls) {
			w.draw(g);
		}
		for (Tank t : tanks) {
			g.setColor(t.getColor());
			t.draw(g, mx, my);

		}
		for (Projectile p : proj) {
			g.setColor(p.getColor());
			if (p.hit() == true) {
				p.explode(g);
			}
			p.draw(g);
		}
		for (Wheel w : wheels) {
			g.setColor(w.getColor());
			int temp = w.refresh(turn);
			int px = (int) tank.getRect().getX();
			int py = (int) tank.getRect().getY();
			w.draw(g, temp, px, py);
		}

	}

}
