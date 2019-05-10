/**
 * All of the items that can be carried by the player
 * @author jadonlam
 *
 */
public interface Carryable {
	/**
	 * "p" picks up the item 
	 * @param p
	 * @return True or false if you could pick up the item
	 */
	boolean pickUp(Player p);
	/**
	 * Uses the item
	 * @return True or false if you can use the item
	 */
	boolean use();
	/**
	 * Creates the powerup at a random place on the map
	 */
	void generate();
}
