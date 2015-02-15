/**
 * 1Z0-803 OCA Java SE 7 Study Guide<br>
 * Ch. 5, p. 201a<p>
 * Section 6, Item 4<br>
 * Differentiate between default and user-defined constructors<p>
 * Section 6, Item 5<br>
 * Create and overload constructors<p>
 * Section 7, Item 5<br>
 * Use <code>super</code> and <code>this</code> to access objects and constructors<p>
 * Illustrates: making a constructor, overloading a constructor, keyword this
 */
public class LoanDetails {
	/**
	 * number of interest cycles of a loan<p>
	 */
	private int term = 0;

	/**
	 * annual interest rate of a loan stored as a decimal value<p>
	 */
	private double rate = 0.0;

	/**
	 * principle amount of a loan<p>
	 */
	private double principal = 0.0;
	
	/**
	 * My explicit, public, no-arg constructor. Sets default values for term, 
	 * rate, and principal.<br>
	 * <ul><code>
	 *   <li>term      = 180</li>
	 *   <li>rate      = 0.0265</li>
	 *   <li>principal = 0</li>
	 * </code></ul>
	 */
	public LoanDetails() {
		super();
		this.term = 180;
		this.rate = 0.0265; // Interest rate as a decimal
		this.principal = 0;
	}
	
	/**
	 * This one-arg constructor shows how to call another constructor using 
	 * <code>this</code>. The one-arg simply changes the signature so that I 
	 * have another constructor to do the illustration. The constructor body
	 * calls a different constructor with a more detailed signature. The 
	 * idea is to maintain only that constructor, calling it with other
	 * constructors that pass in default values.<p>
	 * 1Z0-803 OCA Java SE 7 Study Guide<br>
	 * Ch. 5, p. 205a<p>
	 * @param dummyArg this arg simply changes the signature to give another 
	 * constructor for illustration.
	 */
	public LoanDetails(char dummyArg) {
		this(180, 0.0265, 0);
		System.out.println("After call to another constructor.");
	}
	
	/**
	 * This one-<code>String</code>-arg constructor shows how to call another method using 
	 * <code>this</code>. The constructor body
	 * calls a different method.<p>
	 * 1Z0-803 OCA Java SE 7 Study Guide<br>
	 * Ch. 5, p. 205b<p>
	 * @param dummyStrArg this arg simply changes the signature to give another 
	 * constructor for illustration.
	 */
	public LoanDetails(String dummyStrArg) {
		this.calledbByThis();
		System.out.println("After call to another method.");
	}
	
	/**
	 * My explicit, public, 3-arg constructor. Sets values for term, 
	 * rate, and principal.<p>
	 * This constructor illustrates overloading.
	 * @param t number of interest cycles for this loan<br> 
	 * @param r interest rate for this loan stored as a decimal value<br> 
	 * @param p principal amount for this loan<br> 
	 */
	public LoanDetails(int t, double r, double p) {
		super();
		term = t;
		rate = r; // Interest rate as a decimal
		principal = p;
		System.out.println("Another constructor (no this)");
	}
	
	/**
	 * The signature change of this constructor allows illustration of <code>this</code><p>
	 * @param term number of interest cycles for this loan<br> 
	 * @param rate interest rate for this loan stored as a decimal value<br> 
	 * @param principal principal amount for this loan<br> 
	 */
	public LoanDetails(int term, float rate, double principal) {
		super();
		this.term = term;
		this.rate = rate; // Interest rate as a decimal
		this.principal = principal;
		System.out.println("This constructor illustrates \"this\".");
	}
	
	/**
	 * @param principal the initial value of the loan
	 * @see "<a href="http://www.javaworld.com/javaworld/jw-09-2003/jw-0905-toolbox.html">Why getter and setter methods are evil</a>"
	 * @see "<a href="http://stackoverflow.com/questions/1028967/simple-getter-setter-comments">Simple Getter/Setter comments</a>"
	 */
	public void setPrincipal(double principal) {
		this.principal = principal;
	}
	
	/**
	 * Calculates the monthly payment for a loan using default
	 * values for <code>term</code> and <code>rate</code>. Please set the
	 * value of the intial loan amount before calling this method.
	 * @return the calculated monthly payment
	 */
	public double monthlyPayment() {
		return (rate * principal / 12) / (1.0 - Math.pow(((rate / 12) + 1.0), (-term)));
	}
	
	/**
	 * A simple method that illustrates using <code>this</code> to call
	 * it from within an instance of the class. The book states, "However,
	 * it is not often used."<p>
	 * 1Z0-803 OCA Java SE 7 Study Guide<br>
	 * Ch. 5, p. 205b<p>
	 */
	private void calledbByThis() {
		System.out.println("Executing in a method called by \"this.calledByThis\"");
	}
	 
}
