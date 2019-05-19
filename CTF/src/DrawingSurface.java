
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;

//import main.Algorithm;
import processing.core.*;

/**
 * the surface where drawings are made
 * 
 * @author hdesouza538
 *
 */
public class DrawingSurface extends PApplet {
	enum Algorithm {
		RANDOM_DFS_RECURSIVE, RANDOM_DFS_NONRECURSIVE, RANDOM_BFS, PRIM, RECURSIVE_DIVISION, ALDOUS_BRODER, WILSON,
		SIDEWINDER, BINARY_TREE
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
		for (Direction dir = unvisitedDir(grid, v, visited); dir != null; dir = unvisitedDir(grid, v, visited)) {
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
	int p1GridX, p1GridY, p2GridX, p2GridY;
	int numFrames; // The number of frames in the animation
	int currentFrame = 0;
	PImage[] images;
	PImage wall, flag1, flag2;
	Flag f1, f2;
	boolean[] keyDown;
	String maze;
	Grid mazeGrid;
	
	/*
	 * Private variables for defining Maze Grid
	 */
	private int gridStartX = 20;
	private int gridStartY = 5;
	private int gridWallWidth = 20;
	private int gridCellWidth = 60;
	private int gridCellHeight = 60;

	/**
	 * constructs the DrawingSurface, initializes player variables
	 */
	public DrawingSurface() {
		super();
		numFrames = 13;
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

		// change the first two values in the maze call to change width/height
		mazeGrid = maze(11, 9, 0, 0, Algorithm.values()[0]);
		maze = mazeGrid.toString();

		System.out.println(maze);

		// this.text("", 100, 100);
		wall = loadImage("wall.png");

		// Loading and resizing the images
		images[0] = loadImage("knightStand.png");
		images[0].resize(126 / 4, 126 / 4);
		images[1] = loadImage("knightWalkRight.png");
		images[1].resize(96 / 4, 126 / 4);
		images[2] = loadImage("knightAttack1.png");
		images[2].resize(128 / 4, 126 / 4);
		images[3] = loadImage("knightAttack2.png");
		images[3].resize(192 / 4, 126 / 4);
		images[4] = loadImage("knightWalkDown1.png");
		images[4].resize(96 / 4, 128 / 4);
		images[5] = loadImage("knightWalkDown2.png");
		images[5].resize(128 / 4, 128 / 4);
		images[6] = loadImage("knightStandUp.png");
		images[6].resize(126 / 4, 127 / 4);
		images[7] = loadImage("knightWalkUp1.png");
		images[7].resize(96 / 4, 126 / 4);
		images[8] = loadImage("knightWalkUp2.png");
		images[8].resize(128 / 4, 126 / 4);
		images[9] = loadImage("background.png");
		images[9].resize(1000, 1000);
		images[10] = loadImage("knightStandLeft.png");
		images[10].resize(126 / 4, 126 / 4);
		images[11] = loadImage("knightWalkLeft.png");
		images[11].resize(96 / 4, 126 / 4);
		images[12] = loadImage("knightStandDown.png");
		images[12].resize(126 / 4, 128 / 4);
		
		p1GridX = 0;
		p1GridY = 0;
		p2GridX = mazeGrid.numCols-1;
		p2GridY = mazeGrid.numRows-1;
		
		player1 = new Player(1, gridX2X(p1GridX), gridY2Y(p1GridY));
		player2 = new Player(2, gridX2X(p2GridX), gridY2Y(p2GridY));

	}
	
	public double gridX2X(int gridX) {
		return gridStartX + gridX * gridCellWidth+gridWallWidth;
	}
	
	public double gridY2Y(int gridY) {
		System.out.println("gridY: "+gridY+":gridCellHeight:"+gridCellHeight);
		return gridStartY + gridY * gridCellHeight+gridWallWidth;
	}

	/**
	 * draws the background and players
	 */
	public void draw() {
		background(255);

		this.image(images[9], 0, 0);
		player1.draw(this, images, currentFrame);
		player2.draw(this, images, currentFrame);
		drawMazeGrid();

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
			if ((p1GridY - 1 >= 0) && mazeGrid.hasEdge(mazeGrid.vertex(p1GridX, p1GridY), Direction.NORTH)) {
				p1GridY--;
			}
			keyDown[0] = true;
			if (currentFrame == 7) {
				currentFrame = 8;
			} else {
				currentFrame = 7;
			}
		} else if (key == 115 || key == 83) { // s
			if ((p1GridY + 1 < mazeGrid.numRows) && mazeGrid.hasEdge(mazeGrid.vertex(p1GridX, p1GridY), Direction.SOUTH)) {
				p1GridY++;
			}
			keyDown[2] = true;
			if (currentFrame == 4) {
				currentFrame = 5;
			} else {
				currentFrame = 4;
			}
		} else if (key == 97 || key == 65) { // a
			if ((p1GridX - 1 >= 0) && mazeGrid.hasEdge(mazeGrid.vertex(p1GridX, p1GridY), Direction.WEST)) {
				p1GridX--;
			}
			keyDown[1] = true;
			if (currentFrame == 11) {
				currentFrame = 10;
			} else {
				currentFrame = 11;
			}
		} else if (key == 100 || key == 68) { // d
			if ((p1GridX + 1 < mazeGrid.numCols) && mazeGrid.hasEdge(mazeGrid.vertex(p1GridX, p1GridY), Direction.EAST)) {
				p1GridX++;
			}
			keyDown[3] = true;
			if (currentFrame == 1) {
				currentFrame = 0;
			} else {
				currentFrame = 1;
			}
		}
			player1.move(gridX2X(p1GridX),gridY2Y(p1GridY));

			//Player 2
		if (keyCode == UP) {
			if ((p2GridY - 1 >= 0) && mazeGrid.hasEdge(mazeGrid.vertex(p2GridX, p2GridY), Direction.NORTH)) {
				p2GridY--;
			}
			keyDown[0] = true;
			if (currentFrame == 7) {
				currentFrame = 8;
			} else {
				currentFrame = 7;
			}
		}
		if (keyCode == DOWN) {
			if ((p2GridY + 1 < mazeGrid.numRows) && mazeGrid.hasEdge(mazeGrid.vertex(p2GridX, p2GridY), Direction.SOUTH)) {
				p2GridY++;
			}
			keyDown[2] = true;
			if (currentFrame == 4) {
				currentFrame = 5;
			} else {
				currentFrame = 4;
			}
		}
		if (keyCode == RIGHT) {
			if ((p2GridX + 1 < mazeGrid.numCols) && mazeGrid.hasEdge(mazeGrid.vertex(p2GridX, p2GridY), Direction.EAST)) {
			p2GridX++;
		}
		keyDown[3] = true;
		if (currentFrame == 1) {
			currentFrame = 0;
		} else {
			currentFrame = 1;
		}
		}
		if (keyCode == LEFT) {
			if ((p2GridX - 1 >= 0) && mazeGrid.hasEdge(mazeGrid.vertex(p2GridX, p2GridY), Direction.WEST)) {
			p2GridX--;
		}
		keyDown[1] = true;
		if (currentFrame == 11) {
			currentFrame = 10;
		} else {
			currentFrame = 11;
		}
		}
		player2.move(gridX2X(p2GridX),gridY2Y(p2GridY));
	}

//	public void keyReleased() {
//		if (key == 119) { // w
//			keyDown[0] = false;
//		}
//		if (key == 97) { // a
//			keyDown[1] = false;
//		}
//		if (key == 115) { // s
//			keyDown[2] = false;
//		}
//		if (key == 100) { // d
//			keyDown[3] = false;
//		}
//
//		if (keyDown[0] && !keyDown[2]) {
//			player1.moveUp();
//		}
//		if (keyDown[2] && !keyDown[0]) {
//			player1.moveDown();
//		}
//		if (keyDown[1] && !keyDown[3]) {
//			player1.moveLeft();
//		}
//		if (keyDown[3] && !keyDown[1]) {
//			player1.moveRight();
//		}
//
//		if (!keyDown[0] && !keyDown[2]) {
//			player1.stopMoveUp();
//		}
//		if (!keyDown[1] && !keyDown[3]) {
//			player1.stopMoveLeft();
//		}
//
//		if (currentFrame == 1) {
//			currentFrame = 0;
//		} else if (currentFrame == 11) {
//			currentFrame = 10;
//		} else if (currentFrame == 4 || currentFrame == 5) {
//			currentFrame = 12;// change to standing down pic later
//		} else if (currentFrame == 7 || currentFrame == 8) {
//			currentFrame = 6;
//		}
//	}

