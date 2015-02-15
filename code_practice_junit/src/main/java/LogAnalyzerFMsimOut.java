/**
 * Factory Method Simulated Result - Example
 * <u>The Art of Unit Testing</u>
 * Section 3.5.1 Using Extract and Override to create fake results
 * Simulating output from the CUT (virtualized result)
 * pdf-p. 81
 *
 */
public class LogAnalyzerFMsimOut
{
	// This is the method under test
	public boolean IsValidLogFileName(String fileName)
	{
		// Call our own "isValid()" in which we create the seam
		return this.isValid(fileName);
	}
	
	// Use this class as is in production. It becomes the CUT and
	// we create the seam here when we override in a test sub-class
	// with the objective of outputting our own "isValid()" method result.
	// (see LogAnalyzerFMsimOutTests)
	protected boolean isValid(String fileName) {
		// Instantiate the production file system interface and use it
		// to validate our fileName
		FileExtensionManagable mgr = new FileExtensionManager();
		return mgr.isValid(fileName);
	}
}