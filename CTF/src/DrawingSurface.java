
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import processing.core.*;

/**
 * the surface where drawings are made
 * @author hdesouza538
 *
 */
public class DrawingSurface extends PApplet {
	Player player1, player2;
	int numFrames; // The number of frames in the animation
	int currentFrame = 0;
	PImage[] images;
	boolean[] keyDown;

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
		//this.image(images[9], 0, 0);
		//player1.draw(this, images, currentFrame);
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
