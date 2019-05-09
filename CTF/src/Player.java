
public class Player {
	private int playerId;
	private boolean hasFlag;
	double x, y;
	Powerup q = null;
	
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

	public Player(int playerId, double x, double y) {
		this.playerId = playerId;
		hasFlag = false;
		this.x = x;
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
		if(hasFlag && x) {
			return true;
		}
		else {
			return false;
		}
	}
}
