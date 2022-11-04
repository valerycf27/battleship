package players;

import ships.Ship;

public interface Player {
	//Ship [] SHIPSLIST=new Ship[10];
	public void attack(Ship[][]grid);
	public void createOceanGrid();
	public Ship [][] getOceanGrid();
	public char [][] getTargetGrid();
}
