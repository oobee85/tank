import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class Tank {
	private static final double HIT_THRESHOLD = 0;
	private Rectangle rect;
	private String team;
	private Color color;
	private double rotate = 45;
	double maxturn = 16; //number/quadrants = directions supported
	private int MOVESIZE = 10;

	public Tank(int x, int y, int w, int h, String t, Color c) {
		rect = new Rectangle(x, y, w, h);
		team = t;
		color = c;
	}

	public void draw(Graphics g, int mx, int my) {
		if (rect != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.rotate(Math.toRadians(10), 240 + 480 / 2, 240 + 480 / 2);
		//	g.drawRect(rect.x, rect.y, rect.width, rect.height);
			g2d.rotate(Math.toRadians(-10), 240 + 480 / 2, 240 + 480 / 2);
			g.fillRect(rect.x, rect.y, rect.width, rect.height);
		}
		
		if(Game.aiming == true) {
			aim(g, mx, my);			
		}
		
	}
	
	private void aim(Graphics g, int mx, int my) {
		int dx = (int)rect.getX()-mx;
		int dy = (int)rect.getY()-my;
		g.drawLine((int)(rect.getX()+rect.getWidth())+2*dx, (int)(rect.getY()+rect.getHeight())+2*dy, mx, my);
		g.setColor(Color.BLACK);
		
	}
	public void moveForward() {
	
		int x = (int)(Math.sin(Game.turn/(maxturn)*2*Math.PI)*MOVESIZE);
		int y = (int)(Math.cos(Game.turn/(maxturn)*2*Math.PI)*MOVESIZE*-1);
//		System.out.println(x+"x");
//		System.out.println(y+"y");
		rect.translate(x, y);
		
	}
	
	public void moveBack() {
		
		int x = (int)(Math.sin(Game.turn/(maxturn)*2*Math.PI)*MOVESIZE);
		int y = (int)(Math.cos(Game.turn/(maxturn)*2*Math.PI)*MOVESIZE*-1);
		System.out.println(x+"x");
		System.out.println(y+"y");
		rect.translate(x*-1, y*-1);
	}

	public void moveLeft(int num) {
		rect.translate((int)( Math.cos(Math.toRadians((90*Math.abs(num)+rotate)*Game.turn))*MOVESIZE), -1*MOVESIZE);
	}

	public void moveRight(int num) {
		rect.translate((int) (Math.sin(Math.toRadians((270+rotate)*Game.turn)) * MOVESIZE), -1 * MOVESIZE);
	}

	public void turnR() {

	}

	public void turnL() {

	}

	public Rectangle getRect() {
		return this.rect;
	}

	public Color getColor() {
		return color;
	}

	public int getMoveS() {
		return MOVESIZE;
	}

	public String getTeam() {
		return team;
	}

	public boolean hit(Wall w) {
		Rectangle over = collisionRect(w);
		if (over.isEmpty())
			return false;
		double thisArea = area(rect), goArea = area(w.getRect()), overArea = area(over);
		return overArea > Math.min(thisArea, goArea) * HIT_THRESHOLD;
	}

	public Rectangle collisionRect(Wall w) {
		return this.rect.intersection(w.getRect());
	}

	public static double area(Rectangle rect) {
		return rect.width * rect.height;
	}

}
