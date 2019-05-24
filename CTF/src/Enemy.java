import java.util.Random;

import processing.core.PImage;
/**
 * an AI enemy that heads for and attacks players while they are playing
 * @author hdesouza538
 *
 */
public class Enemy {
	private double x, y;
	private int gridX, gridY;
	private int health;
	private Direction direction;
	
	

	/**
	 * initializes Enemy as well as its x and y variables
	 * @param x the initial x value of the enemy
	 * @param y the initial y value of the enemy
	 */
	public Enemy(double x, double y, int gridX, int gridY, Direction d) {
		this.x = x;
		this.y = y;
		this.gridX = gridX;
		this.gridY = gridY;
		this.health = 100;
		this.direction = d;
	}
	
	public int getGridX() {
		return gridX;
	}
	
	public int getGridY() {
		return gridY;
	}
	
	public void setGridX(int g) {
		gridX = g;
	}
	
	public void setGridY(int g) {
		gridY = g;
	}
	public void draw(DrawingSurface drawer, PImage img, double x, double y) {
		drawer.image(img, (float)x, (float)y);
	}
	
	public void move(int newX, int newY) {
		setGridX(newX);
		setGridY(newY);
	}
	/**
	 * finds and attacks the closest player
	 * @param player1 the first player
	 * @param player2 the second player
	 */
	public void pursue(Player player1, Player player2) {
		Player toAttack = findClosestPlayer(player1, player2);
		while (this.health>0 && toAttack.getHealth()>0) {
			//moveToPlayer(toAttack);
			attack(toAttack);
		}
		
	}
	
	/**
	 * does the job of attacking the player
	 * @param p the player to attack
	 */
	private void attack(Player p) {
		if(true) {//touching
			p.changeHealth(-20);
		}
	}
	
	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
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

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

//	private void moveToPlayer(Player toAttack) {
//		// move through maze to player
//		if(!(Math.abs(x-toAttack.getX())<=1 && Math.abs(y-toAttack.getY())<=1)){
//			moveToPlayer(toAttack);
//		}
//	}

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
