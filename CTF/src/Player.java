
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
			System.out.println("shotX: " + shotX + ", shotY:" + shotY);
			System.out.println(deltaX + "," + deltaY);
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

	public boolean isDead() {
		return isDead;
	}

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
	 * 
	 * @return The x cord of your player
	 */
	public double getX() {
		return x;
	}

	/**
	 * Sets the x cord of your player
	 * 
	 * @param x the new x coord of player
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
	 * 
	 * @param y the new y cord of your player
	 */
	public void setY(double y) {
		this.y = y;
	}

	public int getShotGridX() {
		return shotGridX;
	}
	
	public int getShotGridY() {
		return shotGridY;
	}
	
	public void setShotGridX(int gX) {
		shotGridX = gX;
	}
	
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
	 * 
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
	 * 
	 * @return Whether or not the player is currently shooting
	 */
	public boolean getIsShooting() {
		return isShooting;
	}

	/**
	 * Set whether or not the player is currently shooting
	 */
	public void setIsShooting(boolean v) {
		isShooting = v;
	}

	public void setShotFrame(int frame) {
		shotFrame = frame;
	}
}
