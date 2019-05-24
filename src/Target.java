import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Target {

	private Rectangle rect;
	private Color color;
	
	public Target(int x, int y, int w, int h, Color c) {
		rect = new Rectangle(x, y, w, h);
		color = c;
	}
	public void draw(Graphics g) {
		
		g.setColor(color);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		
	}
	public void dodge(int d) {
		int dx = d;
		int dy = d;
		
		for(int r = 0;r<4; r++) {
			if(r==0) {
				double i =  Math.random();
				if(0<=i&& i<=0.25) {
					dx*=-1;
				}
			}
			if(r==1) {
				double i =  Math.random();
				if(0.25<i&& i<=0.5) {
					dy*=-1;
				}
			}
			if(r==2) {
				double i =  Math.random();
				if(0.5<i&& i<=0.75) {
					dx*=1;
				}
			}
			if(r==3) {
				double i =  Math.random();
				if(0.75<i&& i<=1) {
					dy*=1;
				}
			}
		}
		rect.setBounds(rect.x+dx,rect.y+dy,rect.width,rect.height);
		
	}
	public boolean isHit(Projectile p) {
		if(rect.intersects(p.getRect())) {
			return true;
		}
		return false;
	}
	
	public Rectangle getRect() {
		return rect;
	}
	public Color getColor() {
		return color;
	}
	
}

