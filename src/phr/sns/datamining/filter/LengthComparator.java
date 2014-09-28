package phr.sns.datamining.filter;

import java.util.Comparator;

public class LengthComparator implements Comparator<String> {

	@Override
	public int compare(String arg0, String arg1) {
		return Math.abs(arg1.length()) - Math.abs(arg0.length());
	}

}
