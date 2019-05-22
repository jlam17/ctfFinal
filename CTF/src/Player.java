
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
	private double velX, velY;
	private int gridX, gridY;
	private Powerup q;
	private boolean isShooting;
	private Direction dOfShot;
	private double shotX, shotY;
	private int shotFrame;

	private int health;
	private int score;
	


	/**
	 * Creates the player
	 * 
	 * @param playerId The specific id used to identify the player
	 * @param x        The x cord of the player
	 * @param y        The y cord of the player
	 */
	public Player(int playerId, double x, double y) {
		this.health = 100;
		this.playerId = playerId;
		hasFlag = false;
		this.initialx = x;
		this.initialy = y;
		this.x = x;
		this.y = y;
		q = null;
		isShooting = false;
	}

	/**
	 * Draws the player on the screen
	 * 
	 * @param drawer The PApplet drawer used to draw the player on the screen
	 */
	public void draw(PApplet drawer, PImage[] images, int currentFrame, boolean hasFlag, int r, int g, int b) {
		tick();
		this.hasFlag = hasFlag;
		if (this.hasFlag) {
			drawer.tint(r, g, b);
			drawer.image(images[currentFrame], (float) x, (float) y);
			drawer.noTint();
		} else {
			drawer.image(images[currentFrame], (float) x, (float) y);
		}
		if (isShooting) {
			int deltaX = 0;
			int deltaY = 0;
			if(dOfShot == Direction.NORTH) {
				deltaY = -5;
			} else if (dOfShot == Direction.EAST) {
				deltaX = 5;
			} else if (dOfShot == Direction.WEST) {
				deltaX = -5;
			} else {
				deltaY = 5;
			}
			shotX += deltaX;
			shotY += deltaY;
			if (shotFrame == 15) {
				setShotFrame(16);
			} else if (shotFrame == 16) {
				setShotFrame(17);
			} else if (shotFrame == 17) {
				setShotFrame(18);
			} else {
				setShotFrame(15);
			} 
			drawer.image(images[shotFrame], (float)shotX, (float)shotY);
		}
	}
	
	public void drawShot(PApplet drawer, PImage[] images, int frame) {
		
	}

	/**
	 * makes the player move
	 */
	public void tick() {
		x += velX;
		y += velY;

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

	public void setVelX(double velX) {
		this.velX = velX;
	}

	public void setVelY(double velY) {
		this.velY = velY;
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
	 * Moves the player right
	 */
	public void moveRight(double m) {
		setVelX(m);
	}

	/**
	 * Moves the player left
	 */
	public void moveLeft(double m) {
		setVelX(m);
	}

	/**
	 * Moves the player up
	 */
	public void moveUp(double m) {
		setVelY(m);
	}

	/**
	 * Moves the player down
	 */
	public void moveDown(double m) {
		setVelY(m);
	}

	public void move(double x, double y) {
//		setVelX(x - this.x);
//		setVelY(y - this.y);
		setX(x);
		setY(y);
	}

	/**
	 * Stop the player from moving down
	 */
	public void stopMoveDown() {
		setVelY(0);
	}

	/**
	 * Stop the player from moving right
	 */
	public void stopMoveRight() {
		setVelX(0);
	}

	/**
	 * Stop the player from moving left
	 */
	public void stopMoveLeft() {
		setVelX(0);
	}

	/**
	 * Stop the player from moving up
	 */
	public void stopMoveUp() {
		setVelY(0);
	}

	/**
	 * Keeps track of whether or not you have won
	 * 
	 * @return True or false if you have won
	 */
	public boolean hasWon() {
		if (hasFlag && Math.abs(x-initialx)<=5 && Math.abs(y-initialy)<=5) { // set x to 0 so there wouldnt be a compiler error for now
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @return health of the player
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * changes the health by deltaHealth (positive or negative)
	 * 
	 * @param deltaHealth the change in the health
	 */
	public void changeHealth(int deltaHealth) {
		this.health += deltaHealth;
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
	 * Shoots a sword beam in the direction of the player
	 */
	public void shoot(PApplet drawer, PImage[] images, int frame, Grid mazeGrid, Direction d, double x, double y) {
		setIsShooting(true);
		dOfShot = d;
		shotX = x;
		shotY = y;
		shotFrame = frame;
		drawer.image(images[frame], (float)x, (float)y);
		
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
