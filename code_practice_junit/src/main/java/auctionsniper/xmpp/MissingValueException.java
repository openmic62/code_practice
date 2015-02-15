package auctionsniper.xmpp;

public class MissingValueException extends RuntimeException {
	public MissingValueException() {}
	public MissingValueException(String fieldName) {
		super(String.format("Sniper's auction message omitted a field: -->%s<--", fieldName));
	}
}