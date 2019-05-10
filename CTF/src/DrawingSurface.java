
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import processing.core.*;

public class DrawingSurface extends PApplet {
	Player player1, player2;
	int numFrames = 4; // The number of frames in the animation
	int currentFrame = 0;
	PImage[] images = new PImage[numFrames];

	public DrawingSurface() {
		super();
		player1 = new Player(1, 10, 50);
//		player2 = new Player(2, 100, 50);
	}

//	public void paintComponent(Graphics g)
//	  {
//	    super.paintComponent(g);  // Call JPanel's paintComponent method to paint the background
//
//	    int width = getWidth();
//	    int height = getHeight();
//	    
//	    Graphics2D g2 = (Graphics2D)g;
//	    
//	    player1.draw(g2, this);
//		// TODO Add any custom drawings here
//	  }

	public void settings() {
		size(640, 360);
	}

	public void setup() {
		
		settings();
		frameRate(24);

		images[0] = loadImage("knightStand.png");
		images[0].resize(126, 126);
		images[1] = loadImage("knightWalkRight.png");
		images[1].resize(96, 126);
		images[2] = loadImage("knightAttack1.png");
		images[2].resize(128,126);
		images[3] = loadImage("knightAttack2.png");
		images[3].resize(192,126);

		// If you don't want to load each image separately
		// and you know how many frames you have, you
		// can create the filenames as the program runs.
		// The nf() command does number formatting, which will
		// ensure that the number is (in this case) 4 digits.
		// for (int i = 0; i < numFrames; i++) {
		// String imageName = "PT_anim" + nf(i, 4) + ".gif";
		// images[i] = loadImage(imageName);
		// }
	}

	public void draw() {
		background(255);
		image(images[currentFrame], (float)player1.getX(), (float)player1.getY());
	}

	public void mousePressed() {

	}

	public void keyPressed() {
		if (key == 119 || key == 87) {
			player1.moveUp();
		} else if (key == 115 || key == 83) {
			player1.moveDown();
		} else if (key == 97 || key == 65) {
			player1.moveLeft();
		} else if (key == 100 || key == 68) {
			player1.moveRight();
			if (currentFrame == 1) {
				currentFrame = 0;
			} else {
				currentFrame = 1;
			}
		} else if (key == 32) {
			if (currentFrame == 2) {
				currentFrame = 3;
				
			} else {
				currentFrame = 2;
			}
		}
	}
	
	public void keyReleased() {
		currentFrame = 0;
	}
	

}
