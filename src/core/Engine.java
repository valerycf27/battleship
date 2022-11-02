package core;

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
		
		boolean victoryUser=false;
		boolean victoryAI=false;

		
		while(!victoryUser && !victoryAI) {
			user.attack(ai.getOceanGrid());
			ai.attack(user.getOceanGrid());
			victoryUser=Util.checkwinner(ai.getOceanGrid(),user.getTargetGrid());
			victoryAI=Util.checkwinner(user.getOceanGrid(),ai.getTargetGrid());
			Util.printBoards(user.getOceanGrid(),user.getTargetGrid());
			Util.printBoards(ai.getOceanGrid(),ai.getTargetGrid());

		}
		
	}

}
