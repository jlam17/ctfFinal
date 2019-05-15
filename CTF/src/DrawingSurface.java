
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

//import main.Algorithm;
import processing.core.*;



/**
 * the surface where drawings are made
 * @author hdesouza538
 *
 */
public class DrawingSurface extends PApplet {
	enum Algorithm {
		RANDOM_DFS_RECURSIVE,
		RANDOM_DFS_NONRECURSIVE,
		RANDOM_BFS,
		PRIM,
		RECURSIVE_DIVISION,
		ALDOUS_BRODER,
		WILSON,
		SIDEWINDER,
		BINARY_TREE
	}
	public static Grid maze(int numCols, int numRows, int startCol, int startRow, Algorithm algorithm) {
		Grid grid = new Grid(numCols, numRows);
		int startVertex = grid.vertex(startCol, startRow);
		BitSet visited = new BitSet(numCols * numRows);
		randomDFSRecursive(grid, startVertex, visited);
		return grid;
	}
	
	static void randomDFSRecursive(Grid grid, int v, BitSet visited) {
		visited.set(v);
		for (Direction dir = unvisitedDir(grid, v, visited); dir != null; dir = unvisitedDir(grid, v,
				visited)) {
			grid.addEdge(v, dir);
			randomDFSRecursive(grid, grid.neighbor(v, dir), visited);
		}
	}
	
	static Direction unvisitedDir(Grid grid, int v, BitSet visited) {
		List<Direction> unvisitedDirections = unvisitedDirections(grid, v, visited);
		return unvisitedDirections.isEmpty() ? null : unvisitedDirections.get(0);
	}
	
	static List<Direction> unvisitedDirections(Grid grid, int v, BitSet visited) {
		List<Direction> candidates = new ArrayList<>(4);
		for (Direction dir : Direction.values()) {
			int neighbor = grid.neighbor(v, dir);
			if (neighbor != Grid.NO_VERTEX && !visited.get(neighbor)) {
				candidates.add(dir);
			}
		}
		Collections.shuffle(candidates);
		return candidates;
	}
	
	Player player1, player2;
	int numFrames; // The number of frames in the animation
	int currentFrame = 0;
	PImage[] images;
	PImage wall, flag1, flag2;
	boolean[] keyDown;
	String maze;

	/**
	 * constructs the DrawingSurface, initializes player variables
	 */
	public DrawingSurface() {
		super();
		numFrames = 13;
		player1 = new Player(1, 10, 50);
//		player2 = new Player(2, 100, 50);
		images = new PImage[numFrames];
		keyDown = new boolean[4];
	}

	/**
	 * the settings of the DrawingSurface
	 */
	public void settings() {
		size(1000, 960);
	}

	/**
	 * initial one-time run commands
	 */
	public void setup() {
		
		settings();
		frameRate(24);
		
		images[9] = loadImage("background.png");
		images[9].resize(1000, 1000);
		this.image(images[9], 0, 0);
		
		flag1 = loadImage("flag.png");
		flag2 = loadImage("flag.png");
		
		flag1.resize(,126); //0.659090909
		
		this.image(flag1, 100, 300);
		this.image(flag2, 300, 100);
		
		//change the first two values in the maze call to change width/height
		maze = maze(11, 9, 0, 0, Algorithm.values()[0]).toString();
		
		//this.text("", 100, 100);
		wall = loadImage("wall.png");
		
		int y = 5;
		int x = 20;
		for(int i=0; i<874 && i<maze.length(); i++) {
			x=20;
			y+=36;
			while(!maze.substring(i, i+1).equals("\n")) {
				if(!maze.substring(i, i+1).equals(" ")) {
					if(maze.substring(i, i+1).equals("-")) {
						//this.line(x, y, x+18, y);
						this.image(wall, x, y, width/18, height/36);
					}
					else {
						//this.line(x, y, x, y+18);
						this.image(wall, x, y, width/36, height/18);
						
					}
				}
				i++;
				x+=18;
			}
		}
		
		
		
		//Loading and resizing the images
		images[0] = loadImage("knightStand.png");
		images[0].resize(126, 126);
		images[1] = loadImage("knightWalkRight.png");
		images[1].resize(96, 126);
		images[2] = loadImage("knightAttack1.png");
		images[2].resize(128,126);
		images[3] = loadImage("knightAttack2.png");
		images[3].resize(192,126);
		images[4] = loadImage("knightWalkDown1.png");
		images[4].resize(96, 128);
		images[5] = loadImage("knightWalkDown2.png");
		images[5].resize(128,128);
		images[6] = loadImage("knightStandUp.png");
		images[6].resize(126, 127);
		images[7] = loadImage("knightWalkUp1.png");
		images[7].resize(96, 126);
		images[8] = loadImage("knightWalkUp2.png");
		images[8].resize(128, 126);
		images[9] = loadImage("background.png");
		images[9].resize(1000, 1000);
		//this.image(images[9], 0, 0);
		images[10] = loadImage("knightStandLeft.png");
		images[10].resize(126, 126);
		images[11] = loadImage("knightWalkLeft.png");
		images[11].resize(96, 126);
		images[12] = loadImage("knightStandDown.png");
		images[12].resize(126, 128);
		
	}

