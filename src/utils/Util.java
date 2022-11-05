package utils;

import ships.Ship;

public class Util {
	
	public static void printBoards(Ship[][] oceanGrid,char [][] targetGrid) {
		printHeader(" TARGET GRID ");
		printColumns();
		printMargin();
		printGrid(null,targetGrid);
		printMargin();
		printColumns();
		printSymbols(23, "=");
		
		for(int i = 0; i<2;i++) {
			System.out.println();
		}
		printSymbols(23, "-");
		for(int i = 0; i<2;i++) {
			System.out.println();
		}
		
		printHeader(" OCEAN  GRID ");
		printColumns();
		printMargin();
		printGrid(oceanGrid,null);
		printMargin();
		printColumns();
		printSymbols(23, "=");
		System.out.println();
	}

	private static void printMargin() {
		System.out.print(" ");
		printSymbols(10, "+-");
		System.out.println("+");
	}

	private static void printGrid(Ship[][] oceanGrid,char [][] targetGrid) {
		for(int i = 0; i<Constants.TEN; i++) {
			System.out.print(i);
			if(targetGrid == null) {
				printOceanGrid(Constants.TEN,oceanGrid,i);
			}else {
				printTargetGrid(Constants.TEN,targetGrid,i);
			}
			
			System.out.println("|"+i);
		}
	}
	
	private static void printOceanGrid(int size,Ship[][] oceanGrid,int row) {
		if (oceanGrid==null) {
			for(int i=0; i<size; i++) {
				System.out.print("| ");
			}
		}else {
			for(int i=0; i<size; i++) {
				if(oceanGrid[row][i] != null) {
					System.out.print("|"+oceanGrid[row][i].getArrayShip()[oceanGrid[row][i].getCounterArray()]);
					oceanGrid[row][i].setCounterArray(oceanGrid[row][i].getCounterArray()+1);
					if (oceanGrid[row][i].getCounterArray() == oceanGrid[row][i].getSize()) {
						oceanGrid[row][i].setCounterArray(oceanGrid[row][i].getCounterArray()-oceanGrid[row][i].getSize());
					}
				}else {
					System.out.print("| ");
				}
			}
		}
		
	}
	
	private static void printTargetGrid(int size,char[][] targetGrid,int row) {
		if (targetGrid==null) {
			for(int i=0; i<size; i++) {
				System.out.print("| ");
			}
		}else {
			for(int i=0; i<size; i++) {
				if(targetGrid[row][i] != '\u0000') {
					System.out.print("|"+targetGrid[row][i]);
				}else {
					System.out.print("| ");
				}
			}
		}
		
	}

	private static void printSymbols(int size, String symbols) {
		for(int i=0; i<size; i++) {
			System.out.print(symbols);
		}
		
	}

	public static void printHeader(String header) {
		printSymbols(5, "=");
		System.out.print(header);
		printSymbols(5, "=");
		System.out.println();
	}
	
	private static void printColumns() {
		System.out.print("  ");
		for(int asciiValue = Constants.A_ASCII; asciiValue < Constants.K_ASCII ; asciiValue++) {
			 char convertedChar = (char)asciiValue;
		        System.out.print(convertedChar+" ");
		}
		System.out.println();
	}
	
	public static boolean checkwinner(Ship[][] oceanGrid,char [][] targetGrid) {
		for (int i=0;i<Constants.TEN;i++) {
			for (int j=0;j<Constants.TEN;j++) {
				if (oceanGrid[i][j] != null && targetGrid[i][j]=='\u0000') {
					return false;
				}
			}
		}
		return true;
	}
	
}
