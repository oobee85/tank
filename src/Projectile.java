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
	private int g = -10;
	private int v;
	private int h;
	private Point center;
	private int spawnTime;
	
	Rectangle lastPo = new Rectangle();
	Rectangle vertex = new Rectangle();
	boolean hitVert = false;
	
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
		v = 5;
		h = 0+ t*v + t*t*g;
		
	}

	public void move() {
		change();
		Rectangle temp = new Rectangle();
		temp.setBounds((int) rect.getX() + speedx, (int) rect.getY() + speedy, (int) rect.getWidth(),
				(int) rect.getHeight());
		rect = temp;

		
		
	}
	
	
	
	public void change() {
		System.out.println(h);
		t = Game.getTime()-spawnTime;;
		h = (0+ t*v + t*t*g)/1000;
	}

	public Color getColor() {
		return color;
	}

	public void draw(Graphics g) {
		if (rect != null) {
			
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
			g.setColor(Color.RED);
//			if(h>300) {
				int h2 = 300-h;
				g.drawRect(rect.x, rect.y+h, rect.width, rect.height);
//			}else if(h<300) {
				g.drawRect(rect.x, rect.y+h, rect.width, rect.height);
//			}
			
			g.setColor(Color.BLUE);
			g.fillRect(lastPo.x,lastPo.y,lastPo.width,lastPo.height);
			g.setColor(Color.GREEN);
			g.fillRect(vertex.x, vertex.y, vertex.width, vertex.height);
			g.setColor(Color.CYAN);
			g.fillRect(center.x, center.y, 10, 10);
			
//			g.fillRect(m, y, 2, 2);
//			g.drawArc(lastPo.x, lastPo.y,vertex.x-lastPo.x, vertex.y-lastPo.y, 1, 90);
			g.setColor(color);
		}
		
		g.fillRect((int) point.getX() - 1, (int) point.getY() - 1, 2, 2);//aiming point
		
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
	public boolean isOld() {
		if((Game.getTime()-spawnTime)>5000) {
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

}

