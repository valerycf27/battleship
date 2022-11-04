package players;

import java.util.HashMap;
import java.util.Scanner;

import ships.Battleship;
import ships.Carrier;
import ships.PatrolBoat;
import ships.Ship;
import ships.Submarine;

public class User implements Player{
	private Ship [][] oceanGrid;
	private char [][] targetGrid;
	private Ship[] shipList = new Ship [10];
	private HashMap<String, Integer> lettersMap = new HashMap<String, Integer>();

	
	public User() {
		oceanGrid = new Ship[10][10];
		targetGrid = new char[10][10];
		
		shipList[0] = new Carrier(1);
		shipList[1] = new Battleship(1);
		shipList[2] = new Battleship(2);
		
		for(int i = 3; i<6; i++) {
			shipList[i] = new Submarine(i-2);
		}
		for(int i = 6; i<10; i++) {
			shipList[i] = new PatrolBoat(i-5);
		}	
		
		int count = 0;
		for(int asciiValue = 65; asciiValue < 75 ; asciiValue++) {
			 char convertedChar = (char)asciiValue;
			 lettersMap.put(Character.toString(convertedChar), count);
			 count++;
		}
	}
	
	@Override
	public Ship[][] getOceanGrid() {
		return oceanGrid;
	}
	
	@Override
	public void createOceanGrid() {
		
		int counter=0;
		//System.out.println(lettersMap);
		//inicializar
	    
		while (counter<shipList.length) {
			System.out.print("\nPlease enter the position of your "+shipList[counter].getName()+": ");
			String coordenada;
			Scanner teclado = new Scanner(System.in);
			coordenada=teclado.nextLine();
			String letra1 = coordenada.substring(0,1);
			String n1 = coordenada.substring(1,2);
			int number1 = Integer.parseInt(n1);
			String letra2 = coordenada.substring(3,4);
			String n2 = coordenada.substring(4,5);
			int number2 = Integer.parseInt(n2);
			
			if (letra1.equals(letra2) && Math.abs(number2-number1)==shipList[counter].getSize()-1) {  //COLUMN SHIP
				if (checkShip(number1,lettersMap.get(letra1),counter,this.oceanGrid, true)) {
					shipList[counter].setVertical(true);
					placeShip(number1,lettersMap.get(letra1),counter,this.oceanGrid);
					counter++;
				}
				//System.out.println("The ship is overlapping other ships. Please try again.\n");
			}
			else if (number1==number2 && (Math.abs(lettersMap.get(letra1)-lettersMap.get(letra2)))==shipList[counter].getSize()-1){ //ROW SHIP
				if (checkShip(number1,lettersMap.get(letra1),counter,this.oceanGrid, false)) {
					shipList[counter].setVertical(false);
					placeShip(number1,lettersMap.get(letra1),counter,this.oceanGrid);
					counter++;
				}
			}
			//System.out.println("The coordinates are not correct. Please try again.\n");
		}
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

	private boolean checkShip(int x, int y,int positionList,Ship[][] og, boolean column) {
		for (int i=0;i<shipList[positionList].getSize();i++) {
			if (og[x][y] != null) {
				System.out.println("The Ship is overlapping other ships");
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


	@Override
	public void attack(Ship[][] rivalGrid) {
		boolean notAttack=false;
	    while (!notAttack) {
			System.out.print("Please select a position within the board to attack: ");
			String coordenada;
			Scanner teclado = new Scanner(System.in);
			coordenada=teclado.nextLine();
			String letra1 = coordenada.substring(0,1);
			String n1 = coordenada.substring(1,2);
			int number1 = Integer.parseInt(n1);
		    
		    if (targetGrid[number1][lettersMap.get(letra1)]== '\u0000'){
		    	if (rivalGrid[number1][lettersMap.get(letra1)]!=null) {
		    		targetGrid[number1][lettersMap.get(letra1)]='X';
		    		int checkeo[]=movePosition(number1,lettersMap.get(letra1),rivalGrid);
		    		checkShipSunk(checkeo[0],checkeo[1],rivalGrid,targetGrid); //CHECK IF ALL POSITIONS OF THE SHIP HAVE BEEN HIT
		    	}
		    	else {
		    		targetGrid[number1][lettersMap.get(letra1)]='o';
		    	}
		    	notAttack = true;
		    }

	    }	
		
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

	@Override
	public char[][] getTargetGrid() {
		return targetGrid;
	}
}
