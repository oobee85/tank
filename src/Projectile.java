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
	public int time;
	public int curTime = 0;
	private int explodeSize;
	private Point tankPos;
	
	public Projectile(int x, int y, int w, int h, int sx, int sy, Color c, Point p, int t, int eS, Point tP) {

		rect = new Rectangle(x, y, w, h);
		speedx = sx;
		speedy = sy;
		color = c;
		point = p;
		time = t;
		explodeSize = eS;
		tankPos = tP;
	}

	public void move() {
//		speedx = 10;
		Rectangle temp = new Rectangle();
		temp.setBounds((int) rect.getX() + speedx, (int) rect.getY() + speedy, (int) rect.getWidth(),
				(int) rect.getHeight());
		rect = temp;
		change();
		
	}
	
	Rectangle lastPo = new Rectangle();
	Rectangle vertex = new Rectangle();
	int y;
	boolean hitVert = false;
	public void change() {
		
		int xdif =  Math.abs(tankPos.x-point.x);
		
		Point endPo = new Point(point.x-xdif/2,tankPos.y);
		lastPo.setBounds(endPo.x, endPo.y, 10, 10);
		
		int x = Math.abs(endPo.x-tankPos.x)/2;
		
		Point ver = new Point(endPo.x+x,(tankPos.x-lastPo.x));
		vertex.setBounds(ver.x, ver.y, 10, 10);
		
		if(hitVert() == true) {
			hitVert = true;
			System.out.println("hitvert");
		}
		if(hitVert == false) {
			speedx = -1*(rect.x-vertex.x)/(rect.width/2);
			speedy = -1*(rect.y-vertex.y)/(rect.height/2);
		}else {
			System.out.println("pastvert");
			speedx = -1*(rect.x-lastPo.x)/(rect.width/2);
			speedy = -1*(rect.y-lastPo.y)/(rect.height/2);
		}
		
		
		if(Math.signum(speedx)==1) {
			speedx+=2;
		}if(Math.signum(speedy)==1) {
			speedy+=2;
		}
		if(Math.signum(speedx)==-1) {
			speedx-=2;
		}if(Math.signum(speedy)==-1) {
			speedy-=2;
		}
		
		
		int yPo = 1/2*x*x;
//		System.out.println(yPo);
//		rect.setBounds((int)rect.getX(),yPo,(int)rect.getWidth(),(int)rect.getHeight());
		
	}

	public Color getColor() {
		return color;
	}

	public void draw(Graphics g) {
		if (rect != null) {
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
			g.setColor(Color.BLUE);
			g.fillRect(lastPo.x,lastPo.y,lastPo.width,lastPo.height);
			g.setColor(Color.GREEN);
			g.fillRect(vertex.x, vertex.y, vertex.width, vertex.height);
			g.setColor(Color.CYAN);
			
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
