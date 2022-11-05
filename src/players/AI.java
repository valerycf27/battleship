package players;

import java.util.Random;

import ships.Battleship;
import ships.Carrier;
import ships.PatrolBoat;
import ships.Ship;
import ships.Submarine;
import utils.Constants;

public class AI implements Player{
	private Ship [][] oceanGrid;
	private char [][] targetGrid;
	private Ship[] shipList = new Ship [Constants.TEN];
	
	
	private int[] coordinateHit=new int[2];
	private boolean shipTouched;
	private boolean verticalDirection;
	private boolean horizontalDirection;

	
	public AI() {
		oceanGrid = new Ship[Constants.TEN][Constants.TEN];
		targetGrid = new char[Constants.TEN][Constants.TEN];
		
		int i;
		for (i=1;i<=Constants.NUM_CARRIER;i++) {
			shipList[0] = new Carrier(i);
		}
		for (i=1;i<=Constants.NUM_BATTLESHIP;i++) {
			shipList[0] = new Battleship(i);
		}
		for (i=1;i<=Constants.NUM_SUBMARINE;i++) {
			shipList[0] = new Submarine(i);
		}
		for (i=1;i<=Constants.NUM_PATROLBOAT;i++) {
			shipList[0] = new PatrolBoat(i);
		}

		
		
	}
	
