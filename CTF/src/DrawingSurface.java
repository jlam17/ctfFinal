
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
	/**
	 * chooses which algorithm to draw
	 * 
	 * @author Wikipedia
	 *
	 */
	enum Algorithm {
	RANDOM_DFS_RECURSIVE, RANDOM_DFS_NONRECURSIVE, RANDOM_BFS, PRIM, RECURSIVE_DIVISION, ALDOUS_BRODER, WILSON,
	SIDEWINDER, BINARY_TREE
	}

	/**
	 * generates a grid with the parameters
	 * 
	 * @param numCols   number of columns
	 * @param numRows   number of rows
	 * @param startCol  start column
	 * @param startRow  start row
	 * @param algorithm algorithm type
	 * @return a grid that fits the parameters
	 */
	public static Grid maze(int numCols, int numRows, int startCol, int startRow, Algorithm algorithm) {
		Grid grid = new Grid(numCols, numRows);
		int startVertex = grid.vertex(startCol, startRow);
		BitSet visited = new BitSet(numCols * numRows);
		randomDFSRecursive(grid, startVertex, visited);
		return grid;
	}

	/**
	 * generates the maze
	 * 
	 * @param grid    the grid passed in
	 * @param v       integer that helps with direction
	 * @param visited a bit set
	 */
	static void randomDFSRecursive(Grid grid, int v, BitSet visited) {
		visited.set(v);
		for (Direction dir = unvisitedDir(grid, v, visited); dir != null; dir = unvisitedDir(grid, v, visited)) {
			grid.addEdge(v, dir);
			randomDFSRecursive(grid, grid.neighbor(v, dir), visited);
		}
	}

	/**
	 * helps generates the maze
	 * 
	 * @param grid    the grid passed in
	 * @param v       integer that helps with direction
	 * @param visited a bit set
	 * @return a direction
	 */
	static Direction unvisitedDir(Grid grid, int v, BitSet visited) {
		List<Direction> unvisitedDirections = unvisitedDirections(grid, v, visited);
		return unvisitedDirections.isEmpty() ? null : unvisitedDirections.get(0);
	}

	/**
	 * helps generates the maze
	 * 
	 * @param grid    the grid passed in
	 * @param v       integer that helps with direction
	 * @param visited a bit set
	 * @return a list of directions
	 */
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
	int p1GridX, p1GridY, p2GridX, p2GridY, f1GridX, f1GridY, f2GridX, f2GridY, eGridX, eGridY;
	int numFrames; // The number of frames in the animation
	int currentFrame, currentFrame2, shotFrame1, shotFrame2;
	PImage[] images, enemies;
	PImage wall, flag1, flag2;
	Flag f1, f2;
	Enemy e;
	boolean[] keyDown;
	String maze;
	Grid mazeGrid;

	boolean done = false, started = false;

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
		numFrames = 19;
		images = new PImage[numFrames];
		enemies = new PImage[4];
		keyDown = new boolean[4];
		currentFrame = 0;
		currentFrame2 = 0;
		shotFrame1 = 15;
		shotFrame2 = 15;

		mazeGrid = maze(11, 9, 0, 0, Algorithm.values()[0]);
		e = new Enemy(gridX2X(0), gridY2Y(mazeGrid.numRows - 1));
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
		frameRate(60);

		images[9] = loadImage("background.png");
		images[9].resize(1000, 1000);
		this.image(images[9], 0, 0);

		// change the first two values in the maze call to change width/height
		
		maze = mazeGrid.toString();

		//System.out.println(maze);
		
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
		images[13] = loadImage("redFlag.png");
		images[13].resize(40, 40);
		images[14] = loadImage("blueFlag.png");
		images[14].resize(40, 40);
		images[15] = loadImage("fireball1.png");
		images[15].resize(40, 40);
		images[16] = loadImage("fireball2.png");
		images[16].resize(40, 40);
		images[17] = loadImage("fireball3.png");
		images[17].resize(40, 40);
		images[18] = loadImage("fireball4.png");
		images[18].resize(40, 40);

		enemies[0] = loadImage("ghostWalkUp.png");
		enemies[1] = loadImage("ghostWalkDown.png");
		enemies[2] = loadImage("ghostWalkLeft.png");
		enemies[3] = loadImage("ghostWalkLeft.png");
		
		flag1 = images[13];
		flag2 = images[14];
		p1GridX = 0;
		p1GridY = 0;
		p2GridX = mazeGrid.numCols - 1;
		p2GridY = mazeGrid.numRows - 1;

		f1 = new Flag(flag1, player1, gridX2X(p1GridX), gridY2Y(p1GridY));
		f2 = new Flag(flag2, player2, gridX2X(p2GridX), gridY2Y(p2GridY));

		player1 = new Player(1, gridX2X(p1GridX), gridY2Y(p1GridY));
		player2 = new Player(2, gridX2X(p2GridX), gridY2Y(p2GridY));

		f1GridX = p1GridX;
		f1GridY = p1GridY;

		f2GridX = p2GridX;
		f2GridY = p2GridY;

		textSize(100);
		this.text("Click Anywhere \nto Start\n\nRight Click for \nInstructions", 200, 300);
	}

	public double gridX2X(int gridX) {
		return gridStartX + gridX * gridCellWidth + gridWallWidth;
	}

	public double gridY2Y(int gridY) {
//		System.out.println("gridY: " + gridY + ":gridCellHeight:" + gridCellHeight);
		return gridStartY + gridY * gridCellHeight + gridWallWidth;
	}

	/**
	 * draws the background and players
	 */
	public void draw() {
		if (!done && started) {
			background(255);
			this.image(images[9], 0, 0);
			
			e.draw(this, enemies[0]);
			if(mazeGrid.hasEdge(mazeGrid.vertex((int)gridX2X((int) e.getX()), (int)gridY2Y((int) e.getY())), Direction.NORTH) && e.getDirection() == 1) {
				//e.draw(this, enemies[1]);
				int toSet = e.getDirection()+1;
				if(toSet>4) {
					toSet = 1;
				}
				e.setDirection(toSet);
			}
			else if(mazeGrid.hasEdge(mazeGrid.vertex((int)gridX2X((int) e.getX()), (int)gridY2Y((int) e.getY())), Direction.EAST) && e.getDirection() == 2) {
				//e.draw(this, enemies[0]);
				int toSet = e.getDirection()+1;
				if(toSet>4) {
					toSet = 1;
				}
				e.setDirection(toSet);

			}
			else if(mazeGrid.hasEdge(mazeGrid.vertex((int)gridX2X((int) e.getX()), (int)gridY2Y((int) e.getY())), Direction.SOUTH) && e.getDirection() == 3) {
				//e.draw(this, enemies[0]);
				int toSet = e.getDirection()+1;
				if(toSet>4) {
					toSet = 1;
				}
				e.setDirection(toSet);

			}
			else if(mazeGrid.hasEdge(mazeGrid.vertex((int)gridX2X((int) e.getX()), (int)gridY2Y((int)e.getY())), Direction.WEST) && e.getDirection() == 4) {
				//e.draw(this, enemies[0]);
				int toSet = e.getDirection()+1;
				if(toSet>4) {
					toSet = 1;
				}
				e.setDirection(toSet);

			}
			else {
				if(e.getDirection() == 1) {
					e.setY(e.getY()-2);
				}
				else if(e.getDirection() == 2) {
					e.setX(e.getX()+2);
				}
				else if(e.getDirection() == 3) {
					e.setY(e.getY()+2);
				}
				else if(e.getDirection() == 4) {
					e.setX(e.getX()-2);
				}
			}
			textSize(50);
			this.text("Player 1: "+player1.getScore()+"\nPlayer 2: "+player2.getScore(), 300, 700);
			
			if(player1.hasWon()) {
				f1.generate();
				f2.generate();
				player1.setScore(player1.getScore()+1);
				player1.setX(gridX2X(0));
				p1GridX = 0;
				player1.setY(gridY2Y(0));
				p1GridY = 0;
				player2.setX(gridX2X(mazeGrid.numCols - 1));
				p2GridX = mazeGrid.numCols-1;
				player2.setY(gridY2Y(mazeGrid.numRows - 1));
				p2GridY = mazeGrid.numRows-1;
				player2.draw(this, images, 1, false, 0, 0, 0);
				player1.draw(this, images, 1, false, 0, 0, 0);

				// f1.draw(this, flag1);
				// f2.draw(this, flag2);
			}
			if(player2.hasWon()) {
				f1.generate();
				f2.generate();
				player2.setScore(player2.getScore()+1);
				player1.setX(gridX2X(0));
				p1GridX = 0;
				player1.setY(gridY2Y(0));
				p1GridY = 0;
				player2.setX(gridX2X(mazeGrid.numCols - 1));
				p2GridX = mazeGrid.numCols-1;
				player2.setY(gridY2Y(mazeGrid.numRows - 1));
				p2GridY = mazeGrid.numRows-1;
			}
			
			if(player1.getScore()>=5 || player2.getScore()>=5) {
				done = true;
			}

			if (!f1.isPossession()) {
				f1.draw(this, flag1);
				player2.draw(this, images, currentFrame2, false, 0, 0, 0);
			} else {
				player2.draw(this, images, currentFrame2, true, 250, 0, 0);
			}
			if (!f2.isPossession()) {
				f2.draw(this, flag2);
				player1.draw(this, images, currentFrame, false, 0, 0, 0);
			} else {
				player1.draw(this, images, currentFrame, true, 0, 0, 250);
			}
			drawMazeGrid();
		} else {
			//print a player has won...
			if(player1.getScore()>=5 || player2.getScore()>=5) {
				background(0);
				if(player1.getScore()>=5) {
					this.text("Player 1 Has WON", 100, 100);
				}
				else {
					this.text("Player 2 Has WON", 100, 100);
				}
			}
		}

		// f1.draw(this, flag1);
		// f2.draw(this, flag2);
	}

	/**
	 * checks for what to do if a mouse button is pressed
	 */
	public void mousePressed() {
		if (started && mouseButton == LEFT) {
			Direction d = Direction.NORTH;
			if (currentFrame == 12 || currentFrame == 4 || currentFrame == 5) {
				d = Direction.SOUTH;
			} else if (currentFrame == 0 || currentFrame == 1) {
				d = Direction.EAST;
			} else if (currentFrame == 10 || currentFrame == 11) {
				d = Direction.WEST;
			}
			//player2.shoot(mazeGrid, d, gridX2X(p2GridX), gridY2Y(p2GridY));
		}
		if (!started && mouseButton == LEFT) {
			started = true;
		}

		if (mouseButton == RIGHT) {
			if (!started) {
				this.background(0);
				this.text("2 Player Capture the Flag\n", 100, 200); // instructions here
			}
		}
	}

	/**
	 * checks for what to do if a certain key is pressed
	 */
	public void keyPressed() {
		System.out.println("Player1: " + player1.getX() + ", " + p1GridX);
		if (key == 119 || key == 87) { // w
			if ((p1GridY - 1 >= 0) && mazeGrid.hasEdge(mazeGrid.vertex(p1GridX, p1GridY), Direction.NORTH)) {
//				player1.setVelY(-60);
				p1GridY--;
			}
			keyDown[0] = true;
			if (currentFrame == 7) {
				currentFrame = 8;
			} else {
				currentFrame = 7;
			}
		} else if (key == 115 || key == 83) { // s
			if ((p1GridY + 1 < mazeGrid.numRows)
					&& mazeGrid.hasEdge(mazeGrid.vertex(p1GridX, p1GridY), Direction.SOUTH)) {
//				player1.setVelY(60);
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
//				player1.setVelX(-60);
				p1GridX--;
			}
			keyDown[1] = true;
			if (currentFrame == 11) {
				currentFrame = 10;
			} else {
				currentFrame = 11;
			}
		} else if (key == 100 || key == 68) { // d
			if ((p1GridX + 1 < mazeGrid.numCols)
					&& mazeGrid.hasEdge(mazeGrid.vertex(p1GridX, p1GridY), Direction.EAST)) {
//				player1.setVelX(60);
				p1GridX++;
			}
			keyDown[3] = true;
			if (currentFrame == 1) {
				currentFrame = 0;
			} else {
				currentFrame = 1;
			}
		} else if (key == 32) {
			Direction d = Direction.NORTH;
			if (currentFrame == 12 || currentFrame == 4 || currentFrame == 5) {
				d = Direction.SOUTH;
			} else if (currentFrame == 0 || currentFrame == 1) {
				d = Direction.EAST;
			} else if (currentFrame == 10 || currentFrame == 11) {
				d = Direction.WEST;
			}
				player1.shoot(this, images, shotFrame1, mazeGrid, d, gridX2X(p1GridX), gridY2Y(p1GridY), p1GridX, p1GridY);

		}
		if (p1GridX == f2GridX && p1GridY == f2GridY) {
			f2.pickUp(player1);
		}
		player1.move(gridX2X(p1GridX), gridY2Y(p1GridY));

		// Player 2
		if (keyCode == UP) {
			if ((p2GridY - 1 >= 0) && mazeGrid.hasEdge(mazeGrid.vertex(p2GridX, p2GridY), Direction.NORTH)) {
				p2GridY--;
			}
			keyDown[0] = true;
			if (currentFrame2 == 7) {
				currentFrame2 = 8;
			} else {
				currentFrame2 = 7;
			}
		} else if (keyCode == DOWN) { // s
			if ((p2GridY + 1 < mazeGrid.numRows)
					&& mazeGrid.hasEdge(mazeGrid.vertex(p2GridX, p2GridY), Direction.SOUTH)) {
				p2GridY++;
			}
			keyDown[2] = true;
			if (currentFrame2 == 4) {
				currentFrame2 = 5;
			} else {
				currentFrame2 = 4;
			}
		} else if (keyCode == LEFT) { // a
			if ((p2GridX - 1 >= 0) && mazeGrid.hasEdge(mazeGrid.vertex(p2GridX, p2GridY), Direction.WEST)) {
				p2GridX--;
			}
			keyDown[1] = true;
			if (currentFrame2 == 11) {
				currentFrame2 = 10;
			} else {
				currentFrame2 = 11;
			}
		} else if (keyCode == RIGHT) { // d
			if ((p2GridX + 1 < mazeGrid.numCols)
					&& mazeGrid.hasEdge(mazeGrid.vertex(p2GridX, p2GridY), Direction.EAST)) {
				p2GridX++;
			}
			keyDown[3] = true;
			if (currentFrame2 == 1) {
				currentFrame2 = 0;
			} else {
				currentFrame2 = 1;
			}
		}
		if (p2GridX == f1GridX && p2GridY == f1GridY) {
			f1.pickUp(player2);
		}
		player2.move(gridX2X(p2GridX), gridY2Y(p2GridY));

	}

	/**
	 * checks what happens if a key is released
	 */

	public void keyReleased() {
		if (currentFrame == 1) {
			currentFrame = 0;
		} else if (currentFrame == 11) {
			currentFrame = 10;
		} else if (currentFrame == 4 || currentFrame == 5) {
			currentFrame = 12;// change to standing down pic later
		} else if (currentFrame == 7 || currentFrame == 8) {
			currentFrame = 6;
		}
		if (currentFrame2 == 1) {
			currentFrame2 = 0;
		} else if (currentFrame2 == 11) {
			currentFrame2 = 10;
		} else if (currentFrame2 == 4 || currentFrame2 == 5) {
			currentFrame = 12;// change to standing down pic later
		} else if (currentFrame2 == 7 || currentFrame2 == 8) {
			currentFrame2 = 6;
		}
//		if (key == 119 || key == 87) { // w
//			player1.setVelY(0);
//		} else if (key == 115 || key == 83) { // s
//			player1.setVelY(0);
//		} else if (key == 97 || key == 65) { // a
//			player1.setVelX(0);
//		} else if (key == 100 || key == 68) { // d
//			player1.setVelX(0);
//		}

	}

	/**
	 * generates the maze in a handy function
	 */
	public void drawMaze() {
		int y = 5;
		int x = 20;
		for (int i = 0; i < 874 && i < maze.length(); i++) {
			x = 20;
			y += 36;
			while (!maze.substring(i, i + 1).equals("\n")) {
				if (!maze.substring(i, i + 1).equals(" ")) {
					if (maze.substring(i, i + 1).equals("-")) {
						if (i > 1 && maze.substring(i - 1, i).equals(" ")) {
							this.image(wall, x, y, width / 72, height / 144);
						} else if (i > 1 && maze.substring(i + 1, i + 2).equals(" ")
								&& maze.substring(i + 2, i + 3).equals("|")) {
							this.image(wall, x, y, width / 72, height / 144);
						}
//						else if(i>1 && maze.substring(i+1, i+1).equals(" ")) {
//							this.image(wall, x, y, width/72, height/144);
//						}
						else {
							this.image(wall, x, y, width / 18, height / 36);
						}
					} else {
						if (i > 1 && maze.substring(i - 1, i).equals("-")) {
							this.image(wall, x, y, width / 36, height / 36);
						} else if (i > 1 && maze.substring(i - 1, i).equals(" ") /*
																					 * && !maze.substring(i+1,
																					 * i+2).equals("\n")
																					 */
								&& maze.substring(i + 1, i + 2).equals(" ")) {
							this.image(wall, x, y, width / 72, height / 40);
						} else if (i > 1 && maze.substring(i - 1, i).equals(" ")
								&& !maze.substring(i + 1, i + 2).equals("\n")
								&& !maze.substring(i + 1, i + 2).equals(" ")) {
							this.image(wall, x, y, width / 36, height / 36);
						} else {
							this.image(wall, x, y, width / 36, height / 18);
						}
					}
				}
			}
		}
	}

	public void drawMazeGrid() {

		for (int r = 0; r < mazeGrid.numRows; r++) {
			for (int c = 0; c < mazeGrid.numCols; c++) {
				if (!mazeGrid.hasEdge(mazeGrid.vertex(c, r), Direction.NORTH)) {
					this.image(wall, gridStartX + c * gridCellWidth, gridStartY + r * gridCellHeight, gridCellWidth,
							gridWallWidth);
				}
				if (!mazeGrid.hasEdge(mazeGrid.vertex(c, r), Direction.WEST)) {
					this.image(wall, gridStartX + c * gridCellWidth, gridStartY + r * gridCellHeight, gridWallWidth,
							gridCellHeight);
				}
			}
			this.image(wall, gridStartX + mazeGrid.numCols * gridCellWidth, gridStartY + r * gridCellHeight,
					gridWallWidth, gridCellHeight);

		}
		for (int i = 0; i < mazeGrid.numCols; i++) {
			if (i == mazeGrid.numCols - 1) {
				this.image(wall, gridStartX + i * gridCellWidth, gridStartY + mazeGrid.numRows * gridCellHeight,
						gridCellWidth + gridWallWidth, gridWallWidth);
			} else {
				this.image(wall, gridStartX + i * gridCellWidth, gridStartY + mazeGrid.numRows * gridCellHeight,
						gridCellWidth, gridWallWidth);
			}
		}

	}

}
