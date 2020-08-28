package app.concurrent.gui;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class CheckingOrtography {

	String original, dyktanded;

	private List<String> mistakes;

	public void setTexts(String original, String dyktanded) {
		this.original = original;
		this.dyktanded = dyktanded;
		this.mistakes = new ArrayList<String>();
	}

	public void checkDyktando() throws OrtographyException {
		String[] cutOriginal = original.split("\\s");

		String[] cutDyktanded = dyktanded.split("\\s");

		int longer = (cutDyktanded.length > cutOriginal.length) ? cutDyktanded.length
				: cutOriginal.length;

		for (int i = 0; i < longer; i++)
			if (!cutOriginal[i].equals(cutDyktanded[i]))
				mistakes.add(cutDyktanded[i]);

		throw new OrtographyException(mistakes);
	}
}