	@Override
	public void createOceanGrid() {
		int counter=0;
		Random rand = new Random();
		
		while (counter<shipList.length) {
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
	
	@Override
	public void attack(Ship[][] rivalGrid) {   
		boolean notAttack=false;
		int x1=0;
	    int y1=0;
	    while (!notAttack) {
	    	if (shipTouched) {
	    		if (surroundingCheck(coordinateHit[0],coordinateHit[1])) {	//IF THERE IS A HIT AND THE AREA IS ALREADY CHECKED, WE MOVE TO LOOK FOR MORE HITS
	    			while(targetGrid[coordinateHit[0]][coordinateHit[1]]== 'X'){
		    			if (verticalDirection && coordinateHit[0]<9) {
		    				coordinateHit[0]++;
		    			}
		    			else if (verticalDirection && coordinateHit[0]>0) {
		    				coordinateHit[0]--;
		    			}
		    			else if(coordinateHit[1]<9){
		    				coordinateHit[1]++;
		    			}
		    			else {
		    				coordinateHit[1]--;
		    			}
	    			}
	    		}
	    		Random rd = new Random();  //ONCE WE HAVE A HIT, WE CHECK IN THE AREA FOR MORE HITS
	    		boolean column=rd.nextBoolean();
	    		boolean side=rd.nextBoolean();
	    		
	    		if (column) {  	//COLUMN POSITION
	    			if (coordinateHit[0]==0) {
	    				x1=coordinateHit[0]+1;
	    				y1=coordinateHit[1];
	    			}
	    			else if(coordinateHit[0]==9) {
	    				x1=coordinateHit[0]-1;
	    				y1=coordinateHit[1];
	    			}
	    			else if (side) {	//UP
	    				x1=coordinateHit[0]-1;
	    				y1=coordinateHit[1];
	    			}
	    			else{				//DOWN
	    	    		x1=coordinateHit[0]+1;
	    	    		y1=coordinateHit[1];
	    			}
	    		}
	    		else if (!column) {		//ROW POSITION
	    			
	    			if (coordinateHit[1]==0) {
	    				x1=coordinateHit[0];
	    				y1=coordinateHit[1]+1;
	    			}
	    			else if(coordinateHit[1]==9) {
	    				x1=coordinateHit[0];
	    				y1=coordinateHit[1]-1;
	    			}
	    			else if (side) {	//LEFT
	    				x1=coordinateHit[0];
	    				y1=coordinateHit[1]-1;
	    			}
	    			else {				//RIGHT
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

	    	
    		System.out.println("Antes de entrar: "+x1+" "+y1);
		    if (targetGrid[x1][y1]== '\u0000'){
	    		System.out.println(x1+" "+y1);
		    	if (rivalGrid[x1][y1] != null) {
		    		targetGrid[x1][y1]='X';
		    		rivalGrid[x1][y1].setLetter('X');
		    		if (Math.abs(x1-coordinateHit[0])==1) {
		    			verticalDirection=true;
		    		}
		    		else if(Math.abs(y1-coordinateHit[1])==1){
		    			horizontalDirection=true;
		    		}

		    		coordinateHit[0]=x1;
		    		coordinateHit[1]=y1;
		    		shipTouched=true;
		    		int checkeo[]=movePosition(x1,y1,rivalGrid);
		    		checkShipSunk(checkeo[0],checkeo[1],rivalGrid,targetGrid);
		    		if (!shipTouched) {
		    			verticalDirection=false;
		    			horizontalDirection=false;
		    			searchX();
		    		}
		    	}
		    	else {
		    		targetGrid[x1][y1]='o';
		    	}
		    	notAttack=true;
		    }

	    }
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
	
	private void searchX() {
		for (int i=0;i<10;i++) {
			for (int j=0;j<10;j++) {
				if (targetGrid[i][j]=='X' && !surroundingCheck(i,j)) {
					System.out.println("HOLA"+i+" "+j);
					coordinateHit[0]=i;
					coordinateHit[1]=j;
					shipTouched=true;
					return;
				}
			}
		}
	}
	
	private boolean surroundingCheck(int x,int y) { //FALLA AQUÍ
		if (x>0 && x<9 && y>0 && y<9) { //MEDIO DEL TABLERO
			if ((targetGrid[x+1][y]== '\u0000') || (targetGrid[x-1][y]== '\u0000') || (targetGrid[x][y+1]== '\u0000') || (targetGrid[x][y-1]== '\u0000')){
				return false;
			}			
		}
		else if (x==0) { //LADO ARRIBA
			if (y==0) { //ESQUINA SUPERIOR IZQUIERDA
				if ((targetGrid[x+1][y]== '\u0000')  || (targetGrid[x][y+1]== '\u0000')){
					return false;
				}
			}
			else if(coordinateHit[1]==9) { //ESQUINA SUPERIOR DERECHA
				if ((targetGrid[x+1][y]== '\u0000')  || (targetGrid[x][y-1]== '\u0000')){
					return false;
				}				
			}
			else {
				if ((targetGrid[x+1][y]== '\u0000')  || (targetGrid[x][y+1]== '\u0000') || (targetGrid[x][y-1]== '\u0000')){
					return false;
				}				
			}
		}
		else if (x==9) { //LADO ABAJO
			if (y==0) { //ESQUINA INFERIOR IZQUIERDA
				if ((targetGrid[x-1][y]== '\u0000')  || (targetGrid[x][y+1]== '\u0000')){
					return false;
				}
			}
			else if(y==9) { //ESQUINA INFERIOR DERECHA
				if ((targetGrid[x-1][y]== '\u0000')  || (targetGrid[x][y-1]== '\u0000')){
					return false;
				}				
			}
			else {
				if ((targetGrid[x-1][y]== '\u0000')  || (targetGrid[x][y+1]== '\u0000') || (targetGrid[x][y-1]== '\u0000')){
					return false;
				}				
			}
		}
		else if(y==0 && x>0 && x<9) { //LADO IZQUIERDO
			if ((targetGrid[x-1][y]== '\u0000')  || (targetGrid[x+1][y]== '\u0000') || (targetGrid[x][y+1]== '\u0000')){
				return false;
			} 
		}
		else if(y==9 && (x>0) && x<9) { //LADO DERECHO
			if ((targetGrid[x-1][y]== '\u0000')  || (targetGrid[x+1][y]== '\u0000') || (targetGrid[x][y-1]== '\u0000')){
				return false;
			} 
		}	
		return true;
	}
	
	private int[] movePosition(int x,int y,Ship[][] rg) {
		if (rg[x][y].isVertical() && x>0) {
			if (/*(tg[x-1][y]!= '\u0000') &&*/ rg[x][y].equals(rg[x-1][y])) {  //COLUMN SHIP
				return movePosition(x-1,y,rg);
			}			
		}
		else if(y>0){
			if (/*(tg[x][y-1]!= '\u0000') && */(rg[x][y].equals(rg[x][y-1]))) { //ROW SHIP
				return movePosition(x,y-1,rg);
			}			
		}
		int coordinates[]=new int[2];
		coordinates[0]=x;
		coordinates[1]=y;
		return coordinates;
	}
	
	private void checkShipSunk(int x,int y,Ship[][] rg,char[][] tg) {
		boolean entireShip=true;
		int len=rg[x][y].getSize();
		for (int i=0;i<len;i++) {
			if (tg[x][y]== '\u0000') {
				entireShip=false;
				break;
			}
			if (rg[x][y].isVertical() && i+1<len) {
				x++;
			}
			else if(i+1<len){
				y++;
			}
		}
		if (entireShip) {
			for (int i=0;i<len;i++) {
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
