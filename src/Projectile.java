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
	public Projectile(int x, int y, int w, int h, int sx, int sy, Color c, Point p, int t) {

		rect = new Rectangle(x, y, w, h);
		speedx = sx;
		speedy = sy;
		color = c;
		point = p;
		time = t;
	}

	public void move() {
		Rectangle temp = new Rectangle();
		temp.setBounds((int) rect.getX() + speedx, (int) rect.getY() + speedy, (int) rect.getWidth(),
				(int) rect.getHeight());
		rect = temp;
	}

	public Color getColor() {
		return color;
	}

	public void draw(Graphics g) {
		if (rect != null) {
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
		g.fillRect((int) point.getX() - 1, (int) point.getY() - 1, 2, 2);
//		System.out.println(point.getX()+":"+point.getY());
		if(curTime == time) {
			fade(g);
		}
		curTime++;
		
	}

	private void fade(Graphics g) {
		for(int x = 1; x<rect.getWidth(); x++) {
			if(rect.getWidth()==0) {
				Rectangle disAp = new Rectangle((int)rect.getX(), (int)rect.getY(), 0, 0);
				rect = disAp;
			}
			Rectangle temp = new Rectangle((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth()-x, (int)rect.getHeight()-x);
			rect = temp;
			curTime = 0;
			break;
			//g.fillRect(rect.x, rect.y, x, x);
		}
		
	}
	
	public void explode(Graphics g) {
		g.fillRect((int) (point.getX() - rect.getWidth()), (int) (point.getY() - rect.getHeight()),
				(int) rect.getWidth() * 5, (int) rect.getHeight() * 5);
		speedx = 0;
		speedy = 0;
	}

	public boolean hit() {
			if (rect.contains(point.getX(), rect.getY())) {
//				System.out.println(rect.getX() + ":" + rect.getY() + " :rect");
				return true;
			} else if (rect.contains(rect.getX(), point.getY())) {
//				System.out.println(rect.getX() + ":" + rect.getY() + " :rect");
				return true;
			}
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
