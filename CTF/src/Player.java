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

import processing.core.PApplet;

/**
 * This is the character that you control and use to play the game
 * @author jadonlam
 *
 */
public class Player {
	private int playerId;
	private boolean hasFlag;
	private double x, y;
	private Powerup q;
	
	/**
	 * Creates the player
	 * @param playerId The specific id used to identify the player
	 * @param x The x cord of the player
	 * @param y The y cord of the player
	 */
	public Player(int playerId, double x, double y) {
		this.playerId = playerId;
		hasFlag = false;
		this.x = x;
		this.y = y;
		q = null;
	}
	
	/**
	 * Draws the player on the screen
	 * @param drawer The PApplet drawer used to draw the player on the screen
	 */
	public void draw(PApplet drawer) {
		
	}
	
	/**
	 * Returns your player id
	 * @return number which is your id
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * 
	 * @return Whether or not the player has the flag
	 */
	public boolean isHasFlag() {
		return hasFlag;
	}

	/**
	 * 
	 * @param hasFlag Sets whoever has the flag at the moment based on hasFlag
	 */
	public void setHasFlag(boolean hasFlag) {
		this.hasFlag = hasFlag;
	}

	/**
	 * 
	 * @return The x cord of your player
	 */
	public double getX() {
		return x;
	}

	/**
	 * Sets the x cord of your player
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * 
	 * @return The y cord of your player
	 */
	public double getY() {
		return y;
	}

	/**
	 * Sets the y cord of your player
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Toggles if you have the flag or not
	 * @param f The current flag
	 */
	public void toggleFlag(Flag f) {
		this.hasFlag = !hasFlag;
		f.pickUp(this);
	}
	
	/**
	 * Picks up the powerup "p"
	 * @param p
	 */
	public void pickUpPowerup(Powerup p) {
		q = p;
	}
	
	/**
	 * Uses the powerup once, and sets it to null(gets rid of it) if you used it
	 */
	public void usePowerup() {
		if(q != null) {
			q.use();
			q = null;
		}
	}
	
	/**
	 * Moves the player right
	 */
	public void moveRight() {
		x++;
	}
	
	/**
	 * Moves the player left
	 */
	public void moveLeft() {
		x--;
	}
	
	/**
	 * Moves the player up
	 */
	public void moveUp() {
		y--;
	}
	
	/**
	 * Moves the player down
	 */
	public void moveDown() {
		y++;
	}
	
	/**
	 * Keeps track of whether or not you have won
	 * @return True or false if you have won
	 */
	public boolean hasWon() {
		if(hasFlag) { //set x to 0 so there wouldnt be a compiler error for now
			return true;
		}
		else {
			return false;
		}
	}
}
