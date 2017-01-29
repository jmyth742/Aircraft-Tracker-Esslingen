package adsbmesser;

import java.lang.*;
import java.util.*;

/**
 * @author Jonathan Smyth
 * @version 1.0
 * */
public class AISUtil
{
	private static Map<String,String> convTable = new HashMap<String,String>();

	static{
		convTable.put("000000","@");
		convTable.put("000001","A");
		convTable.put("000010","B");
		convTable.put("000011","C");
		convTable.put("000100","D");
		convTable.put("000101","E");
		convTable.put("000110","F");
		convTable.put("000111","G");
		convTable.put("001000","H");
		convTable.put("001001","I");
		convTable.put("001010","J");
		convTable.put("001011","K");
		convTable.put("001100","L");
		convTable.put("001101","M");
		convTable.put("001110","N");
		convTable.put("001111","O");
		convTable.put("010000","P");
		convTable.put("010001","Q");
		convTable.put("010010","R");
		convTable.put("010011","S");
		convTable.put("010100","T");
		convTable.put("010101","U");
		convTable.put("010110","V");
		convTable.put("010111","W");
		convTable.put("011000","X");
		convTable.put("011001","Y");
		convTable.put("011010","Z");
		convTable.put("011011","[");
		convTable.put("011100","\\");
		convTable.put("011101","]");
		convTable.put("011110","^");
		convTable.put("011111","_");
		convTable.put("100000"," ");
		convTable.put("100001","!");
		convTable.put("100010","\"");
		convTable.put("100011","#");
		convTable.put("100100","$");
		convTable.put("100101","%");
		convTable.put("100110","&");
		convTable.put("100111","'");
		convTable.put("101000","(");
		convTable.put("101001",")");
		convTable.put("101010","*");
		convTable.put("101011","+");
		convTable.put("101100",",");
		convTable.put("101101","-");
		convTable.put("101110",".");
		convTable.put("101111","/");
		convTable.put("110000","0");
		convTable.put("110001","1");
		convTable.put("110010","2");
		convTable.put("110011","3");
		convTable.put("110100","4");
		convTable.put("110101","5");
		convTable.put("110110","6");
		convTable.put("110111","7");
		convTable.put("111000","8");
		convTable.put("111001","9");
		convTable.put("111010",":");
		convTable.put("111011",";");
		convTable.put("111100","<");
		convTable.put("111101","=");
		convTable.put("111110",">");
		convTable.put("111111","?");

		convTable=Collections.unmodifiableMap(convTable);
	}

	/**
	 * @param 	aisbin 	binary String, sixbit ASCII
	 * @return		aisLetter, 8-bit-ASCII-String (1 character)
	 * */
	public static String aisLetter(String aisbin)
	{
		int len = aisbin.length();
		int chunks = len / 6;

		StringBuffer sb = new StringBuffer();

		for(int i = 0; i < chunks;i++){
			int begin = i * 6;
			int end = begin + 6;

			String convTableKey = aisbin.substring(begin,end);
			String convTableValue = convTable.get(convTableKey);
			sb.append(convTableValue);
		}

		return sb.toString();
	}
}
