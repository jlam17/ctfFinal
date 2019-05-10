/**
 * 
 * @author hdesouza538
 *
 */
public class Flag implements Carryable {
	private Player home, enemy;
	private boolean possession;
	private double initialx, initialy;
	private double y;
	private double x;
	
	/**
	 * 
	 * @param home
	 * @param enemy
	 * @param x
	 * @param y
	 */
	public Flag(Player home, Player enemy, double x, double y) {
		this.initialx = x;
		this.initialy = y;
		this.x = x;
		this.y = y;
		this.enemy = enemy;
		this.home = home;
		this.possession = false;
	}

	/**
	 * 
	 */
	public boolean pickUp(Player p) {
		if(this.possession) {
			return false;
		}
		this.possession = true;
		return true;
	}

	/**
	 * 
	 */
	public boolean use() {
		return false;
	}

	/**
	 * 
	 */
	public void generate() {
		this.x = initialx;
		this.y = initialy;
		this.possession = false;
	}
	
	/**
	 * 
	 * @return
	 */
	public Player getHome() {
		return home;
	}

	/**
	 * 
	 * @return
	 */
	public Player getEnemy() {
		return enemy;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isPossession() {
		return possession;
	}
	
	/**
	 * 
	 * @param possession
	 */
	public void setPossession(boolean possession) {
		this.possession = possession;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getInitialx() {
		return initialx;
	}
	
	public double getInitialy() {
		return initialy;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
}
