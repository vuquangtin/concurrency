package com.rxjava.examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * Provides and Iterable interface to a text file. Makes text files amenable to
 * Reactive/Stream APIs.
 * 
 * @author dabecker
 */
public class BufferedReaderIterator implements Iterable<String> {

	private BufferedReader reader;

	public BufferedReaderIterator(BufferedReader r) {
		this.reader = r;
	}

	public BufferedReaderIterator(File f) throws FileNotFoundException {
		this.reader = new BufferedReader(new FileReader(f));
	}

	public BufferedReaderIterator(String filePath) throws FileNotFoundException {
		this.reader = new BufferedReader(new FileReader(new File(filePath)));
	}

	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {
			@Override
			public boolean hasNext() {
				try {
					reader.mark(1);
					if (reader.read() < 0) {
						return false;
					}
					reader.reset();
					return true;
				} catch (IOException e) {
					return false;
				}
			}

			@Override
			public String next() {
				try {
					return reader.readLine();
				} catch (IOException e) {
					return null;
				}
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
}