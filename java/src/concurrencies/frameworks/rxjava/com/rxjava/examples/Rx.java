package com.rxjava.examples;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import rx.Observable;
import rx.functions.Action1;

/**
 * RxJava-Observable
 * <p>
 * Demonstrate rc.Observable via Java simple example
 * <p>
 * java -jar target/rxjava...jar One Two Three
 * <p>
 * See some good reading at
 *
 * @author EMAIL:vuquangtin@gmail.com , tel:0377443333
 * 
 * @version 1.0.0
 * @see <a
 *      href="https://github.com/vuquangtin/concurrency">https://github.com/vuquangtin/concurrency</a>
 *
 */
public class Rx {

	// options
	public static boolean strictMode = false; // uses dice method, not computer
												// shortCuts
	public static int numWords = 6;
	public static String dictionaryName = "";

	public static void main(String[] args) throws Exception {
		hello(args);
	}

	public static void hello(String... names) {
		Observable.from(names).subscribe(new Action1<String>() {
			@Override
			public void call(String s) {
				System.out.println("Hello " + s + "!");
			}
		});
	}

	/** Command line options for this application. */
	public static void parseGatherOptions(String[] args) throws ParseException {
		// Parse the command line arguments
		Options options = new Options();
		options.addOption("h", "help", false, "print the command line options.");
		options.addOption("n", "numWords", true,
				"uses this many words in the pass-phrase.");
		CommandLineParser cliParser = new DefaultParser();
		CommandLine line = cliParser.parse(options, args);

		// Gather command line arguments for execution
		if (line.hasOption("help")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("java -jar target/rxjava...jar <options> ",
					options);
			System.exit(0);
		}
		if (line.hasOption("numWords")) {
			numWords = Integer.parseInt(line.getOptionValue("numWords"));
		}
	}

}
