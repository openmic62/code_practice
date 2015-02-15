/**
 * Construction Injection - Example
 * <u>The Art of Unit Testing</u>
 * Section 3.4.3 Inject a fake at the constructor level (constructor injection)
 * pdf-p. 66
 *
 */
public class LogAnalyzerCI
{
	private FileExtensionManagable manager;
	
	// use the seam provided by the FileExtensionManagable interface. This
	// REQUIRES clients to provide explicitly the manager (whether actual or test)
	public LogAnalyzerCI(FileExtensionManagable mgr) {
		this.manager = mgr;
	}
		
	public boolean IsValidLogFileName(String fileName)
	{
		return manager.isValid(fileName);
	}
}