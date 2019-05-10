import java.util.Random;

public class Enemy {
	private double x, y;
	
	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
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
