import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class Projectile {
	private Rectangle rect;
	private int speedx;
	private int speedy;
	private Color color;
	private Point point;
	public int t;
	public int curTime = 0;
	private int explodeSize;
	private Point tankPos;
//	private int g = -10;
//	private int v;
//	private int h;
	private Point center;
	private int spawnTime;
	
	private Rectangle lastPo = new Rectangle();
	private Rectangle vertex = new Rectangle();
	boolean hitVert = false;
	private Rectangle side;
	private int bounceSlow = 2; //larger number -> less slow
	
	public Projectile(int x, int y, int w, int hi, int sx, int sy, Color c, Point p, int time, int eS, Point tP){

		rect = new Rectangle(x, y, w, hi);
		speedx = sx;
		speedy = sy;
		color = c;
		point = p;
		t = Game.getTime()-time;
		spawnTime = time;
		explodeSize = eS;
		tankPos = tP;
		center = Game.centerScreen;
		side = rect;
//		v = 5;
//		h = 0+ t*v + t*t*g;
		
	}
	public Rectangle nexPo() {
		Rectangle next = new Rectangle();
		next.setBounds(rect.x+speedx, rect.y+speedy, rect.width, rect.height);
		return next;
	}

	
	public void bounce(int s, Rectangle w) {
		Rectangle bounce = new Rectangle();
		for(int i = 1; i < 6; i++) {
			
				if(i == 1 && (rect.x-w.x)>=0 && (w.y-rect.y)>=0) {
					if(Game.debugMode==true) {
						System.out.println("s1");
					}
					side.setBounds(w.x, rect.y, rect.x-w.x, w.y-rect.y);
					if(side.intersects(w)==false) {
						speedy *= -1;
						speedy = speedy/bounceSlow;
						bounce.setBounds(rect.x+speedx, rect.y+speedy, rect.width, rect.height);
						rect = bounce;
					}
				}else
				if(i == 2 && (rect.x-(w.x+w.width))>=0 && (rect.y-w.y)>=0) {
					if(Game.debugMode==true) {
						System.out.println("s2");
					}
					side.setBounds(w.x+w.width, w.y, rect.x-(w.x+w.width), rect.y-w.y);
					if(side.intersects(w)==false) {
						speedx *= -1;
						speedx = speedx/bounceSlow;
						bounce.setBounds(rect.x+speedx, rect.y+speedy, rect.width, rect.height);
						rect = bounce;
					}
				}else
				if(i == 3 && (rect.x-w.x)>=0 && (rect.y-(w.y+w.height))>=0) {
					if(Game.debugMode==true) {
						System.out.println("s3");
					}
					side.setBounds(w.x, w.y+w.height, rect.x-w.x, rect.y-(w.y+w.height));
					if(side.intersects(w)==false) {
						speedy *= -1;
						speedy = speedy/bounceSlow;
						bounce.setBounds(rect.x+speedx, rect.y+speedy, rect.width, rect.height);
						rect = bounce;
					}
				}else
				if(i == 4 && (w.x-rect.x)>=0 && (rect.y-w.y)>=0) {
					if(Game.debugMode==true) {
						System.out.println("s4");
					}
					side.setBounds(rect.x, w.y, w.x-rect.x-1, rect.y-w.y);
					if(side.intersects(w)==false) {
						speedx *= -1;
						speedx = speedx/bounceSlow;
						bounce.setBounds(rect.x+speedx, rect.y+speedy, rect.width, rect.height);
						rect = bounce;
					}
				}
				
				if(i == 5 && (w.x-rect.x)>=0 && (w.y-rect.y)>=0) {
					if(Game.debugMode==true) {
						System.out.println("s5");
					}
					side.setBounds(rect.x, rect.y, rect.x-w.x-1, rect.y-w.y-1);
					if(side.intersects(w)==false) {
						speedx *= -1;
						speedx = speedx/bounceSlow;
						bounce.setBounds(rect.x+speedx, rect.y+speedy, rect.width, rect.height);
						rect = bounce;
					}
				}
				
		}
		
		
		
	}

	public void move() {
		change();
		Rectangle temp = new Rectangle();
		temp.setBounds((int) rect.getX() + speedx, (int) rect.getY() + speedy, (int) rect.getWidth(),
				(int) rect.getHeight());
		rect = temp;
	}
	
	public void change() {
//		System.out.println(h);
//		t = Game.getTime()-spawnTime;;
//		h = (0+ t*v + t*t*g)/1000;
	}

	public Color getColor() {
		return color;
	}

	public void draw(Graphics g) {
		if (rect != null) {
			
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
			
			if(Game.debugMode==true) {
				g.setColor(Color.MAGENTA);
				g.drawRect(side.x, side.y, side.width, side.height);
				g.setColor(color);
			}
			
			
			
//			g.setColor(Color.BLUE);
//			g.fillRect(lastPo.x,lastPo.y,lastPo.width,lastPo.height);
//			g.setColor(Color.GREEN);
//			g.fillRect(vertex.x, vertex.y, vertex.width, vertex.height);
//			g.setColor(Color.CYAN);
//			g.fillRect(center.x, center.y, 10, 10);
			
//			g.fillRect(m, y, 2, 2);
//			g.drawArc(lastPo.x, lastPo.y,vertex.x-lastPo.x, vertex.y-lastPo.y, 1, 90);
			g.setColor(color);
		}
		
		if(Game.debugMode==true) {
			g.fillRect((int) point.getX() - 1, (int) point.getY() - 1, 2, 2);//aiming point
		}
		
		
	}

	private void fade(Graphics g) {
			if(rect.getWidth()==0) {
//				System.out.println("disap");
				Rectangle disAp = new Rectangle((int)rect.getX()+100, (int)rect.getY()+100, 1, 1);
				rect = disAp;
				
			}else {
//				System.out.println("lower");
				Rectangle temp = new Rectangle((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth()-1, (int)rect.getHeight()-1);
				rect = temp;
				curTime = 0;
				//g.fillRect(rect.x, rect.y, x, x);
			}
		}
		
	
	public void explode(Graphics g) {
		g.fillRect((int) (point.getX() - rect.getWidth()*explodeSize/2), (int) (point.getY() - rect.getHeight()*explodeSize/2),
				(int) rect.getWidth() * explodeSize, (int) rect.getHeight() * explodeSize);
		speedx = 0;
		speedy = 0;
	}
	
	public boolean hitVert() {
		if(rect.intersects(vertex)) {
			return true;
		}
		return false;
	}
	private int slowAmount = 1;
	
	public void slow() {
		if((Game.getTime()-spawnTime)/10==0) {
			System.out.println("asdf");
			if(speedx>0) {
				System.out.println("lowerx");
				speedx+= -1*slowAmount;
			}else {
				System.out.println("raisex");
				speedx+= slowAmount;
			}
			if(speedy>0) {
				System.out.println("lowery");
				speedy+= -1*slowAmount;
			}else {
				System.out.println("raisey");
				speedy+= slowAmount;
			}
		}
	}
	
	public boolean age() {
		
		if((Game.getTime()-spawnTime)>500) {
			return true;
		}
		return false;
	}
	
	public boolean hit() {
			if (rect.contains(point.getX(), point.getY())) {
				return true;
				
			} 
//			else if (rect.contains(rect.getX(), p.getY())) {
//				return true;
//				
//			}
			return false;
//		Rectangle over = collisionRect(point);
//		if (over.isEmpty()) {
//			return false;
//		}else {
//			return true;
//		}
	}

	public Rectangle ricochet(Wall w, Rectangle p) {
			return p.intersection(w.getRect());
	}
	
	private Rectangle collisionRect(Point p) {
		Rectangle temp = new Rectangle((int) p.getX() - 1, (int) p.getY() - 1, 2, 2);
		return this.rect.intersection(temp);
	}

	private Rectangle point(Point p) {
		Rectangle temp;
		return temp = new Rectangle((int) p.getX() - 1, (int) p.getY() - 1, 2, 2);
	}

	private static double area(Rectangle rect) {
		return rect.width * rect.height;
	}
	public Rectangle getRect() {
		return rect;
	}

}

