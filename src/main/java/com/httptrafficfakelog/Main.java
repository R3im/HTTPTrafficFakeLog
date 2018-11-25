package com.httptrafficfakelog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

/**
 * Launch HTTP traffic fake log generation
 * 
 * @author Remi c
 *
 */
public class Main {

	/**
	 * console width for console log design default: 100char (char count)
	 */
	private final static int CONSOLE_WIDTH = 100;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		AnsiConsole.systemInstall();
		try {
			boolean isVerbose = false;
			for (int i = 0; i < args.length; i++) {
				String arg = args[i];
				if (arg.equals("--help") || arg.equals("-h") || arg.equals("-?")) {// help option
					showHelpLog();
				} else if (arg.equals("-log") || arg.equals("-l")) {// HTTP log path option
					if (args.length <= i + 1) {// bad parameter
						showBadParameterLog();
					} else if (!args[i + 1].endsWith(".log")) {// bad parameter
						showBadParameterLog();
					} else {
						final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
						final Configuration config = ctx.getConfiguration();
						config.getProperties().put("APP_LOG_FILENAME",
								args[i + 1].substring(0, args[i + 1].lastIndexOf(".log") - 4));
						ctx.updateLoggers();
					}
				} else if (arg.equals("-v") || arg.equals("-verbose")) {
					isVerbose = true;
					break;
				}
			}
			if (!isVerbose) {
				final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
				final Configuration config = ctx.getConfiguration();
				config.getRootLogger().removeAppender("Console");
				ctx.updateLoggers();
			}
			new FakeLog().startGenerating();
		} finally {
			AnsiConsole.out.println(Ansi.ansi().reset());
			AnsiConsole.systemUninstall();
		}
	}

	/**
	 * show bad parameter console log also showing help
	 */
	private static void showBadParameterLog() {
		AnsiConsole.out.println(Ansi.ansi().fgRed().a(" !!! BAD PARAMETER !!!").reset());
		showHelpLog();
	}

	/**
	 * show help console log
	 */
	private static void showHelpLog() {
		appendLogFilled();
		appendLogTitle(" HTTP TRAFFIC FAKE LOG ");
		appendLogFilled();
		appendLogTitle(" HELP ");
		appendLogFilled();
		AnsiConsole.out.println("  USAGE: HTTPLogMonitoringTool [option...] [--help] ");
		AnsiConsole.out.println("   -?, -h, --help \t\tShows this help message.");
		AnsiConsole.out.println("   -log, -l \t\t\tSet HTTP log file fullpath (default: \"/var/log/access.log\").");
		AnsiConsole.out.println("   -verbose, -v \t\tLog statistics into console while writing to file.");
		appendLogFilled();
		appendLogFilled();
		System.exit(0);
	}

	/**
	 * fill log row with '-' on {@link #CONSOLE_WIDTH}
	 */
	private static void appendLogFilled() {
		StringBuilder logSB = new StringBuilder();
		logSB.append(' ');
		for (int i = 0; i < CONSOLE_WIDTH - 1; i++) {
			logSB.append('-');
		}
		AnsiConsole.out.println(logSB.toString());
	}

	/**
	 * log centered title surrounded by '-'
	 * 
	 * @param title
	 */
	private static void appendLogTitle(String title) {
		StringBuilder logSB = new StringBuilder();
		// fill last part
		for (int i = CONSOLE_WIDTH / 2 - title.length() / 2 - 6; i >= 0; i--) {
			logSB.append('-');
		}
		logSB.append(title);
		appendLog('-', logSB.toString());
	}

	/**
	 * log messages surrounded with #fillChar and bordered by '-'
	 * 
	 * @param fillChar
	 * @param messages
	 */
	private static void appendLog(char fillChar, String... messages) {
		StringBuilder logSB = new StringBuilder();
		logSB.append(' ');
		logSB.append('-');
		logSB.append(fillChar);
		logSB.append(fillChar);
		for (String message : messages) {
			logSB.append(message);
		}
		// complete row with -
		int sbLength = logSB.length();
		for (int i = 0; i < CONSOLE_WIDTH - sbLength - 1; i++) {
			logSB.append(fillChar);
		}
		logSB.append('-');
		AnsiConsole.out.println(logSB.toString());
	}

}
