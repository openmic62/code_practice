// Create a seam through which to inject testing dependency
public interface FileExtensionManagable {
	
	boolean isValid(String fileName);  // real work or test stubbing

}