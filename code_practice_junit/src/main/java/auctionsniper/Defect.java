package auctionsniper;

public class Defect extends RuntimeException {
	Defect() {}
	Defect(String message) {
		super(message);
	}
}