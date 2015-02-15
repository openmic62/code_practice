/**
 * Factory Method Injection - Example
 * <u>The Art of Unit Testing</u>
 * Section 3.4.6 Injecting a fake just before a method call [using a factory method]
 * FAKE METHOD - USE A LOCAL FACTORY METHOD (EXTRACT AND OVERRIDE)
 * Simulating input to the CUT (virtualized input ... factory method)
 * pdf-p. 77
 *
 */
public class LogAnalyzerFM
{
	// This memer remains unused in the example. I would consider instantiating
	// into this variable if I were using the manager a lot.
	private FileExtensionManagable manager;
	
	public boolean IsValidLogFileName(String fileName)
	{
		return getManager().isValid(fileName);
	}
	
	// Use this class as is in production. It becomes the CUT and
	// we create the seam here when we override in a test sub-class
	// with the objective of inputting our own manager.
	// We VIRTUALIZE this factory method.
	// (see LogAnalyzerFMTests)
	protected FileExtensionManagable getManager() {
		return new FileExtensionManager();
	}
}