package players;

import java.util.Random;

import ships.Battleship;
import ships.Carrier;
import ships.PatrolBoat;
import ships.Ship;
import ships.Submarine;

public class AI implements Player{
	
	private Ship [][] oceanGrid;
	private char [][] targetGrid;
	private Ship[] shipList = new Ship [10];
	
	
	private int[] coordinateHit=new int[2];
	private boolean shipTouched;
	private boolean verticalDirection;
	
	public AI() {
		oceanGrid = new Ship[10][10];
		targetGrid = new char[10][10];
		
		shipList[0] = new Carrier();
		shipList[1] = new Battleship();
		shipList[2] = new Battleship();
		
		for(int i = 3; i<6; i++) {
			shipList[i] = new Submarine();
		}
		for(int i = 6; i<10; i++) {
			shipList[i] = new PatrolBoat();
		}	
		
		
	}
	
	@Override
	public void attack(Ship[][] rivalGrid) {   
		boolean notAttack=false;
		int x1;
	    int y1;
	    while (!notAttack) {
	    	if (shipTouched) {
	    		if (rangeCheck()) {			//IF THERE IS A HIT AND THE AREA IS ALREADY CHECKED, WE MOVE TO LOOK FOR MORE HITS
	    			do {
		    			if (verticalDirection) {
		    				coordinateHit[0]++;
		    			}
		    			else {
		    				coordinateHit[1]++;
		    			}
	    			} while(targetGrid[coordinateHit[0]][coordinateHit[1]]== 'X');
	    		}
	    		Random rd = new Random();   //ONCE WE HAVE A HIT, WE CHECK IN THE AREA FOR MORE HITS
	    		if (rd.nextBoolean()) {  	//COLUMN POSITION
	    			if (rd.nextBoolean()) {	//UP
	    				x1=coordinateHit[0]-1;
	    				y1=coordinateHit[1];
	    			}
	    			else {					//DOWN
	    	    		x1=coordinateHit[0]+1;
	    	    		y1=coordinateHit[1];
	    			}
	    		}
	    		else {						//ROW POSITION
	    			if (rd.nextBoolean()) {	//LEFT
	    				x1=coordinateHit[0];
	    				y1=coordinateHit[1]-1;
	    			}
	    			else {					//RIGHT
	    	    		x1=coordinateHit[0];
	    	    		y1=coordinateHit[1]+1;
	    			}
	    		}	    			
	    	}
	    	
	    	else {
				Random rand = new Random(); //instance of random class
			    x1 = rand.nextInt(shipList.length);
			    y1 = rand.nextInt(shipList.length);
	    	}

		    
		    if (targetGrid[x1][y1]== '\u0000'){
		    	if (rivalGrid[x1][y1] != null) {
		    		targetGrid[x1][y1]='X';
		    		if (x1-coordinateHit[0]==1) {
		    			verticalDirection=true;
		    		}
//		    		else if (y1-coordinateHit[1]==1) {
//		    			horizontalDirection=true;
//		    		}
		    		coordinateHit[0]=x1;
		    		coordinateHit[1]=y1;
		    		shipTouched=true;
		    		checkShipSunk(x1,y1,rivalGrid,targetGrid);	    		
		    	}
		    	else {
		    		targetGrid[x1][y1]='o';
		    	}
		    	notAttack=true;
		    }

	    }
	}
	
	private boolean rangeCheck() {
		if ((targetGrid[coordinateHit[0]+1][coordinateHit[1]]== '\u0000') || (targetGrid[coordinateHit[0]-1][coordinateHit[1]]== '\u0000') || (targetGrid[coordinateHit[0]][coordinateHit[1]+1]== '\u0000') || (targetGrid[coordinateHit[0]][coordinateHit[1]-1]== '\u0000')){
			return false;
		}
		else {
			return true;
		}
	}
	@Override
	public void createOceanGrid() {
		int counter=0;
	    
		while (counter<shipList.length) {
			Random rand = new Random(); //instance of random class
		    int x = rand.nextInt(shipList.length);
		    int y = rand.nextInt(shipList.length);
		    
		    int direction = rand.nextInt(4);
		    
			if (checkShip(x,y,direction,counter,this.oceanGrid)) {
				placeShip(x,y,direction,counter,this.oceanGrid);
				counter++;
			}
		}
		
	}

	@Override
	public Ship[][] getOceanGrid() {
		return oceanGrid;
	}
	
	@Override
	public char[][] getTargetGrid() {
		return targetGrid;
	}
	
	private boolean checkShip(int x, int y, int direction,int positionList,Ship[][] og) {
		for (int i=0;i<shipList[positionList].getSize();i++) {
			if (x>9 || x<0 || y>9 ||y<0) {
				return false;
			}
			if (og[x][y] != null) {
				return false;
			}
			if (direction==0) {
				y++;
			}
			else if (direction==1) {
				x++;
			}
			else if (direction==2) {
				y--;
			}
			else if (direction==3) {
				x--;
			}
		}
		return true;
	}
	
	private void placeShip(int x,int y, int direction, int positionList,Ship[][] og) {
		for (int i=0;i<shipList[positionList].getSize();i++) {
			og[x][y]=shipList[positionList];
			if (direction==0) {
				y++;
			}
			else if (direction==1) {
				og[x][y].setVertical(true);
				x++;
			}
			else if (direction==2) {
				y--;
			}
			else if (direction==3) {
				og[x][y].setVertical(true);
				x--;
			}
		}
	}
	
	private void checkShipSunk(int x,int y,Ship[][] rg,char[][] tg) {
		boolean entireShip;
		if (rg[x][y].isVertical()) {
			if ((tg[x-1][y]!= '\u0000') && (rg[x-1][y].equals(rg[x][y]))) {  //COLUMN SHIP
				checkShipSunk(x-1,y,rg,tg);
			}			
		}
		else{
			if ((tg[x][y-1]!= '\u0000') && (rg[x][y-1].equals(rg[x][y]))) { //ROW SHIP
				checkShipSunk(x,y-1,rg,tg);
			}			
		}
		entireShip=true;
		
		for (int i=0;0<rg[x][y].getSize();i++) {
			if (tg[x][y]== '\u0000') {
				entireShip=false;
				break;
			}
			if (rg[x][y].isVertical()) {
				x++;
			}
			else {
				y++;
			}
		}
		if (entireShip) {
			for (int i=0;0<rg[x][y].getSize();i++) {
				tg[x][y]=rg[x][y].getLetter();
				if (rg[x][y].isVertical()) {
					x--;
				}
				else {
					y--;
				}
			}
			shipTouched = false;
		}

	}


}
