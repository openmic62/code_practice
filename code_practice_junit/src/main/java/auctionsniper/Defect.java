package auctionsniper;

class Defect extends RuntimeException {
	Defect() {}
	Defect(String message) {
		super(message);
	}
}