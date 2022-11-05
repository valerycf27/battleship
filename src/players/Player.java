package players;

import ships.Ship;

public interface Player {
	public void createOceanGrid();
	public Ship [][] getOceanGrid();
	public char [][] getTargetGrid();
	public void attack(Ship[][]grid);
	
}
