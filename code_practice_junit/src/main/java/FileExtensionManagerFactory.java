/**
 * Factory Class Injection - Example
 * <u>The Art of Unit Testing</u>
 * Section 3.4.6 Injecting a fake just before a method call [using a factory class]
 * pdf-p. 73
*
 */
public class FileExtensionManagerFactory
{
	private static FileExtensionManagable manager = null;
	
	// Factory method provides the seam
	public static FileExtensionManagable create() {
		if (manager != null) {
			// inject the fake if it exists
			return FileExtensionManagerFactory.manager; 
		}
	  // return actual implementation by default for use in the production code
	 	return new FileExtensionManager();
	}
	
	// helper for using the seam; called by test code
	public static void setManager(FileExtensionManagable mgr)
	{
		FileExtensionManagerFactory.manager = mgr;
	}
}