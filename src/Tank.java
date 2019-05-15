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
	private Rectangle back;
	private String team;
	private Color color;
	private double rotate = 45;
	double maxturn = 16; //number/quadrants = directions supported
	private int MOVESIZE = 10;

	public Tank(int x, int y, int w, int h, String t, Color c) {
		rect = new Rectangle(x, y, w, h);
		team = t;
		color = c;
		back = canMoveB();
	}

	public void draw(Graphics g, int mx, int my) {
		
		if (rect != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.rotate(Math.toRadians(10), 240 + 480 / 2, 240 + 480 / 2);		// rotates screen to get diagonal move
//			g.drawRect(rect.x, rect.y, rect.width, rect.height);
			g2d.rotate(Math.toRadians(-10), 240 + 480 / 2, 240 + 480 / 2);		// rotates screen back to normal location
			g.fillRect(rect.x, rect.y, rect.width, rect.height);				// draws main tank
			
			Rectangle forw = canMoveF();
			g.drawRect((int)forw.getX(), (int)forw.getY(), (int)forw.getWidth()-1, (int)forw.getHeight()-1); //draws next move of tank
			g.fillRect((int)back.getX(), (int)back.getY(), (int)back.getWidth(), (int)back.getHeight());	 //draws prev position of tank
			
			g.drawLine(forw.x,forw.y+forw.height-1,rect.x,rect.y+rect.height-1);						//draws lines between next move and tank
			g.drawLine(forw.x+forw.width-1,forw.y,rect.x+rect.width-1,rect.y);
			g.drawLine(forw.x,forw.y,rect.x,rect.y);
			g.drawLine(forw.x+forw.width-1,forw.y+forw.height-1,rect.x+rect.width-1,rect.y+rect.height-1);
			
			
			
			for(int x = 0; x<rect.width; x++) {
				for(int x2 = 0; x2 <back.width; x2++) {
					g.drawLine(rect.x+x, rect.y, back.x+x2, back.y);//fills in the space between the 
				}													//top faces of the rectangles
			}
			for(int y = 0; y<rect.height; y++) {
				for(int y2 = 0; y2 <back.height; y2++) {
					g.drawLine(rect.x, rect.y+y, back.x, back.y+y2);//fills in the space between the 
				}													//left faces of the rectangles
			}
			for(int x = 0; x<rect.width; x++) {
				for(int x2 = 0; x2 <back.width; x2++) {
					g.drawLine(rect.x+x, rect.y+rect.height-1, back.x+x2, back.y+back.height-1);
																	//fills in the space
				}													//between the bottom faces 
			}														//of the rectangles
			int dx = (int)rect.getX()-mx;
			int dy = (int)rect.getY()-my;
			
			g.setColor(Color.BLACK);
			g.fillRect(rect.x+rect.width/4+1, rect.y+rect.height/4, rect.width/2+1, rect.height/2+1);
			g.drawLine(rect.x+rect.width/2, rect.y+rect.height/2, rect.x+rect.width+dx, rect.y+rect.height+dy);
			
			g.setColor(color);
			
		}
		
		
		if(Game.aiming == true) {
			aim(g, mx, my);			
		}
		
	}
	
	private void aim(Graphics g, int mx, int my) {
		int dx = (int)rect.getX()-mx;
		int dy = (int)rect.getY()-my;
		g.drawLine((int)(rect.getX()+rect.getWidth())+2*dx, (int)(rect.getY()+rect.getHeight())+2*dy, mx, my);
		g.drawString(mx+", "+my, 300, 50);
		g.setColor(Color.BLACK);
		
	}
	public void moveForward() {
		int x = (int)(Math.sin(Game.turn/(maxturn)*2*Math.PI)*MOVESIZE);
		int y = (int)(Math.cos(Game.turn/(maxturn)*2*Math.PI)*MOVESIZE*-1);
		Rectangle temp = new Rectangle(rect.x,rect.y,rect.width,rect.height);
		back = temp;
		
//		System.out.println(x+"x");
//		System.out.println(y+"y");
		rect.translate(x, y);
	}
	public Rectangle canMoveF() {
		int x = (int)(Math.sin(Game.turn/(maxturn)*2*Math.PI)*MOVESIZE);
		int y = (int)(Math.cos(Game.turn/(maxturn)*2*Math.PI)*MOVESIZE*-1);
		Rectangle nextPo = new Rectangle((int)rect.getX(),(int)rect.getY(),(int)rect.getWidth(),(int)rect.getHeight());
		nextPo.translate(x, y);
		return nextPo;
		
	}
	public Rectangle canMoveB() {
		int x = (int)(Math.sin(Game.turn/(maxturn)*2*Math.PI)*MOVESIZE);
		int y = (int)(Math.cos(Game.turn/(maxturn)*2*Math.PI)*MOVESIZE*-1);
		Rectangle nextPo = new Rectangle((int)rect.getX(),(int)rect.getY(),(int)rect.getWidth(),(int)rect.getHeight());
		nextPo.translate(x*-1, y*-1);
		return nextPo;
		
	}
	
	public void moveBack() {
		
		int x = (int)(Math.sin(Game.turn/(maxturn)*2*Math.PI)*MOVESIZE);
		int y = (int)(Math.cos(Game.turn/(maxturn)*2*Math.PI)*MOVESIZE*-1);
		Rectangle temp = new Rectangle(rect.x,rect.y,rect.width,rect.height);
		back = temp;
		
//		System.out.println(x+"x");
//		System.out.println(y+"y");
		rect.translate(x/2*-1, y/2*-1);
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
	
	public boolean dead(Projectile p) {
		return true;
	}

	public Rectangle collisionRect(Wall w) {
		return this.rect.intersection(w.getRect());
	}

	public static double area(Rectangle rect) {
		return rect.width * rect.height;
	}

}
