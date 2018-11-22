package com.rc.httptrafficfakelog;

import org.apache.log4j.Logger;

public class FakeLog {
	
	static Logger logger = Logger.getLogger(FakeLog.class);

	public static void main(String[] args) {
		logger.debug("Hello world.");
	}
}
