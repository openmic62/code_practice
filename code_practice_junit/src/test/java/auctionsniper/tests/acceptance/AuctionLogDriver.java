package auctionsniper.tests.acceptance;

import java.io.File;
import java.io.IOException;
import java.util.logging.LogManager;

import org.apache.commons.io.FileUtils;

import org.hamcrest.Matcher;

import static org.hamcrest.MatcherAssert.assertThat;

public class AuctionLogDriver  {
	private final String LOG_FILE_NAME = "auction-sniper.log";
	private final File logFile = new File(LOG_FILE_NAME);
	
	public void clearLog() {
		logFile.delete();
		LogManager.getLogManager().reset();
	}
	
	public void hasEntry(Matcher<String> matcher) throws IOException {
		assertThat(FileUtils.readFileToString(logFile), matcher);
	}
	
}