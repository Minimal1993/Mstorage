/*
 * MStorage - storage for notes.
 * 
 * Permission is granted to copy, distribute and/or
 * modify  this  document under  the  terms  of the
 * GNU General Public License
 * 
 * @author: ilya.gulevskiy
 * @email: naveter@gmail.com
 * @date: 2016
 */
package mstorage;

import org.apache.commons.cli.*;

import mstorage.classes.Settings;

public class MStorage {

	protected static CommandLine CommandLine;

	public static CommandLine getCommandLine() {
		return CommandLine;
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		// Command line parser
		commandLineParser(args);

		MainForm mainFrame = new MainForm();
//		mainFrame.setBounds(200, 100, 400, 300);
		mainFrame.setVisible(true);
	}

	/**
	 * Create and parse command line options
	 */
	private static void commandLineParser(String[] args) {
		CommandLineParser parser = new DefaultParser();

		// create the Options
		Options options = new Options();
		options.addOption("s", "settings", true, "set the another name of settings file.");

		try {
			// parse the command line arguments
			CommandLine = parser.parse(options, args);

			// validate that block-size has been set
			if (CommandLine.hasOption("settings")) {
				// print the value of block-size
				System.out.println(CommandLine.getOptionValue("settings"));
			}
		} catch (ParseException exp) {
			System.out.println("Unexpected exception:" + exp.getMessage());
		}

	}

}
