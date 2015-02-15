package auctionsniper.tests.acceptance;

import auctionsniper.xmpp.XMPPAuctionHouse;

import java.io.File;
import java.io.IOException;
import java.util.logging.LogManager;

import org.apache.commons.io.FileUtils;

import org.hamcrest.Matcher;

import static org.hamcrest.MatcherAssert.assertThat;

public class AuctionLogDriver  {
	//private final String LOG_FILE_NAME = "auction-sniper.log"; // <mlr 140315: moved to XMPPAuctionHouse per BV
	private final File logFile = new File(XMPPAuctionHouse.LOG_FILE_NAME);
	
	public void clearLog() {
		logFile.delete();
		LogManager.getLogManager().reset();
	}
	
	public void hasEntry(Matcher<String> matcher) throws IOException {
		assertThat(FileUtils.readFileToString(logFile), matcher);
	}
	
}