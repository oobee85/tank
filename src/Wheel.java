import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Wheel extends Rectangle{
	private Rectangle rect;
	private Color color;
	private int x;
	private int y;
	private int w;
	private int h;
	private int speed;
	private int mw;
	private int xb;
	private int ls = 5;//linesize

	
	public Wheel(int xp, int yp, int wp, int hp, Color c, int maxw, int dx) {
		rect = new Rectangle(xp,yp,wp,hp);
		mw = maxw;
		x = xp;
		y = yp;
		w = wp;
		h = hp;
		speed = dx;
		color = c;
		xb = xp;
	}
	
	public int refresh(int t) {
		int turn = t;
		int dx;
		
		return dx = turn*speed;
	}

	public void draw(Graphics g, int dx, int px, int py) {
		if(rect != null){
			int out = dx;
			if( ((x+dx)<x-mw/2 +w/2)||(mw/2+w/2+xb)<(xb+dx+w) ) {//checks if out of bounds
				if(dx>=0) {//wheel at max level
					g.fillRect(x+mw/2+w/2-w, y, w,h);
				}else if(dx<=0) {//wheel at min level
					g.fillRect(x-mw/2+w/2, y, w,h);
				}
			}else {
				g.fillRect(x+dx, y, w,h);//draw wheel in bounds
			}
		
		}
		g.drawRect(x-mw/2+w/2,y,mw,h);//draws bounds
		g.drawLine(x+w/2,y+h,x+w/2,y+h+ls);//midline
		g.drawLine(x-mw/2+w/2,y+h,x-mw/2+w/2,y+h+ls);//minline
		g.drawLine(x+mw/2+w/2, y+h, x+mw/2+w/2,y+h+ls);//maxline
	}

	public Color getColor() {
		return color;
	}
	
}
