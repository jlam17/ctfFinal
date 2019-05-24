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
		this.direction = d;
	}
	
	/**
	 * returns the gridX value (2D int coordinate) of the enemy
	 * @return the gridX value (2D int coordinate) of the enemy
	 */
	public int getGridX() {
		return gridX;
	}
	
	/**
	 * returns the gridY value (2D int coordinate) of the enemy
	 * @return the gridY value (2D int coordinate) of the enemy
	 */
	public int getGridY() {
		return gridY;
	}
	
	/**
	 * sets the 2D x int coordinate of the enemy
	 * @param g the 2D x int coordinate to move to
	 */
	public void setGridX(int g) {
		gridX = g;
	}
	
	/**
	 * sets the 2D y int coordinate of the enemy
	 * @param g the 2D y int coordinate to move to
	 */
	public void setGridY(int g) {
		gridY = g;
	}
	
	/**
	 * draws the enemy on the DrawingSurface using the passed in variables
	 * @param drawer the DrawingSurface the enemy will be drawn upon
	 * @param img the image of the enemy
	 */
	public void draw(DrawingSurface drawer, PImage img) {
		drawer.image(img, (float)x, (float)y);
	}
	
	/**
	 * changes the 2D int coordinates of the enemy
	 * @param x the x int coordinate to move to
	 * @param y the y int coordinate to move to
	 */
	public void move(int newX, int newY) {
		setGridX(newX);
		setGridY(newY);
	}
	
	/**
	 * returns the current direction of the enemy
	 * @return the current direction of the enemy
	 */
	public Direction getDirection() {
		return direction;
	}

	/**
	 * changes the direction of the enemy
	 * @param direction the direction to set the enemy's direction to
	 */
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	/**
	 * gets the x coord of the enemy
	 * @return The x coord of the enemy
	 */
	public double getX() {
		return x;
	}

	/**
	 * Sets the x coord of the enemy
	 * 
	 * @param x the new x coord of enemy
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * gets the y coord of the enemy
	 * @return The y coord of the enemy
	 */
	public double getY() {
		return y;
	}

	/**
	 * Sets the y coord of the enemy
	 * 
	 * @param y the new y coord of enemy
	 */
	public void setY(double y) {
		this.y = y;
	}
}
