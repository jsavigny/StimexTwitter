
public class BoyerMoore{
	public static int bmMatch(String texti, String patterni){
		String text = texti.toLowerCase();
		String pattern = patterni.toLowerCase();
		int last[] = buildLast(pattern);
		int n = text.length();
		int m = pattern.length();
		int i = m - 1;

		if (i > n - 1)
			return -1;

		int j = m - 1;
		do {
			if (pattern.charAt(j) == text.charAt(i)) {
				if (j == 0)
					return i;
				else {
					i--;
					j--;
				}
			}
			else{
				int lo = last[text.charAt(i)];
				i = i + m - Math.min(j, 1 + lo);
				j = m - 1;
			}
		} while (i <= n - 1);

		return -1;
	}

	public static int[] buildLast(String pattern){
		int last[] = new int[128];
		for (int i = 0; i < 128; i++)
			last[i] = -1;

		for (int i = 0; i < pattern.length(); i++)
			last[pattern.charAt(i)] = i;

		return last;
	}

}