
public abstract class Powerup implements Carryable {
	private Player play = null;
	
	public Powerup() {
		generate();
	}
	
	public boolean pickUp(Player p) {
		if(play==null) {
			play = p;
			return true;
		}
		return false;
	}
	public abstract boolean use();
	public void generate() {
		//something random
	}

	public Player getPlay() {
		return play;
	}
}
