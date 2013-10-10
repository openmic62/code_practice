public void testMultiplication() {
	Dollar five = new Dollar();
	five.times(2);
	assertEquals(10, five.amount);
}