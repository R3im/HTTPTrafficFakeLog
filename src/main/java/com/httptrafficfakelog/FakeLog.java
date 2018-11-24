package com.httptrafficfakelog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;

public class FakeLog {

	private final static Logger logger = LogManager.getLogger(FakeLog.class.getName());

	public static void main(String[] args) {
		boolean isVerbose = false;
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.equals("-v")) {
				isVerbose = true;
				break;
			}
		}
		final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		final Configuration config = ctx.getConfiguration();
		if (!isVerbose) {
			config.getRootLogger().removeAppender("Console");
			ctx.updateLoggers();
		}

		File file = new File(config.getProperties().get("APP_LOG_FILENAME")+".log");
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			logger.debug(e.getMessage(), e);
		}
		new FakeLog().startGenerating();
	}

	private final Random random = new Random();
	private final static int LOW_TRAFFIC_MAX_TIME = 1000 * 3;
	private final static int HIGH_TRAFFIC_MAX_TIME = 200;
	private final static int LOW_HIGH_MAX_TIME_SWITCH = 1000 * 60 * 2 + 1000 * 10;
	private final static String[] REMOTEHOSTS = new String[] { "127.0.0.1" };
	private final static String[] RFC931S = new String[] { "-" };
	private final static String[] AUTHUSERS = new String[] { "james", "jill", "frank", "mary", "jeanne", "john",
			"oliver", "jack", "harry", "jacob", "robert", "olivia", "amelia", "ava", "lily", "sophie", "bethany",
			"megan" };
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("[dd/MM/yyyy:HH:mm:ss ZZZ]");
	private final static String[] REQUEST_TYPE = new String[] { "GET", "POST", "PUT", "HEAD", "DELETE" };
	private final static String[] REQUEST_RESOURCE = new String[] { "/report", "/api", "/api/user", "/api/customer",
			"/api/product", "/shop", "/shop/product", "/shop/product/computer", "/shop/product/games",
			"/shop/product/clothes", "/shop/product/shoes", "/sport", "/sport/soccer", "/sport/baseball",
			"/sport/tennis", "/sport/baseball", "/sport/basketball", "/sport/golf", "/sport/hockey",
			"/sport/volleyball", "/sport/rugby", "/weather", "/bank", "/mail", "/health" };
	private final static String[] REQUEST_PROTOCOL = new String[] { "HTTP/1.0" };
	private final static int[] HTTP_STATUS_CODES = new int[] { 200, 300, 301, 302, 304, 307, 400, 401, 403, 404, 410,
			500, 501, 503, 550 };
	private final static int MAX_BYTES_LENGTH = 250;

	public FakeLog() {

	}

	public void startGenerating() {
		// starts infinite loop
		Date lastSwitchDate = new Date();
		int currentTrafficTime = LOW_TRAFFIC_MAX_TIME;
		while (true) {
			try {
				Date logDate = new Date();
				// do switch
				if (logDate.getTime() - lastSwitchDate
						.getTime() > (long) LOW_HIGH_MAX_TIME_SWITCH/* random.nextInt(LOW_HIGH_MAX_TIME_SWITCH) */) {
					lastSwitchDate = logDate;
					currentTrafficTime = currentTrafficTime == LOW_TRAFFIC_MAX_TIME ? HIGH_TRAFFIC_MAX_TIME
							: LOW_TRAFFIC_MAX_TIME;
				}
				// if is low traffic we get the random value between HIGH_TRAFFIC_MAX_TIME and
				// LOW_TRAFFIC_MAX_TIME
				if (currentTrafficTime == LOW_TRAFFIC_MAX_TIME) {
					TimeUnit.MILLISECONDS
							.sleep((long) random.nextInt(LOW_TRAFFIC_MAX_TIME - LOW_TRAFFIC_MAX_TIME / 2 + 1)
									+ LOW_TRAFFIC_MAX_TIME / 2);
				} else {
					TimeUnit.MILLISECONDS.sleep((long) random.nextInt(currentTrafficTime));
				}
				// append log
				logFakeTraffic(logDate);

			} catch (InterruptedException e) {
				logger.debug(e.getMessage(), e);
			}
		}
	}

	private void logFakeTraffic(Date logDate) {

		StringBuilder logSB = new StringBuilder();
		logSB.append(REMOTEHOSTS[random.nextInt(REMOTEHOSTS.length)]);
		logSB.append(" ");
		logSB.append(RFC931S[random.nextInt(RFC931S.length)]);
		logSB.append(" ");
		logSB.append(AUTHUSERS[random.nextInt(AUTHUSERS.length)]);
		logSB.append(" ");
		logSB.append(dateFormat.format(logDate));
		logSB.append(" \"");
		logSB.append(REQUEST_TYPE[random.nextInt(REQUEST_TYPE.length)]);
		logSB.append(" ");
		logSB.append(REQUEST_RESOURCE[random.nextInt(REQUEST_RESOURCE.length)]);
		logSB.append(" ");
		logSB.append(REQUEST_PROTOCOL[random.nextInt(REQUEST_PROTOCOL.length)]);
		logSB.append("\" ");
		logSB.append(HTTP_STATUS_CODES[random.nextInt(HTTP_STATUS_CODES.length)]);
		logSB.append(" ");
		logSB.append(random.nextInt(MAX_BYTES_LENGTH));
		logger.info(logSB.toString());
	}
}
