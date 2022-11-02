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
		for(int i = 0; i<10; i++) {
			System.out.print(i);
			if(targetGrid == null) {
				printOceanGrid(10,oceanGrid,i);
			}else {
				printTargetGrid(10,targetGrid,i);
			}
			
			System.out.println("|"+i);
		}
	}
	private static void printOceanGrid(int size,Ship[][] oceanGrid,int fila) {
		if (oceanGrid==null) {
			for(int i=0; i<size; i++) {
				System.out.print("| ");
			}
		}else {
			for(int i=0; i<size; i++) {
				if(oceanGrid[fila][i] != null) {
					System.out.print("|"+oceanGrid[fila][i].getLetter());
				}else {
					System.out.print("| ");
				}
			}
		}
		
	}
	
	private static void printTargetGrid(int size,char[][] targetGrid,int fila) {
		if (targetGrid==null) {
			for(int i=0; i<size; i++) {
				System.out.print("| ");
			}
		}else {
			for(int i=0; i<size; i++) {
				if(targetGrid[fila][i] != '\u0000') {
					System.out.print("|"+targetGrid[fila][i]);
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
		for(int asciiValue = 65; asciiValue < 75 ; asciiValue++) {
			 char convertedChar = (char)asciiValue;
		        System.out.print(convertedChar+" ");
		}
		System.out.println();
	}
	
	public static boolean checkwinner(Ship[][] oceanGrid,char [][] targetGrid) {
		for (int i=0;i<10;i++) {
			for (int j=0;j<10;j++) {
				if (oceanGrid[i][j] != null) {
					if(oceanGrid[i][j].getLetter()!=targetGrid[i][j]) {
						return false;
					}
				}
			}
		}
		return true;
	}
}
