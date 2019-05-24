import processing.core.PImage;

/**
 * the flag that players carry to their base (from their opponent's) to win
 * @author hdesouza538
 *
 */
public class Flag implements Carryable {
	private Player home;
	private boolean possession;
	private double initialx, initialy;
	private double y;
	private double x;
	
	/**
	 * initializes Flag object by initializing variables home, enemy, x, y, initalx, initialy
	 * @param p the PImage of the flag
	 * @param home the player whose flag it is
	 * @param x the x value the flag is created at
	 * @param y the y value the flag is created at
	 */
	public Flag(PImage p, Player home, double x, double y) {
		this.initialx = x;
		this.initialy = y;
		this.x = x;
		this.y = y;
		this.home = home;
		this.possession = false;
	}

	/**
	 * draws the flag to the screen
	 * @param drawer the DrawingSurface that the flag will be drawn onto
	 * @param img the PImage that will be drawn
	 */
	public void draw(DrawingSurface drawer, PImage img) {
		drawer.image(img, (float)x, (float)y);
	}
	
	/**
	 * checks whether the player trying to pick the flag up can (if it is already held or not) and 
	 * changes the possession of the flag
	 * @param p the player trying to pick up the flag
	 * @return true if the flag is picked up and is not already held by the enemy, false otherwise
	 */
	public boolean pickUp(Player p) {
		if(this.possession) {
			return false;
		}
		this.possession = true;
		return true;
	}

	/**
	 * implemented method from Carryable
	 * @return false
	 */
	public boolean use() {
		return false;
	}

	/**
	 * creates the flag at the home player's base (in the same place)
	 */
	public void generate() {
		this.x = initialx;
		this.y = initialy;
		this.possession = false;
	}
	
	/**
	 * returns the player whose flag it is
	 * @return the player whose flag it is
	 */
	public Player getHome() {
		return home;
	}
	
	/**
	 * returns the player that controls the flag currently
	 * @return the player that controls the flag currently
	 */
	public boolean isPossession() {
		return possession;
	}
	
	/**
	 * changes the player variable for who controls the flag
	 * @param possession which player holds or controls the flag
	 */
	public void setPossession(boolean possession) {
		this.possession = possession;
	}
	
	/**
	 * returns the initial generated value of x of the flag
	 * @return the initial generated value of x of the flag
	 */
	public double getInitialx() {
		return initialx;
	}
	
	/**
	 * returns the initial generated value of y of the flag
	 * @return the initial generated value of y of the flag
	 */
	public double getInitialy() {
		return initialy;
	}

	/**
	 * returns the y-value of the flag
	 * @return y-value of the flag
	 */
	public double getY() {
		return y;
	}

	/**
	 * sets the y-value of the flag to the passed-in parameter
	 * @param y the y-value of the flag
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * returns the x-value of the flag
	 * @return the x-value of the flag
	 */
	public double getX() {
		return x;
	}

	/**
	 * sets the x-value of the flag to the passed-in parameter
	 * @param x the x-value of the flag
	 */
	public void setX(double x) {
		this.x = x;
	}
}