	public void drawMazeGrid() {

		for (int r = 0; r < mazeGrid.numRows; r++) {
			for (int c = 0; c < mazeGrid.numCols; c++) {
				if (!mazeGrid.hasEdge(mazeGrid.vertex(c, r), Direction.NORTH)) {
					this.image(wall, gridStartX + c * gridCellWidth, gridStartY + r * gridCellHeight, gridCellWidth, gridWallWidth);
				}
				if (!mazeGrid.hasEdge(mazeGrid.vertex(c, r), Direction.WEST)) {
					this.image(wall, gridStartX + c * gridCellWidth, gridStartY + r * gridCellHeight, gridWallWidth, gridCellHeight);
				}
			}
			this.image(wall, gridStartX + mazeGrid.numCols * gridCellWidth, gridStartY + r * gridCellHeight, gridWallWidth, gridCellHeight);

		}
		for (int i = 0; i < mazeGrid.numCols; i++) {
			if (i == mazeGrid.numCols - 1) {
				this.image(wall, gridStartX + i * gridCellWidth, gridStartY + mazeGrid.numRows * gridCellHeight, gridCellWidth + gridWallWidth, gridWallWidth);
			} else {
				this.image(wall, gridStartX + i * gridCellWidth, gridStartY + mazeGrid.numRows * gridCellHeight, gridCellWidth, gridWallWidth);
			}
		}

	}


}
