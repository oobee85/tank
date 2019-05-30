import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall {
	private static final double	HIT_THRESHOLD = 0.1;
	private Rectangle rect;
	private int width;
	private int height;
	private Color color;
	private int x;
	private int y;

	public Wall(int x, int y, int width, int height, Color color) {
		rect = new Rectangle(x,y,width,height);
		this.width = width;
		this.height = height;
		this.color = color;
		this.x = x;
		this.y = y;
	}
	public int getFace(Rectangle inter) {
		//topside = 1
		//leftside = 2
		//botside = 3
		//rightside = 4
		Rectangle one = new Rectangle(rect.x, rect.y, rect.width, rect.height/4);
		Rectangle two = new Rectangle(rect.x, rect.y, rect.width/4, rect.height);
		Rectangle three = new Rectangle(rect.x, rect.y+rect.height-rect.height/4, rect.width, rect.height/4);
		Rectangle four = new Rectangle(rect.x+rect.width-rect.width/4, rect.y, rect.width/4, rect.height);
		if(inter.intersects(one)==true) {
			return 1;
		} 
		if(inter.intersects(two)==true) {
			return 2;
		}
		if(inter.intersects(three)==true) {
			return 3;
		}
		if(inter.intersects(four)==true) {
			return 4;
		}
		return -1;
	}
	
	public void draw(Graphics g) {
		if(rect != null) {
			g.fillRect(rect.x, rect.y, rect.width,rect.height);
		}
	}
	public Rectangle getPosition() {
		Rectangle position = new Rectangle(x, y, width, height);
		return position;
	}

	public Color getColor() {
		return color;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getwidth() {
		return width;
	}

	public int getheight() {
		return height;
	}
	public Rectangle getRect() {
		return this.rect;
	}

	
	
	
}