	/**
	 * draws the background and players
	 */
	public void draw() {
		//background(255);
		
		player1.draw(this, images, currentFrame);
		
		//System.out.println(maze);
		
//		this.text("", 100, 100);
//		
//		int y = 5;
//		int x = 20;
//		for(int i=0; i<874; i++) {
//			x=20;
//			y+=36;
//			while(!maze.substring(i, i+1).equals("\n")) {
//				if(!maze.substring(i, i+1).equals(" ")) {
//					if(maze.substring(i, i+1).equals("-")) {
//						//this.line(x, y, x+18, y);
//						this.image(wall, x, y, width/4, height/8);
//					}
//					else {
//						//this.line(x, y, x, y+18);
//						this.image(wall, x, y, width/8, height/4);
//						
//					}
//				}
//				i++;
//				x+=18;
//			}
//		}
	}

	/**
	 * checks for what to do if a mouse button is pressed
	 */
	public void mousePressed() {

	}

	/**
	 * checks for what to do if a certain key is pressed
	 */
	public void keyPressed() {
		if (key == 119 || key == 87) { // w
			player1.moveUp();
			keyDown[0] = true;
			if (currentFrame == 7) {
				currentFrame = 8;
			} else {
				currentFrame = 7;
			}
		} 
		else if (key == 115 || key == 83) { // s
			player1.moveDown();
			keyDown[2] = true;
			if (currentFrame == 4) {
				currentFrame = 5;
			} else {
				currentFrame = 4;
			}
		}
		else if (key == 97 || key == 65) { // a
			player1.moveLeft();
			keyDown[1] = true;
			if (currentFrame == 11) {
				currentFrame = 10;
			} else {
				currentFrame = 11;
			}
		} 
		else if (key == 100 || key == 68) { // d
			player1.moveRight();
			keyDown[3] = true;
			if (currentFrame == 1) {
				currentFrame = 0;
			} else {
				currentFrame = 1;
			}
		}
		
		
//		if (keyCode == UP) {
//			player2.moveUp();
//		}
//		if (keyCode == DOWN) {
//			player2.moveDown();
//		}
//		if (keyCode == RIGHT) {
//			player2.moveRight();
//		}
//		if (keyCode == LEFT) {
//			player2.moveLeft();
//		}
	}
	
	public void keyReleased() {
		if (key == 119) { // w
			keyDown[0] = false;
		}
		if (key == 97) { // a
			keyDown[1] = false;
		}
		if (key == 115) { // s
			keyDown[2] = false; 
		}
		if (key == 100) { // d
			keyDown[3] = false;
		}
		
		
		if (keyDown[0] && !keyDown[2]) {
			player1.moveUp();
		}
		if (keyDown[2] && !keyDown[0]) {
			player1.moveDown();
		}
		if (keyDown[1] && !keyDown[3]) {
			player1.moveLeft();
		}
		if (keyDown[3] && !keyDown[1]) {
			player1.moveRight();
		}
		
		if (!keyDown[0] && !keyDown[2]) {
			player1.stopMoveUp();
		}
		if (!keyDown[1] && !keyDown[3]) {
			player1.stopMoveLeft();
		}
		
		if (currentFrame == 1) {
			currentFrame = 0;
		} else if(currentFrame == 11) {
			currentFrame = 10;
		} else if (currentFrame == 4 || currentFrame == 5) {
			currentFrame = 12;//change to standing down pic later
		} else if (currentFrame == 7 || currentFrame == 8) {
			currentFrame = 6;
		}
	}
	

}
