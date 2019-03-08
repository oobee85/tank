import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

public class Tank {
	private static final double OVERLAP_THRESHOLD = 0.75;
	private static final double	HIT_THRESHOLD = 0;
	private Rectangle rect;
	private String team;
	private Color color;
	private double rotate = 10;
	
	private int MOVESIZE = 10;
	
	public Tank(int x, int y, int w, int h, String t, Color c) {
		rect = new Rectangle(x,y,w,h);
		team = t;
		color = c;
	}

	public void draw(Graphics g) {

		Graphics2D g2d = (Graphics2D)g; 
		g2d.rotate(Math.toRadians(10), 240+480/2, 240+480/2); 
		
		if(rect != null) {
			g.fillRect(rect.x, rect.y, rect.width,rect.height);
		}
		g2d.rotate(Math.toRadians(-10), 240+480/2, 240+480/2); 
	}
	public void moveUp() {
		rect.translate(0, -1*MOVESIZE);
	}
	public void moveDown() {
		rect.translate(0, MOVESIZE);
	}
	public void moveLeft() {
		rect.translate(-1*MOVESIZE, 0);
	}
	public void moveRight() {
		rect.translate(MOVESIZE, 0);
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
		if(over.isEmpty())
			return false;
		double thisArea = area(rect), 
				goArea = area(w.getRect()),
				overArea = area(over);
		return overArea > Math.min(thisArea, goArea)*HIT_THRESHOLD;
	}
	public Rectangle collisionRect(Wall w) {
		return this.rect.intersection(w.getRect());
	}
	public static double area(Rectangle rect) {
		return rect.width*rect.height;
	}
	
	
	
	
	
}
