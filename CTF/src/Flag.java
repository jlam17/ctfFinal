
public class Flag implements Carryable {
	private Player home, enemy;
	private boolean possession;
	private double initialx, initialy;
	private double y;
	private double x;
	
	public Flag(Player home, Player enemy, double x, double y) {
		this.initialx = x;
		this.initialy = y;
		this.enemy = enemy;
		this.home = home;
		this.possession = false;
	}

	@Override
	public boolean pickUp(Player p) {
		if(this.possession) {
			return false;
		}
		this.possession = true;
		return true;
	}

	@Override
	public boolean use() {
		return false;
	}

	@Override
	public void generate() {
		this.x = initialx;
		this.y = initialy;
		this.possession = false;
	}

	public Player getHome() {
		return home;
	}

	public Player getEnemy() {
		return enemy;
	}

	public boolean isPossession() {
		return possession;
	}

	public void setPossession(boolean possession) {
		this.possession = possession;
	}

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
