
public class Staff extends Powerup {

	public Staff() {
		super();
		//generate image
	}
	
	public boolean use() {
		return false;
		//graphics for shooting, etc.
	}
	
	public boolean pickUp(Player p) {
		super.pickUp(p);
		//graphics stuff
	}
	
	public void generate() {
		//something random
	}

}
