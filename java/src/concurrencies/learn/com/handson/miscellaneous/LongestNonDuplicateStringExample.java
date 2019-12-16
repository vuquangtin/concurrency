/**
 * 
 */
package com.handson.miscellaneous;

import static java.lang.System.out;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * @author sveera
 *
 */
public class LongestNonDuplicateStringExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String inputText = "abcdelmnopafghijka";
		String expectedTest = "abcde";
		char[] chars = inputText.toCharArray();
		Map<Character, Integer> charMap = new HashMap<>();
		StringBuffer stringBuffer = new StringBuffer();

		NavigableMap<String, Integer> stringMapCount = new TreeMap<>();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			int value = charMap.compute(c, (character, count) -> {
				return count == null ? 1 : count + 1;
			});
			// System.out.println(value);
			if (value > 1) {
				stringMapCount.put(stringBuffer.toString(), stringBuffer.toString().length());
				stringBuffer = new StringBuffer();
				stringBuffer.append(c);
				charMap = new HashMap<>();
				charMap.compute(c, (character, count) -> {
					return count == null ? 1 : count + 1;
				});
			} else
				stringBuffer.append(c);
		}
		String maxString = "";
		for (Entry<String, Integer> entry : stringMapCount.entrySet())
			maxString = entry.getKey().length() > maxString.length() ? entry.getKey() : maxString;

		/*
		 * stringMapCount.forEach((count, string) -> { System.out.println("Count " +
		 * count + " has string " + string); });
		 */
		System.out.println(stringMapCount.lastEntry().getKey());

		out.println("Max String is " + maxString);
	}

}
