package com.httptrafficfakelog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;

public class Main {

	public static void main(String[] args) {
		boolean isVerbose = false;
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.equals("-v")) {
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
	}
	
}
