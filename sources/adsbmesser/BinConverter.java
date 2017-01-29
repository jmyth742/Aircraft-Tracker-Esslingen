package adsbmesser;

import java.lang.*;
/**
 * Class to convert hexadecimal values to binary ones
 * @Author David Mändlen, damait06@hs-esslingen.de
 * */
public class BinConverter
{
	/**
	 * @param 	hexString 	a String, containing a hex-formatted value
	 * @return 			A String, containing the same value formatted as binary
	 * */
	public static String hex2bin(String hexString)
	{
		StringBuffer binStringBuffer = new StringBuffer();

		for(int i = 0; i < hexString.length(); i++){
			String character = hexString.substring(i,i+1);
			int charAsInt = Integer.parseInt(character,16);
			String binCharacter = Integer.toBinaryString(charAsInt);
			int missingZeroes = 4 - binCharacter.length();

			StringBuffer leadingZeroes = new StringBuffer();

			for(int j = 0; j < missingZeroes; j++){
				leadingZeroes.append("0");
			}
			binStringBuffer.append(leadingZeroes.toString() + binCharacter);
		}

		return binStringBuffer.toString();
	}
}
