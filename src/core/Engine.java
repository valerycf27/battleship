package core;

import java.util.Random;

import players.AI;
import players.Player;
import players.User;
import utils.Util;

public class Engine {

	public static void main(String[] args) {
		Util.printBoards(null,null);
		Player user = new User();
		user.createOceanGrid();
		Util.printBoards(user.getOceanGrid(),user.getTargetGrid());
		
		Player ai = new AI();
		ai.createOceanGrid();
		Util.printBoards(ai.getOceanGrid(),ai.getTargetGrid());
		
		boolean victoryUser = false;
		boolean victoryAI = false;
		
		Random rd = new Random();  //ONCE WE HAVE A HIT, WE CHECK IN THE AREA FOR MORE HITS
		boolean order=rd.nextBoolean();
		
		while(!victoryUser && !victoryAI) {
	
			if (order) {
				user.attack(ai.getOceanGrid());
				ai.attack(user.getOceanGrid());				
			}
			else {
				ai.attack(user.getOceanGrid());				
				user.attack(ai.getOceanGrid());
			}
			
			victoryUser=Util.checkwinner(ai.getOceanGrid(),user.getTargetGrid());
			victoryAI=Util.checkwinner(user.getOceanGrid(),ai.getTargetGrid());
			Util.printBoards(user.getOceanGrid(),user.getTargetGrid());
			Util.printBoards(ai.getOceanGrid(),ai.getTargetGrid());
		}
		
		if (victoryUser) {
			System.out.println("Congratulations on your victory, player.");
		}
		else {
			System.out.println("The machine has won the game");
		}
		System.out.println("GAME OVER");
		
	}

}
