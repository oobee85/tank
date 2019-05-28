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

	private Tank tank1;
	private Tank tank2;
	private List<Wall> walls = new ArrayList<>();
	private List<Tank> tanks = new ArrayList<>();
	private List<Projectile> proj = new ArrayList<>();
	private List<Wheel> wheels = new ArrayList<>();
	private List<Target> targets = new ArrayList<>();
	private List<Spawn> spawns = new ArrayList<>();
	
	protected static int turn = 0;
	static boolean aiming = false;
	protected static Point centerScreen;
	private int mx;
	private int my;
	protected static boolean debugMode = false;
	protected static int power1 = 100;
	private int kills = 0;
	protected static int spawnPoints = 12;
	private int prevSpawn = 0;
	
	public Game(int x, int y) {
		centerScreen = new Point(x,y);
		Wheel wheel = new Wheel(300, 200, 10, 5, Color.gray, 100, 10);
		wheels.add(wheel);
		
		
		Wall wall = new Wall(400, 300, 100, 100, Color.GRAY);
		Wall wall1 = new Wall(200, 300, 100, 100, Color.GRAY);
		Wall wall2 = new Wall(400, 100, 100, 100, Color.GRAY);
		Wall wall3 = new Wall(200, 100, 100, 100, Color.GRAY);
		
//		walls.add(wall);
//		walls.add(wall1);
//		walls.add(wall2);
//		walls.add(wall3);
		// Projectile a = new Projectile(100, 100, 10, 10, 5, -2, Color.BLUE);
		// proj.add(a);

		tank1 = new Tank(500, 500, 10, 10, "RED", Color.RED);
		tank2 = new Tank(200, 200, 10, 10, "BLUE", Color.BLUE);
		tanks.add(tank1);
//		tanks.add(tank2);
		fillSpawns();
		
	}
	public Game(int x, int y, int z) {
		centerScreen = new Point(x,y);
		Wheel wheel = new Wheel(300, 200, 10, 5, Color.gray, 100, 10);
		wheels.add(wheel);
		
		

		tank1 = new Tank(500, 500, 10, 10, "RED", Color.RED);
		tanks.add(tank1);
		
		fillSpawns();
	}
	
	public void fillSpawns() {
		
		for(int i=0; i<spawnPoints;i++) {
			System.out.println("filling"+i);
			Spawn s = new Spawn(100 +50*i, 100);
			spawns.add(s);
		}
	}
	
	private void makeTarg() {
		
		int x = (int)(Math.random()*spawnPoints);
		if(x == prevSpawn) {
			
			if((x+1)>= spawnPoints) {
				x-=1;
			}else {
				x+=1;
			}
		}
		prevSpawn = x;
		System.out.println(x);
		Point p = new Point(spawns.get(x).getPoint().x, spawns.get(x).getPoint().y);
		Target t = new Target(p.x, p.y, 50, 50, Color.BLUE);
		targets.add(t);
		
	}
	private void updateScore(Graphics g) {
		g.drawString("Score:"+kills*100, 30, 30);
		
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

		if (ticks % 1000 == 0) {
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
		if (str.equals("debug") && debugMode == false) {
			debugMode = true;
		} else if (str.equals("debug") && debugMode == true) {
			debugMode = false;
		}
		
		
		//tank1
		if (str.equals("up1")) {
			Tank testtank = tank1;
			
			Rectangle nexPo = tank1.canMoveF();
			testtank = new Tank((int)nexPo.getX(),(int)nexPo.getY(), tank1.getRect().width,
					tank1.getRect().height, tank1.getTeam(), tank1.getColor());
			int hit = 0;
			for (Wall w : walls) {
				if (testtank.hit(w) == true) {
					System.out.println("hit");
					hit++;
				} 
				
			}
			if(hit == 0) {
				tank1.moveForward();
			}
			hit = 0;
			return;
		}

		if (str.equals("down1")) {
			Tank testtank = tank1;
			Rectangle nexPo = tank1.canMoveB();
			testtank = new Tank((int)nexPo.getX(),(int)nexPo.getY(), tank1.getRect().width,
					tank1.getRect().height, tank1.getTeam(), tank1.getColor());
			
			int hit = 0;
			for (Wall w : walls) {
				if (testtank.hit(w) == true) {
					System.out.println("hit");
					hit++;
				} 
			}
			if(hit == 0) {
				tank1.moveBack();
			}
			hit = 0;
			return;
		}
		if(str.equals("left1")&&turn <= -16+1) {//resets turn when makes full revolution
			
			turn = 0;
			System.out.println(turn + "turnRes");
			return;
		}
		if (str.equals("left1")) {
			Tank backtank = tank1;
			Rectangle backpo = tank1.nexMoveB(-1);//gives the future turn loc
			backtank = new Tank(backpo.x,backpo.y,tank1.getRect().width,(int)tank1.getRect().height,tank1.getTeam(), tank1.getColor() );
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
//			System.out.println(turn + "turnL");
			return;
		}
		
		if (str.equals("right1")&&turn >=16-1) {//resets turn when makes full revolution
			turn = 0;
			System.out.println(turn + "turnRes");
			return;
		}
		if (str.equals("right1")) {
			Tank backtank = tank1;
			Rectangle backpo = tank1.nexMoveB(1);//gives the future turn loc
			backtank = new Tank(backpo.x,backpo.y,tank1.getRect().width,(int)tank1.getRect().height,tank1.getTeam(), tank1.getColor() );
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
//			System.out.println(turn + "turnR");
			return;
		}
		
		if (str.equals("resturn1")) {
			turn = 0;
			System.out.println(turn + "resturn");
			return;
		}
		
		if (str.equals("aim1") && aiming == false) {
			aiming = true;
		} else if (str.equals("aim") && aiming == true) {
			aiming = false;
		}
		if (str.equals("shot1")) {
			int dx = (int) tank1.getRect().getX() - mx;
			int dy = (int) tank1.getRect().getY() - my;
			Point tp = new Point (tank1.getRect().x, tank1.getRect().y);
			Point p = new Point((int) (tank1.getRect().getX() + tank1.getRect().getWidth()) + 2 * dx,
					(int) (tank1.getRect().getY() + tank1.getRect().getHeight()) + 2 * dy);
			//Projectile(x,y,w,h speedx, speedy, color, point of explosion, spawn time, explode size, tankPosition)
			
//			Projectile asdf = new Projectile((int) (tank.getRect().getX() + tank.getRect().getWidth()) + 2 * dx,
//					(int) (tank.getRect().getY() + tank.getRect().getHeight()) + 2 * dy, 5, 5, 0, 0, Color.BLACK, p,
//					50, 5, tp);
			
//			Projectile test2 = new Projectile((int) (tank.getRect().getX()),
//					(int) (tank.getRect().getY()), 10, 10, 2, 2, Color.BLACK, p,
//					50, 5, tp);
			
//			Projectile test = new Projectile((int) (tank.getRect().getX()) ,
//					(int) (tank.getRect().getY()),5,5, 0,0,Color.RED,p,50,5);
			
			Projectile asdf = new Projectile((int)tank1.getRect().getX(), (int)tank1.getRect().getY(),5, 5, 		//old moving line projectile
					((int)tank1.getRect().getX()-mx)/10, ((int)tank1.getRect().getY()-my)/10, Color.BLACK, p, ticks/*spawn T */, 5, tp);
			
			proj.add(asdf);
//			proj.add(test);
//			proj.add(test2);
//			System.out.println(p.getX()+":"+p.getY()+" :point");
			return;
		}
		//tank1
		
		
		
		//tank2
		if (str.equals("up2")) {
			Tank testtank = tank2;
			
			Rectangle nexPo = tank2.canMoveF();
			testtank = new Tank((int)nexPo.getX(),(int)nexPo.getY(), tank2.getRect().width,
					tank2.getRect().height, tank2.getTeam(), tank2.getColor());
			int hit = 0;
			for (Wall w : walls) {
				if (testtank.hit(w) == true) {
					System.out.println("hit");
					hit++;
				} 
				
			}
			if(hit == 0) {
				tank2.moveForward();
			}
			hit = 0;
			return;
		}

		if (str.equals("down2")) {
			Tank testtank = tank2;
			Rectangle nexPo = tank2.canMoveB();
			testtank = new Tank((int)nexPo.getX(),(int)nexPo.getY(), tank2.getRect().width,
					tank2.getRect().height, tank2.getTeam(), tank2.getColor());
			
			int hit = 0;
			for (Wall w : walls) {
				if (testtank.hit(w) == true) {
					System.out.println("hit");
					hit++;
				} 
			}
			if(hit == 0) {
				tank2.moveBack();
			}
			hit = 0;
			return;
		}
		if(str.equals("left2")&&turn <= -16+1) {//resets turn when makes full revolution
			
			turn = 0;
			System.out.println(turn + "turnRes");
			return;
		}
		if (str.equals("left2")) {
			Tank backtank = tank2;
			Rectangle backpo = tank2.nexMoveB(-1);//gives the future turn loc
			backtank = new Tank(backpo.x,backpo.y,tank2.getRect().width,(int)tank2.getRect().height,tank2.getTeam(), tank2.getColor() );
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
		
		if (str.equals("right2")&&turn >=16-1) {//resets turn when makes full revolution
			turn = 0;
			System.out.println(turn + "turnRes");
			return;
		}
		if (str.equals("right2")) {
			Tank backtank = tank2;
			Rectangle backpo = tank2.nexMoveB(1);//gives the future turn loc
			backtank = new Tank(backpo.x,backpo.y,tank2.getRect().width,(int)tank2.getRect().height,tank2.getTeam(), tank2.getColor() );
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
		
		if (str.equals("resturn2")) {
			turn = 0;
			System.out.println(turn + "resturn");
			return;
		}
		
		if (str.equals("aim2") && aiming == false) {
			aiming = true;
		} else if (str.equals("aim") && aiming == true) {
			aiming = false;
		}
		if (str.equals("shot2")) {
			int dx = (int) tank2.getRect().getX() - mx;
			int dy = (int) tank2.getRect().getY() - my;
			Point tp = new Point (tank2.getRect().x, tank2.getRect().y);
			Point p = new Point((int) (tank2.getRect().getX() + tank2.getRect().getWidth()) + 2 * dx,
					(int) (tank2.getRect().getY() + tank2.getRect().getHeight()) + 2 * dy);
			//Projectile(x,y,w,h speedx, speedy, color, point of explosion, spawn time, explode size, tankPosition)
			
//			Projectile asdf = new Projectile((int) (tank.getRect().getX() + tank.getRect().getWidth()) + 2 * dx,
//					(int) (tank.getRect().getY() + tank.getRect().getHeight()) + 2 * dy, 5, 5, 0, 0, Color.BLACK, p,
//					50, 5, tp);
			
//			Projectile test2 = new Projectile((int) (tank.getRect().getX()),
//					(int) (tank.getRect().getY()), 10, 10, 2, 2, Color.BLACK, p,
//					50, 5, tp);
			
//			Projectile test = new Projectile((int) (tank.getRect().getX()) ,
//					(int) (tank.getRect().getY()),5,5, 0,0,Color.RED,p,50,5);
			
			Projectile asdf = new Projectile((int)tank2.getRect().getX(), (int)tank2.getRect().getY(),5, 5, 		//old moving line projectile
					((int)tank2.getRect().getX()-mx)/10, ((int)tank2.getRect().getY()-my)/10, Color.BLACK, p, ticks/*spawn T */, 5, tp);
			
			proj.add(asdf);
//			proj.add(test);
//			proj.add(test2);
//			System.out.println(p.getX()+":"+p.getY()+" :point");
			return;
		}
		//tank2
		
		
		

	}


	protected void drawGame(Graphics g) {
		draw(g);
		Toolkit.getDefaultToolkit().sync();
	}
	
	private boolean tarHit(Target t, Projectile p) {
		if(t.getRect().intersects(p.getRect())==true) {
			return true;
		}
		return false;
				
	}
	
	public void draw(Graphics g) {
		updateScore(g);
		
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
			int px = (int) tank1.getRect().getX();
			int py = (int) tank1.getRect().getY();
			w.draw(g, temp, px, py);
		}
		
		if(targets.isEmpty()==true) {
			makeTarg();
		}
		for (int t=0; t<targets.size(); t++) {
			boolean hit = false;
			for (int p=0; p<proj.size(); p++) {
				
				if(targets.isEmpty()==false && tarHit(targets.get(t), proj.get(p))==true) {
					hit = true;
					kills++;
					targets.get(t).draw(g);
					targets.remove(t);
					proj.remove(p);
				}
			}
			if(hit == false) {
				targets.get(t).draw(g);
			}
			
			
		}
//		for (int t=0; t<targets.size();t++){
//			g.setColor(targets.get(t).getColor());
//			targets.get(t).draw(g);
//			System.out.println("draw"+t);
//			
//			for (int p=0; p<proj.size();p++) {
//				if(targets.isEmpty()==false && targets.get(t).isHit(proj.get(p))) {
//					kills++;
//					System.out.println("kills:"+kills);
//					targets.remove(t);
//					proj.remove(p);
//				}
//			}
//		}
		
		
	}

}

