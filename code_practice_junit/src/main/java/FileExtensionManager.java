// The actual implementation of the file system manager dependency
// that LogAnalyzer* will use. We need this because LogAnalyzerDI
// sets this as the default dependency (used in production code).
public class FileExtensionManager implements FileExtensionManagable {
	
	public boolean isValid(String fileName) {
		return false;
	}

}