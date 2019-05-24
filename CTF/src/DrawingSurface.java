
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
	int p1GridX, p1GridY, p2GridX, p2GridY, f1GridX, f1GridY, f2GridX, f2GridY;
	int eSlowCount, e1SlowCount;
	int p1SlowCount, p2SlowCount;
	int numFrames; // The number of frames in the animation
	int currentFrame, currentFrame2, shotFrame1, shotFrame2;
	PImage[] images, enemies;
	PImage wall, flag1, flag2;
	Flag f1, f2;
	Enemy e, e1;
	boolean[] keyDown;
	String maze;
	Grid mazeGrid;
	int enemyImage, enemyImage1;
	int resetTime1, resetTime2;

	boolean done = false, started = false, instructions = false;

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
		e = new Enemy(gridX2X(mazeGrid.numCols - 1), gridY2Y(0), mazeGrid.numCols - 1, 0, Direction.SOUTH);
		e1 = new Enemy(gridX2X(0), gridY2Y(mazeGrid.numRows-1), 0, mazeGrid.numRows-1, Direction.NORTH);
		enemyImage = 0;
		enemyImage1 = 0;
		eSlowCount = 3;
		e1SlowCount = 3;
		
		p1SlowCount = 0;
		p2SlowCount = 0;
		
		resetTime1 = 100;
		resetTime2 = 100;
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
		enemies[0].resize(14 * 2, 24 * 2);
		enemies[1] = loadImage("ghostWalkDown.png");
		enemies[1].resize(15 * 2, 25 * 2);
		enemies[2] = loadImage("ghostWalkLeft.png");
		enemies[2].resize(13 * 2, 24 * 2);
		enemies[3] = loadImage("ghostWalkRight.png");
		enemies[3].resize(13 * 2, 24 * 2);

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

		fill(30,100,50);
		rect(350,300,350,150);
		textSize(100);
		fill(0,0,0);
		this.text("Start", 400, 400);
		
		fill(30,100,50);
		rect(350,500,350,150);
		textSize(50);
		fill(0,0,0);
		this.text("Instructions", 375, 600);
	}

	/**
	 * converts 2-D int coordinate type into normal x coordinates
	 * @param gridX the 2-D int coordinate y value
	 * @return the normal x-coordinate (used in drawing)
	 */
	public double gridX2X(int gridX) {
		return gridStartX + gridX * gridCellWidth + gridWallWidth;
	}

	/**
	 * converts 2-D int coordinate type into normal y coordinates
	 * @param gridY the 2-D int coordinate y value
	 * @return the normal y-coordinate (used in drawing)
	 */
	public double gridY2Y(int gridY) {
		return gridStartY + gridY * gridCellHeight + gridWallWidth;
	}

	/**
	 * draws the background and players
	 */
	public void draw() {
		if (!done && started) {
			background(255);
			this.image(images[9], 0, 0);
			
			textSize(50);
			this.text("Player 1: " + player1.getScore() + "\nPlayer 2: " + player2.getScore(), 300, 700);

			if (player1.hasWon()) {
				f1.generate();
				f2.generate();
				player1.setScore(player1.getScore()+1);
				player1.setX(gridX2X(0));
				p1GridX = 0;
				player1.setY(gridY2Y(0));
				p1GridY = 0;
				player2.setX(gridX2X(mazeGrid.numCols - 1));
				p2GridX = mazeGrid.numCols - 1;
				player2.setY(gridY2Y(mazeGrid.numRows - 1));
				p2GridY = mazeGrid.numRows - 1;
				player2.draw(this, images, 1, false, 0, 0, 0);
				player1.draw(this, images, 1, false, 0, 0, 0);

			}
			if (player2.hasWon()) {
				f1.generate();
				f2.generate();
				player2.setScore(player2.getScore() + 1);
				player1.setX(gridX2X(0));
				p1GridX = 0;
				player1.setY(gridY2Y(0));
				p1GridY = 0;
				player2.setX(gridX2X(mazeGrid.numCols - 1));
				p2GridX = mazeGrid.numCols - 1;
				player2.setY(gridY2Y(mazeGrid.numRows - 1));
				p2GridY = mazeGrid.numRows - 1;
			}

			if (player1.getScore() >= 5 || player2.getScore() >= 5) {
				done = true;
			}

			if (!f1.isPossession()) {
				f1.draw(this, flag1);
				if(!player2.isDead()) {
					resetTime2 = 100;
					player2.draw(this, images, currentFrame2, false, 0, 0, 0);
				} else {
					f1.draw(this, flag1);
					p2GridX = mazeGrid.numCols-1;
					p2GridY = mazeGrid.numRows-1;
					player2.setX(gridX2X(p2GridX));
					player2.setY(gridY2Y(p2GridY));
					player2.draw(this, images, currentFrame2, false, 0,0, 0);
					resetTime2--;
					if(resetTime2<=0) {
						player2.setDead(false);
						p2GridX = mazeGrid.numCols-1;
						player2.setX(gridX2X(p2GridX));
						p2GridY = mazeGrid.numRows-1;
						player2.setY(gridY2Y(p2GridY));
						player2.draw(this, images, 1, false, 0, 0, 0);
					}
				}
			} else {
				f1.draw(this, flag1);
				if(!player2.isDead()) {
					resetTime2 = 50;
					player2.draw(this, images, currentFrame2, true, 250, 0, 0);
					
				}
				else {
					f1.draw(this, flag1);
					resetTime2--;
					if(resetTime2<=0) {
						player2.setDead(false);
						player2.setHasFlag(false);
						f1.setPossession(false);
						p2GridX = mazeGrid.numCols-1;
						player2.setX(gridX2X(p2GridX));
						p2GridY = mazeGrid.numRows-1;
						player2.setY(gridY2Y(p2GridY));
						player2.draw(this, images, 1, false, 0, 0, 0);
					}
				}
			}
			if (!f2.isPossession()) {
				f2.draw(this, flag2);
				if(!player1.isDead()) {
					resetTime1 = 100;
					player1.draw(this, images, currentFrame, false, 0, 0, 0);
				}
				else {
					resetTime1--;
					p1GridX = 0;
					p1GridY = 0;
					player1.setX(gridX2X(p1GridX));
					player1.setY(gridY2Y(p1GridY));
					player1.draw(this, images, currentFrame, false, 0,0, 0);
					if(resetTime1<=0) {
						player1.setDead(false);
						p1GridX = 0;
						player1.setX(gridX2X(p1GridX));
						p1GridY = 0;
						player1.setY(gridY2Y(p1GridY));
						player1.draw(this, images, 1, false, 0, 0, 0);
					}
				}
			} else {
				if(!player1.isDead()) {
					resetTime1 = 50;
					player1.draw(this, images, currentFrame, true, 0, 0, 250);
					
				}
				else {
					f2.draw(this, flag2);
					resetTime1--;
					if(resetTime1<=0) {
						player1.setDead(false);
						player1.setHasFlag(false);
						f2.setPossession(false);
						p1GridX = 0;
						player1.setX(gridX2X(p1GridX));
						p1GridY = 0;
						player1.setY(gridY2Y(p1GridY));
						player1.draw(this, images, 1, false, 0, 0, 0);
					}
				}
			}
			
			if (eSlowCount == 0) {
				if (e.getDirection() == Direction.SOUTH) {
					if (e.getGridY() < mazeGrid.numRows - 1) {
						e.move(e.getGridX(), e.getGridY() + 1);
						enemyImage = 1;
					} else {
						e.setDirection(Direction.WEST);
					}
				} else if (e.getDirection() == Direction.WEST) {
					if (e.getGridX() > 0) {
						e.move(e.getGridX() - 1, e.getGridY());
						enemyImage = 2;
					} else {
						e.setDirection(Direction.NORTH);
					}
				} else if (e.getDirection() == Direction.NORTH) {
					if (e.getGridY() > 0) {
						enemyImage = 0;
						e.move(e.getGridX(), e.getGridY() - 1);
					} else {
						e.setDirection(Direction.EAST);
					}
				} else if (e.getDirection() == Direction.EAST) {
					if (e.getGridX() <= mazeGrid.numRows) {
						enemyImage = 3;
						e.move(e.getGridX() + 1, e.getGridY());
					} else {
						e.setDirection(Direction.SOUTH);
					}
				}
				eSlowCount = 3;
			} else {
				eSlowCount--;
			}
			e.draw(this, enemies[enemyImage], gridX2X(e.getGridX()), gridY2Y(e.getGridY()));
			
			if (e1SlowCount == 0) {
				if (e1.getDirection() == Direction.SOUTH) {
					if (e1.getGridY() < mazeGrid.numRows-1) {
						e1.move(e1.getGridX(), e1.getGridY() + 1);
						enemyImage1 = 1;
					} else {
						e1.setDirection(Direction.WEST);
					}
				} else if (e1.getDirection() == Direction.WEST) {
					if (e1.getGridX() > 0) {
						e1.move(e1.getGridX() - 1, e1.getGridY());
						enemyImage1 = 2;
					} else {
						e1.setDirection(Direction.NORTH);
					}
				} else if (e1.getDirection() == Direction.NORTH) {
					if (e1.getGridY() > 0) {
						enemyImage1 = 0;
						e1.move(e1.getGridX(), e1.getGridY() - 1);
					} else {
						e1.setDirection(Direction.EAST);
					}
				} else if (e1.getDirection() == Direction.EAST) {
					if (e1.getGridX() <= mazeGrid.numRows) {
						enemyImage1 = 3;
						e1.move(e1.getGridX() + 1, e1.getGridY());
					} else {
						e1.setDirection(Direction.SOUTH);
					}
				}
				e1SlowCount = 3;
			} else {
				e1SlowCount--;
			}
			e1.draw(this, enemies[enemyImage1], gridX2X(e1.getGridX()), gridY2Y(e1.getGridY()));
			
			
			if (e1.getGridX() == p1GridX && e1.getGridY() == p1GridY) {
				player1.setDead(true);
			} else if (e1.getGridX() == p2GridX && e1.getGridY() == p2GridY) {
				player2.setDead(true);
			}
			
			drawMazeGrid();
		} else {
			// print a player has won...
			if (player1.getScore() >= 5 || player2.getScore() >= 5) {
				background(0);
				if (player1.getScore() >= 5) {
					this.text("Player 1 Has WON", 100, 100);
				} else {
					this.text("Player 2 Has WON", 100, 100);
				}
			}
		}

		if (player1.getShotGridX() == p2GridX && player1.getShotGridY() == p2GridY) {
			p2SlowCount = 20;
			player1.setIsShooting(false);
		} 
		if (player2.getShotGridX() == p1GridX && player2.getShotGridY() == p1GridY) {
			p1SlowCount = 20;
			player2.setIsShooting(false);
		}
		if (p1SlowCount > 0) {
			p1SlowCount--;
		}
		if (p2SlowCount > 0) {
			p2SlowCount--;
		}
	}

	/**
	 * checks for what to do if a mouse button is pressed
	 */
	public void mousePressed() {
		if (started && mouseButton == LEFT) {
			Direction d = Direction.NORTH;
			if (currentFrame2 == 12 || currentFrame2 == 4 || currentFrame2 == 5) {
				d = Direction.SOUTH;
			} else if (currentFrame2 == 0 || currentFrame2 == 1) {
				d = Direction.EAST;
			} else if (currentFrame2 == 10 || currentFrame2 == 11) {
				d = Direction.WEST;
			}
			 player2.shoot(this, images, shotFrame2, mazeGrid, d, gridX2X(p2GridX), gridY2Y(p2GridY), p2GridX, p2GridY);
		}
		if (!started && mouseButton == LEFT) {
			if(!instructions) {
				if(mouseX<=700 && mouseX>=350 && mouseY<=450 && mouseY>=300) {
					started = true;
					fill(255);
				}
				else if(mouseX<=700 && mouseX>=350 && mouseY<=650 && mouseY>=500) {
					this.background(0);
					fill(255);
					this.text("2 Player \nCapture the Flag", 50, 100); // instructions here
					textSize(30);
					this.text("Player 2: Arrow Key Controls\n Left Click to Shoot", 25, 550);
					this.text("Player 1: WASD Controls\n Space to Shoot",25, 400);
					this.text("Be Careful! Both players can die to the ghost. Your opponent's \nfireballs will stun you. If you die, you will respawn and be able to \nmove a couple seconds later.", 25, 700);
					textSize(50);
					rect(10, 250, 400, 70);
					fill(0);
					this.text("Start Game!", 50, 300);
					instructions = true;
					fill(255);
				}
			}
			else {
				if(mouseX<=410 && mouseX>=10 && mouseY<=320 && mouseY>=250) {
					started = true;
					fill(255);
				}
			}
			
		}
	}

	/**
	 * checks for what to do if a certain key is pressed
	 */
	public void keyPressed() {
		if (p1SlowCount <= 0) {
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
			if ((p1GridY + 1 < mazeGrid.numRows)
					&& mazeGrid.hasEdge(mazeGrid.vertex(p1GridX, p1GridY), Direction.SOUTH)) {
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
			if ((p1GridX + 1 < mazeGrid.numCols)
					&& mazeGrid.hasEdge(mazeGrid.vertex(p1GridX, p1GridY), Direction.EAST)) {
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
			currentFrame2 = 12;// change to standing down pic later
		} else if (currentFrame2 == 7 || currentFrame2 == 8) {
			currentFrame2 = 6;
		}

	}
	
	/**
	 * draws the maze with precise proportions and no extra jutting out walls
	 */
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
