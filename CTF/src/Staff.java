/**
 * This is a staff that you can pick up and shoot at other players.
 * @author jadonlam
 *
 */
public class Staff extends Powerup {

	/**
	 * Constructs the Staff
	 */
	public Staff() {
		super();
		//generate image
	}
	
	/**
	 * Uses the staff if you have one already
	 * @return true if you can use it, false otherwise
	 */
	public boolean use() {
		return false;
		//graphics for shooting, etc.
	}
	
	/**
	 * This will return whether or not you can actually pick it up or not and picks it up if you can.
	 * @return boolean Whether or not you can actually use the staff
	 */
	public boolean pickUp(Player p) {
		super.pickUp(p);
		return false; // just to compile
		//graphics stuff
	}
	
	/**
	 * Generates the item
	 */
	public void generate() {
		//something random
	}

}
