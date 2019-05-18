/**
 * 
 * @author Wikipedia
 *
 */
public enum Direction {

	NORTH(0, -1), EAST(1, 0), SOUTH(0, 1), WEST(-1, 0);

	/**
	 * 
	 * @return opposite of a direction
	 */
	public Direction opposite() {
		return values()[(ordinal() + 2) % 4];
	}

	/**
	 * x-coordinate
	 */
	public final int x;
	/**
	 * y-coordinate
	 */
	public final int y;

	/**
	 * private constructor
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	private Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
}