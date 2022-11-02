package utils;

import java.util.HashMap;

public class Constantes {
	HashMap<String, Integer> lettersMap = new HashMap<String, Integer>();
	
	public void createHash() {
		int count = 0;
		for(int asciiValue = 65; asciiValue < 75 ; asciiValue++) {
			 char convertedChar = (char)asciiValue;
			 lettersMap.put(Character.toString(convertedChar), count);
			 count++;
		}
	}
	
}
