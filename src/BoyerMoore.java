/**
 * Created by Julio on 4/16/2015.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class BoyerMoore {
	public List<Integer> match(String pattern, String text){
		List<Integer> matches = new Vector<>();
			// DEKLARASI LENGTH
			int m = text.length();
			int n = pattern.length();
			// PREPROSES
			Map<Character, Integer> rightMostIndex = preprocessForBadCharacterShift(pattern);
			// ALIGN START
			int alignedAt = 0;
			while (alignedAt + (n - 1) < m) {
				for (int iPattern = n - 1; iPattern >= 0; iPattern--){
					int iText = alignedAt + iPattern;
					char x = text.charAt(iText);
					char y = pattern.charAt(iPattern);
					if (iText >= m) //Break
						break;
					if (x != y){ //Mismatch
						Integer r = rightMostIndex.get(x); //Get Index
						if (r == null){
							alignedAt = iText + 1;
						}
						else{
							int shift = iText - (alignedAt + r);
							alignedAt += shift > 0 ? shift : alignedAt + 1;
						}
					}
					else if (iPattern == 0){ //Match
						matches.add(alignedAt);
						alignedAt++;
					}
				}	
			}	
			return matches;
	}	
			
	private Map<Character, Integer> preprocessForBadCharacterShift(String pattern){
		Map <Character, Integer> map = new HashMap<>();
		for (int i = pattern.length() - 1; i >= 0; i--){
			char c = pattern.charAt(i);
			if (!map.containsKey(c))
				map.put(c, i);
		}
		return map;
	}
	

}
						