import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Timer;

public class Player {
	private int playerId;
	private boolean hasFlag;
	double x, y;
	
	
	
	
	  private Powerup q;
	
	public Player(int playerId, double x, double y) {
//		this.playerId = playerId;
//		hasFlag = false;
		this.x = x;
		this.y = y;
	}
	
	public int getPlayerId() {
		return playerId;
	}

	public boolean isHasFlag() {
		return hasFlag;
	}

	public void setHasFlag(boolean hasFlag) {
		this.hasFlag = hasFlag;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public void toggleFlag(Flag f) {
		this.hasFlag = !hasFlag;
		f.pickUp(this);
	}
	
	public void pickUpPowerup(Powerup p) {
		q = p;
	}
	
	public void usePowerup() {
		if(q != null) {
			q.use();
			q = null;
		}
	}
	
	public void moveRight() {
		x++;
	}
	
	public void moveLeft() {
		x--;
	}
	
	public void moveUp() {
		y--;
	}
	
	public void moveDown() {
		y++;
	}
	
	public boolean hasWon() {
		if(hasFlag && x == 0) { //set x to 0 so there wouldnt be a compiler error for now
			return true;
		}
		else {
			return false;
		}
	}
	
//	//Below = sprite movement
//	//****** WE can add sound later
//
//	 /*
//	   * Link standing, doing nothing.
//	   */
//	  public void stand() {
//		  action = 0;
//	  }
//	  
//	  /*
//	   * Link slashes his sword.
//	   */
//	  public void walkRight() {
//		  if (action == 0) {
//			  action = 1;
//			  actionTimer.restart();
//		  }
//	  }
//	  
//	  /*
//	   * Link blocks with his shield.
//	   */
//	  public void attackRight() {
//		  if (action == 0) {
//			  action = 2;
//			  actionTimer.restart();
//		  } else if(action == 2) {
//			  action = 3;
//			  actionTimer.restart();
//		  }
//	  }
//	  
//	  /*
//	   * Draw link using the correct sprite.
//	   */
//	  public void draw(Graphics2D g2, ImageObserver io) {
//		    AffineTransform at = g2.getTransform();
//		    double xScale = (double)width / spriteRects[0].width;
//		    double yScale = (double)height / spriteRects[0].height;
//		    g2.drawImage(sprites, (int)x,(int)(y-yScale*spriteRects[action].height),(int)(x+xScale*spriteRects[action].width),
//		    		(int)y,spriteRects[action].x,spriteRects[action].y,spriteRects[action].x+spriteRects[action].width,
//		    		spriteRects[action].y+spriteRects[action].height,io);
//	  }
//	
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		
//		surface.repaint();
//		
//	}
}
