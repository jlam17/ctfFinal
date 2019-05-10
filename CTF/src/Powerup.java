/**
 * The PowerUp is something the player can pick up and use to kill players or alter the map
 * @author jadonlam
 *
 */
public abstract class Powerup implements Carryable {
	private Player play = null;
	
	/**
	 * Creates the powerup and puts it in a random spot
	 */
	public Powerup() {
		generate();
	}
	
	/**
	 * @param the player trying pick up the powerup
	 * @return True or false whether you can pick this powerup up 
	 */
	public boolean pickUp(Player p) {
		if(play==null) {
			play = p;
			return true;
		}
		return false;
	}
	
	/**
	 * Uses your current item
	 * @return True or false whether you can use the item
	 */
	public abstract boolean use();
	
	/**
	 * Puts the powerup somewhere random on the map
	 */
	public void generate() {
		//something random
	}

	/**
	 * 
	 * @return The current player carrying this powerup
	 */
	public Player getPlay() {
		return play;
	}
}
