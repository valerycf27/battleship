package players;

import java.util.HashMap;
import java.util.Scanner;

import ships.Battleship;
import ships.Carrier;
import ships.PatrolBoat;
import ships.Ship;
import ships.Submarine;
import utils.Constants;

public class User implements Player{
	private Ship [][] oceanGrid;
	private char [][] targetGrid;
	private Ship[] shipList = new Ship [Constants.TEN];
	
	private HashMap<String, Integer> lettersMap = new HashMap<String, Integer>();
	
	public User() {
		oceanGrid = new Ship[Constants.TEN][Constants.TEN];
		targetGrid = new char[Constants.TEN][Constants.TEN];
		
		shipList[0] = new Carrier(1);
		
		for(int i = 1; i<1+Constants.NUM_BATTLESHIP; i++) {
			shipList[i] = new Battleship(i);
		}
		for(int i = 3; i<3+Constants.NUM_SUBMARINE; i++) {
			shipList[i] = new Submarine(i-2);
		}
		for(int i=6;i<6+Constants.NUM_PATROLBOAT; i++) {
			shipList[i] = new PatrolBoat(i-5);
		}
		
		int count = 0;
		for(int asciiValue = Constants.A_ASCII; asciiValue < Constants.K_ASCII ; asciiValue++) {
			 char convertedChar = (char)asciiValue;
			 lettersMap.put(Character.toString(convertedChar), count);
			 count++;
		}
	}
	
	@Override
	public void createOceanGrid() {
		
		int counter=0;
//		Scanner teclado = new Scanner(System.in);
		
		while (counter<shipList.length) {
			System.out.print("\nPlease enter the position of your "+shipList[counter].getName()+" "+shipList[counter].getId() +":\n");
			Scanner teclado = new Scanner(System.in); //QUITAR
			String coordinate;
			coordinate=teclado.nextLine();
			String letter1 = coordinate.substring(0,1);
			String n1 = coordinate.substring(1,2);
			int number1 = Integer.parseInt(n1);
			String letter2 = coordinate.substring(3,4);
			String n2 = coordinate.substring(4,5);
			int number2 = Integer.parseInt(n2);
			
			if (letter1.equals(letter2) && Math.abs(number2-number1)==shipList[counter].getSize()-1) {  //COLUMN SHIP
				if (checkOverlapping(number1,lettersMap.get(letter1),counter,this.oceanGrid, true)) {
					shipList[counter].setVertical(true);
					placeShip(number1,lettersMap.get(letter1),counter,this.oceanGrid);
					counter++;
				}
			}
			else if (number1==number2 && (Math.abs(lettersMap.get(letter1)-lettersMap.get(letter2)))==shipList[counter].getSize()-1){ //ROW SHIP
				if (checkOverlapping(number1,lettersMap.get(letter1),counter,this.oceanGrid, false)) {
					shipList[counter].setVertical(false);
					placeShip(number1,lettersMap.get(letter1),counter,this.oceanGrid);
					counter++;
				}
			}
			else {
				System.out.println("The coordinates are not well introduced. Please try again");
			}
		}
//		teclado.close();
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
//		Scanner teclado = new Scanner(System.in);

	    while (!notAttack) {
			System.out.print("Please select a position within the board to attack: \n");
			Scanner teclado = new Scanner(System.in); //QUITAR
			String coordenada;
			coordenada=teclado.nextLine();
			String letra1 = coordenada.substring(0,1);
			String n1 = coordenada.substring(1,2);
			int number1 = Integer.parseInt(n1);
		    
		    if (targetGrid[number1][lettersMap.get(letra1)]== '\u0000'){
		    	if (rivalGrid[number1][lettersMap.get(letra1)]!=null) {
		    		targetGrid[number1][lettersMap.get(letra1)]='X';
//		    		rivalGrid[number1][lettersMap.get(letra1)].setLetter('X');
		    		int checkeo[]=movePosition(number1,lettersMap.get(letra1),rivalGrid);
		    		insertXoceanGrid(checkeo,number1,lettersMap.get(letra1),rivalGrid);
		    		checkShipSunk(checkeo[0],checkeo[1],rivalGrid,targetGrid); //CHECK IF ALL POSITIONS OF THE SHIP HAVE BEEN HIT
		    	}
		    	else {
		    		targetGrid[number1][lettersMap.get(letra1)]='o';
		    	}
		    	notAttack = true;
		    }

	    }
//	    teclado.close();
		
	}
	
	private boolean checkOverlapping(int x, int y,int positionList,Ship[][] og, boolean column) {
		for (int i=0;i<shipList[positionList].getSize();i++) {
			if (og[x][y] != null) {
				System.out.println("The Ship is overlapping other ships.");
				return false;
			}
			if (column) {
				x++;
			}else {
				y++;
			}
		}
		return true;
	}
	
	private void placeShip(int x,int y, int positionList,Ship[][] og) {
		for (int i=0;i<shipList[positionList].getSize();i++) {
			og[x][y]=shipList[positionList];
			if (og[x][y].isVertical()) {
				x++;
			}
			else {
				y++;
			}
		}
	}
	
	private int[] movePosition(int x,int y,Ship[][] rg) {
		if (rg[x][y].isVertical() && x>0) {
			if (rg[x][y].equals(rg[x-1][y])) {  //COLUMN SHIP
				return movePosition(x-1,y,rg);
			}			
		}
		else if(y>0){
			if ((rg[x][y].equals(rg[x][y-1]))) { //ROW SHIP
				return movePosition(x,y-1,rg);
			}			
		}
		int coordinates[]=new int[2];
		coordinates[0]=x;
		coordinates[1]=y;
		return coordinates;
	}
	
	private void insertXoceanGrid(int[] coordinates0,int x1,int y1,Ship[][] rg) {
		if (rg[x1][y1].isVertical()) {
			char[] letterShip=rg[x1][y1].getArrayShip();
			letterShip[x1-coordinates0[0]]='X';
			rg[x1][y1].setArrayShip(letterShip);
		}
		else {
			char[] letterShip=rg[x1][y1].getArrayShip();
			letterShip[y1-coordinates0[1]]='X';
			rg[x1][y1].setArrayShip(letterShip);			
		}
	}
	
	private void checkShipSunk(int x,int y, Ship[][] rg,char[][] tg) {
		boolean entireShip=true;
		int len=rg[x][y].getSize();
		for (int i=0;i<(len);i++) {
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
		}

	}

}
