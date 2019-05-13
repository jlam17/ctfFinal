import java.util.Random;
/**
 * an AI enemy that heads for and attacks players while they are playing
 * @author hdesouza538
 *
 */
public class Enemy {
	private double x, y;
	private int health;
	
	/**
	 * initializes Enemy as well as its x and y variables
	 * @param x the initial x value of the enemy
	 * @param y the initial y value of the enemy
	 */
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		this.health = 100;
	}
	
	public void pursue(Player player1, Player player2) {
		Player toAttack = findClosestPlayer(player1, player2);
		while (this.health>0 && toAttack.getHealth()>0) {
			moveToPlayer();
			attack();
		}
		
	}
	
	private void attack(Player p) {
		if() {//touching
			p.changeHealth(-20);
		}
	}

	private void moveToPlayer() {
		// move through maze to player
		
	}

	/**
	 * finds the closest player to head for and attack
	 * @param player1 the first player playing
	 * @param player2 the second player playing
	 * @return the closest player to the enemy
	 */
	private Player findClosestPlayer(Player player1, Player player2) {
		double player1distance = 0, player2distance = 0;
		player1distance = Math.sqrt(Math.pow(player1.getX()-x, 2) + Math.pow(player1.getY()-y, 2));
		player2distance = Math.sqrt(Math.pow(player2.getX()-x, 2) + Math.pow(player2.getY()-y, 2));
		
		if(player1distance>player2distance) {
			return player1;
		}
		else if(player2distance>player1distance) {
			return player2;
		}
		else {
			Random randomNum = new Random();
			int result = randomNum.nextInt(2);
			if(result == 0) {
				return player1;
			}
			else {
				return player2;
			}
		}
	}
}
