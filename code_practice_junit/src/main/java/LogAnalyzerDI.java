/**
 * Dependency Injection - Example
 * <u>The Art of Unit Testing</u>
 * Section 3.4.5 Injecting a fake as a property get or set [dependency injection]
 * pdf-p. 71
*
 */
public class LogAnalyzerDI
{
	private FileExtensionManagable manager;
	
	// make manager a property to enable DI by tests
	public FileExtensionManagable getManager() {
		return this.manager;
	}
	public void setManager(FileExtensionManagable mgr) {
		this.manager = mgr;  // this seam is where DI occurs as called by LogAnalyzerDITests
	}
	
	// CUT uses actual implementation by default
	public LogAnalyzerDI() {
		this.manager = new FileExtensionManager(); 
	}
		
	public boolean IsValidLogFileName(String fileName)
	{
		return manager.isValid(fileName);
	}
}