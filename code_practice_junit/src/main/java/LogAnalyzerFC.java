/**
 * Factory Class Injection - Example
 * <u>The Art of Unit Testing</u>
 * Section 3.4.6 Injecting a fake just before a method call [using a factory class]
 * pdf-p. 73
*
 */
public class LogAnalyzerFC
{
	private FileExtensionManagable manager;
	
	// CUT uses actual or test implementation as returned by the exteranlly configured factory
	public LogAnalyzerFC() {
		this.manager = FileExtensionManagerFactory.create(); 
	}
		
	public boolean IsValidLogFileName(String fileName)
	{
		return manager.isValid(fileName);
	}
}