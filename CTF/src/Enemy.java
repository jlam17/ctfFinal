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
}
