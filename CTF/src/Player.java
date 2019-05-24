
import processing.core.PApplet;
import processing.core.PImage;

/**
 * This is the character that you control and use to play the game
 * 
 * @author jadonlam
 *
 */
public class Player {
	private int playerId;
	private boolean hasFlag;
	private double x, y;
	private double initialx, initialy;
	private Powerup q;
	private boolean isShooting;
	private Direction dOfShot;
	private double shotX, shotY;
	private int shotGridX, shotGridY;
	private int shotFrame;
	private Grid maze;
	private int deltaX, deltaY;
	private int score;
	private boolean isDead;

	/**
	 * Creates the player
	 * 
	 * @param playerId The specific id used to identify the player
	 * @param x        The x cord of the player
	 * @param y        The y cord of the player
	 */
	public Player(int playerId, double x, double y) {
		this.playerId = playerId;
		hasFlag = false;
		this.initialx = x;
		this.initialy = y;
		this.x = x;
		this.y = y;
		shotGridX = -1;
		shotGridY = -1;
		q = null;
		isShooting = false;
		isDead = false;
	}

	/**
	 * Draws the player on the screen
	 * 
	 * @param drawer The PApplet drawer used to draw the player on the screen
	 */
	public void draw(PApplet drawer, PImage[] images, int currentFrame, boolean hasFlag, int r, int g, int b) {
		this.hasFlag = hasFlag;
		if (this.hasFlag) {
			drawer.tint(r, g, b);
			drawer.image(images[currentFrame], (float) x, (float) y);
			drawer.noTint();
		} else {
			drawer.image(images[currentFrame], (float) x, (float) y);
		}
		if (isShooting) {
			deltaX = 0;
			deltaY = 0;
			if (maze.hasEdge(maze.vertex(shotGridX, shotGridY), dOfShot)) {
				if (dOfShot == Direction.NORTH) {
					deltaY = -60;
					shotGridY--;
				} else if (dOfShot == Direction.SOUTH) {
					deltaY = 60;
					shotGridY++;
				} else if(dOfShot == Direction.EAST) {
					deltaX = 60;
					shotGridX++;
				} else {
					deltaX = -60;
					shotGridX--;
				}
			}

			if (shotFrame == 15) {
				setShotFrame(16);
			} else if (shotFrame == 16) {
				setShotFrame(17);
			} else if (shotFrame == 17) {
				setShotFrame(18);
			} else {
				setShotFrame(15);
			}

			shotX += deltaX;
			shotY += deltaY;
			if (deltaX == 0 && deltaY == 0) {
				isShooting = false;
				setShotGridX(-1);
				setShotGridY(-1);
				deltaX = 0;
				deltaY = 0;
			} else {
				drawer.image(images[shotFrame], (float) shotX, (float) shotY);
				deltaX = 0;
				deltaY = 0;
			}
		} else {
			setShotGridX(-1);
			setShotGridY(-1);
		}
	}

	/**
	 * Returns your player id
	 * 
	 * @return number which is your id
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * 
	 * @return Whether or not the player has the flag
	 */
	public boolean hasFlag() {
		return hasFlag;
	}

	/**
	 * checks if the player is dead
	 * @return true if the player is dead, false if not
	 */
	public boolean isDead() {
		return isDead;
	}

	/**
	 * changes whether the player is dead or not
	 * @param isDead the value that will become whether the player is dead or not
	 */
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	
	/**
	 * 
	 * @param hasFlag Sets whoever has the flag at the moment based on hasFlag
	 */
	public void setHasFlag(boolean hasFlag) {
		this.hasFlag = hasFlag;
	}

	/**
	 * gets the x coord of the player
	 * @return The x coord of your player
	 */
	public double getX() {
		return x;
	}

	/**
	 * Sets the x coord of your player
	 * 
	 * @param x the new x coord of player
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * gets the y coord of the player
	 * @return The y coord of your player
	 */
	public double getY() {
		return y;
	}

	/**
	 * Sets the y coord of your player
	 * 
	 * @param y the new y coord of your player
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * returns the gridX value (2D int coord) of the shot
	 * @return the gridX value (2D int coord) of the shot
	 */
	public int getShotGridX() {
		return shotGridX;
	}
	
	/**
	 * returns the gridY value (2D int coord) of the shot
	 * @return the gridY value (2D int coord) of the shot
	 */
	public int getShotGridY() {
		return shotGridY;
	}
	
	/**
	 * sets the gridX value (2D int coord) of the shot
	 * @param gX the gridX value to change to
	 */
	public void setShotGridX(int gX) {
		shotGridX = gX;
	}
	
	/**
	 * sets the gridY value (2D int coord) of the shot
	 * @param gY the gridY value to change to
	 */
	public void setShotGridY(int gY) {
		shotGridY = gY;
	}
	
	/**
	 * Toggles if you have the flag or not
	 * 
	 * @param f The current flag
	 */
	public void toggleFlag(Flag f) {
		this.hasFlag = !hasFlag;
		f.pickUp(this);
	}

	/**
	 * Picks up the powerup "p"
	 * 
	 * @param p the powerup
	 */
	public void pickUpPowerup(Powerup p) {
		q = p;
	}

	/**
	 * Uses the powerup once, and sets it to null(gets rid of it) if you used it
	 */
	public void usePowerup() {
		if (q != null) {
			q.use();
			q = null;
		}
	}

	/**
	 * changes the x and y coordinates of the player
	 * @param x the x coordinate to move to
	 * @param y the y coordinate to move to
	 */
	public void move(double x, double y) {
		setX(x);
		setY(y);
	}

	/**
	 * Keeps track of whether or not you have won
	 * 
	 * @return True or false if you have won
	 */
	public boolean hasWon() {
		if (hasFlag && Math.abs(x - initialx) <= 5 && Math.abs(y - initialy) <= 5) { 
			return true;
		} else {
			return false;
		}
	}

	/**
	 * returns the score of your player
	 * @return The score of your player
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets the score of your player
	 * 
	 * @param y the new score of your player
	 */
	public void setScore(int y) {
		this.score = y;
	}

	/**
	 * Shoots a fireball in the direction of the player
	 * @param drawer the PApplet that the shot will be drawn on
	 * @param images the array of images of the shot
	 * @param frame the frame of the image of the shot
	 * @param mazeGrid the maze
	 * @param d the direction of the shot
	 * @param x the x value of the shot originally
	 * @param y the y value of the shot originally
	 * @param gridX the 2D int x coordinate of the shot
	 * @param gridY the 2D int y coordinate of the shot
	 */
	public void shoot(PApplet drawer, PImage[] images, int frame, Grid mazeGrid, Direction d, double x, double y,
			int gridX, int gridY) {
		if (!isShooting) {
			setIsShooting(true);
			dOfShot = d;
			shotX = x;
			shotY = y;
			shotFrame = frame;
			maze = mazeGrid;
			shotGridX = gridX;
			shotGridY = gridY;
			drawer.image(images[frame], (float) x, (float) y);
		} else {
			shotGridX = -1;
			shotGridY = -1;
		}

	}

	/**
	 * returns whether or not the player is currently shooting
	 * @return Whether or not the player is currently shooting
	 */
	public boolean getIsShooting() {
		return isShooting;
	}

	/**
	 * Set whether or not the player is currently shooting
	 * @param v the boolean that changes whether the player is shooting
	 */
	public void setIsShooting(boolean v) {
		isShooting = v;
	}

	/**
	 * changes the frame of the shot
	 * @param frame the frame that the shot is in
	 */
	public void setShotFrame(int frame) {
		shotFrame = frame;
	}
}
