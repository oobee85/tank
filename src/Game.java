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
import java.awt.event.KeyEvent;
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
	private List<Target> targs = new ArrayList();
	private List<Spawn> spawns = new ArrayList();
	private List<String> tips = new ArrayList();
	private static int numSpawns = 8;
	protected static int turn = 0;
	static boolean aiming = false;
	protected static Point centerScreen;
	private int mx;
	private int my;
	protected static boolean debugMode = false;
	private int prevTarg = 0;
	private int kills = 0;
	private int curTip = 0;

	public Game(int x, int y) {
		tips.add("You can turn on auto-aim by pressing 'E'. ");
		tips.add("You can ricochet the projectile off of walls. ");
		tips.add("If long range shots are too difficult, move closer. ");
		tips.add("Don't forget to breathe. ");
		tips.add("Try to hit the target by ricocheting the projectile. ");
		tips.add("If earing points with the auto-aim is too easy, try playing without it. ");
		tips.add("If you keep missing, move closer to the target. ");
		tips.add("Take a water break every once in a while. ");
	
		
		
		fillTargs();
		centerScreen = new Point(x,y);
		Wheel wheel = new Wheel(100, 50, 10, 5, Color.gray, 100, 10);
		wheels.add(wheel);

		Wall wall = new Wall(50, 100, 900, 50, Color.GRAY);
		Wall wall1 = new Wall(50, 100, 50, 900, Color.GRAY);
		Wall wall2 = new Wall(900, 100, 50, 900, Color.GRAY);
		Wall wall3 = new Wall(300, 300, 30, 50, Color.GRAY);
		Wall wall4 = new Wall(500, 300, 50, 70, Color.GRAY);
		Wall wall5 = new Wall(200, 350, 30, 20, Color.GRAY);
		Wall wall6 = new Wall(400, 265, 50, 50, Color.GRAY);
		Wall wall7 = new Wall(230, 260, 20, 10, Color.GRAY);
		Wall wall8 = new Wall(130, 270, 30, 10, Color.GRAY);
		Wall wall9 = new Wall(800, 400, 50, 20, Color.GRAY);
		Wall wall10 = new Wall(110, 340, 40, 20, Color.GRAY);
		Wall wall11 = new Wall(500, 150, 40, 40, Color.GRAY);
		Wall wall12 = new Wall(400, 350, 30, 60, Color.GRAY);
		
		walls.add(wall);
		walls.add(wall1);
		walls.add(wall2);
		walls.add(wall3);
		walls.add(wall4);
		walls.add(wall5);
		walls.add(wall6);
		walls.add(wall7);
		walls.add(wall8);
		walls.add(wall9);
		walls.add(wall10);
		walls.add(wall11);
		walls.add(wall12);
		
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
//		}Wall
//	}
	
	public int getScore() {
		return kills*100;
	}
	
	private void fillTargs() {
		for(int i = 0; i<numSpawns;i++) {
			Point p = new Point(100 + 100*i, 200);
			Spawn s = new Spawn(p.x, p.y);
			spawns.add(s);
		}
		Target t = getTarg();
		targs.add(t);
		System.out.println("fillspawns");
		
	}
	private Target getTarg() {
		
		int x = (int)(Math.random()*numSpawns);
		if(Game.debugMode==true) {
			System.out.println("TargetNum: "+x);
		}
		if(x == prevTarg) {
			if((x+1)>numSpawns) {
				x-=1;
			}else {
				x +=1;
			}
	
		}
		Spawn s = spawns.get(x);
		Target t = new Target(s.getPoint().x, s.getPoint().y, 50, 50, Color.BLUE);
		prevTarg = x;
		return t;
		
	}
	
	public static int getTime() {
		return ticks;
	}
	public void updateMousePos(int x, int y) {
		mx = x;
		my = y;
	}
	
	// 10 milliseconds = 100 times per second
	protected void updateGame() {
		ticks++;// track number times timer gone off
		
		
		if (ticks % 2 == 0) {
			PointerInfo a = MouseInfo.getPointerInfo();
			Point b = a.getLocation();
			
//			mx = (int) b.getX();// -68
//			my = (int) b.getY();// -59

		}
		if(ticks%700 == 0) {
			if((curTip+1) >= tips.size()) {
				curTip = 0;
			}else {
				curTip ++;
			}
			System.out.println("curtip: "+curTip);
		}
		if (ticks % 100 == 0) {
//			System.out.println(ticks / 100 + " seconds");

		}
		if (ticks % 10 == 0) {
			for (int p =0;p<proj.size();p++) {
//				proj.get(p).slow();
				
				for(int wall = 0; wall<walls.size();wall++) {
					Rectangle inter = proj.get(p).ricochet(walls.get(wall), proj.get(p).nexPo());
					if(inter.isEmpty()==false) {
						proj.get(p).bounce(walls.get(wall).getFace(inter), walls.get(wall).getRect());
					}
				}
				if(proj.get(p).age()==true) {
					proj.remove(p);
				}else {
					proj.get(p).move();
				}
				
				
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
		
		if (str.equals("up")) {
			Tank testtank = tank;
			
			Rectangle nexPo = tank.canMoveF();
			testtank = new Tank((int)nexPo.getX(),(int)nexPo.getY(), tank.getRect().width,
					tank.getRect().height, tank.getTeam(), tank.getColor());
			int hit = 0;
			for (Wall w : walls) {
				if (testtank.hit(w) == true) {
					System.out.println("hit");
					hit++;
				} 
				
			}
			if(hit == 0) {
				tank.moveForward();
			}
			hit = 0;
			return;
		}

		if (str.equals("down")) {
			Tank testtank = tank;
			Rectangle nexPo = tank.canMoveB();
			testtank = new Tank((int)nexPo.getX(),(int)nexPo.getY(), tank.getRect().width,
					tank.getRect().height, tank.getTeam(), tank.getColor());
			
			int hit = 0;
			for (Wall w : walls) {
				if (testtank.hit(w) == true) {
					System.out.println("hit");
					hit++;
				} 
			}
			if(hit == 0) {
				tank.moveBack();
			}
			hit = 0;
			return;
		}
		if(str.equals("left")&&turn <= -16+1) {//resets turn when makes full revolution
			
			turn = 0;
			System.out.println(turn + "turnRes");
			return;
		}
		if (str.equals("left")) {
			Tank backtank = tank;
			Rectangle backpo = tank.nexMoveB(-1);//gives the future turn loc
			backtank = new Tank(backpo.x,backpo.y,tank.getRect().width,(int)tank.getRect().height,tank.getTeam(), tank.getColor() );
			int hit = 0;
			for (Wall w : walls) {
				if (backtank.hit(w) == true) {
					System.out.println("hit");
					hit++;
				} 
			}
			if(hit==0) {
				turn += -1;
			}
			System.out.println(turn + "turnL");
			return;
		}
		
		if (str.equals("right")&&turn >=16-1) {//resets turn when makes full revolution
			turn = 0;
			System.out.println(turn + "turnRes");
			return;
		}
		if (str.equals("right")) {
			Tank backtank = tank;
			Rectangle backpo = tank.nexMoveB(1);//gives the future turn loc
			backtank = new Tank(backpo.x,backpo.y,tank.getRect().width,(int)tank.getRect().height,tank.getTeam(), tank.getColor() );
			int hit = 0;
			for (Wall w : walls) {
				if (backtank.hit(w) == true) {
					System.out.println("hit");
					hit++;
				} 
			}
			if(hit==0) {
				turn += 1;
			}
			System.out.println(turn + "turnR");
			return;
		}
		
		if (str.equals("resturn")) {
			turn = 0;
			System.out.println(turn + "resturn");
			return;
		}
		if (str.equals("debug") && debugMode == false) {
			debugMode = true;
		} else if (str.equals("debug") && debugMode == true) {
			debugMode = false;
		}
		if (str.equals("aim") && aiming == false) {
			aiming = true;
		} else if (str.equals("aim") && aiming == true) {
			aiming = false;
		}
		if (str.equals("shot")) {
			int dx = (int) tank.getRect().getX() - mx;
			int dy = (int) tank.getRect().getY() - my;
			Point tp = new Point (tank.getRect().x, tank.getRect().y);
			Point p = new Point((int) (tank.getRect().getX() + tank.getRect().getWidth()) + 2 * dx,
					(int) (tank.getRect().getY() + tank.getRect().getHeight()) + 2 * dy);
			//Projectile(x,y,w,h speedx, speedy, color, point of explosion, spawn time, explode size, tankPosition)
			
//			Projectile asdf = new Projectile((int) (tank.getRect().getX() + tank.getRect().getWidth()) + 2 * dx,
//					(int) (tank.getRect().getY() + tank.getRect().getHeight()) + 2 * dy, 5, 5, 0, 0, Color.BLACK, p,
//					50, 5, tp);
			
//			Projectile test2 = new Projectile((int) (tank.getRect().getX()),
//					(int) (tank.getRect().getY()), 10, 10, 2, 2, Color.BLACK, p,
//					50, 5, tp);
			
//			Projectile test = new Projectile((int) (tank.getRect().getX()) ,
//					(int) (tank.getRect().getY()),5,5, 0,0,Color.RED,p,50,5);
			
			Projectile asdf = new Projectile((int)tank.getRect().getX(), (int)tank.getRect().getY(),5, 5, 		//old moving line projectile
					((int)tank.getRect().getX()-mx)/10, ((int)tank.getRect().getY()-my)/10, Color.BLACK, p, ticks/*spawn T */, 5, tp);
			
			proj.add(asdf);
//			proj.add(test);
//			proj.add(test2);
//			System.out.println(p.getX()+":"+p.getY()+" :point");
			return;
		}

	}


	protected void drawGame(Graphics g) {
		
		g.drawString("Tips: ", 550, 90);
		g.drawString(tips.get(curTip), 580, 90);
		g.drawString("Score: "+kills*100, 200, 60);
		g.drawString("Your wheel", 75, 45);
		draw(g);
		Toolkit.getDefaultToolkit().sync();
	}

	public void draw(Graphics g) {
		for (Wall w : walls) {
			g.setColor(w.getColor());
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
		
		if(targs.isEmpty()==true) {
			Target t = getTarg();
			targs.add(t);
		}
		for(int t=0;t<targs.size(); t++) {
			g.setColor(targs.get(t).getColor());
			boolean hit = false;
			for(int p = 0; p<proj.size();p++) {
				if(targs.get(t).getRect().intersects(proj.get(p).getRect())==true) {
					targs.remove(t);
					proj.remove(p);
					hit = true;
					kills++;
					
				}
			}
			
			if(hit == false) {
				targs.get(t).draw(g);
			}
		}
		
		

	}

}

