
public class Fireball {
	private int gridX, gridY;
	private Direction d;
	private boolean hasBeenShot;
	
	public Fireball(int gX, int gY, Direction dOfShot, boolean shot) {
		gridX = gX;
		gridY = gY;
		d = dOfShot;
		hasBeenShot = shot;
	}
	
	public int getGridX() {
		return gridX;
	}
	
	public void setGridX(int gX) {
		gridX = gX;
	}
	
	public void setGridY(int gY) {
		gridY = gY;
	}
	
	public int getGridY() {
		return gridY;
	}
	
	public Direction getDirection() {
		return d;
	}
	
}
