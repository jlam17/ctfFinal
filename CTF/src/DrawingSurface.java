
import processing.core.*;

public class DrawingSurface extends PApplet 
{
	Player player1, player2;
	public DrawingSurface() 
	{
		player1 = new Player(1, 10, 50);
		player2 = new Player(2, 100, 50);
	}
	
	public void setup() 
	{
		
	}
	
	public void draw() 
	{
		
	}
	
	public void mousePressed() {
//		if(mouseButton==) {
//			
//		}
	}
	
	public void keyPressed() {
		if(keyCode==UP) {
			player2.moveUp();
		}
		if(keyCode==DOWN) {
			player2.moveDown();
		}
		if(keyCode==LEFT) {
			player2.moveLeft();
		}
		if(keyCode==RIGHT) {
			player2.moveRight();
		}
		if(key==87 || key==119) {
			player1.moveUp();
		}
	}
}
