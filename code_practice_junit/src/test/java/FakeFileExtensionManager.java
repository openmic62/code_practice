// <mlr 130816: begin>
// Moved class FakeFileExtensionManager implements FileExtensionManagable
// here to its own file so that I could reuse it in both CI and DI tests.
// <mlr 130816: end>
// J:\src\test\java>set CLASSPATH=..\junit-4.11.jar;..\hamcrest-core-1.3.jar
// J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes -d ..\..\..\target\test-classes FakeFileExtensionManager.java

// implement the file system dependency fake with an interface seam
public class FakeFileExtensionManager implements FileExtensionManagable {
	// provide future flexibility
	private boolean willBeValid = false;
	public void setWillBeValid(boolean b) {
		this.willBeValid = b;
	}

	// provide for exceptions that the manager might throw
	private RuntimeException willThrow = null;
	public void setWillThrow(String msg) {
		this.willThrow = new RuntimeException(msg);
	}

	// perform the fake ... this, to me, fails to test accurately. I need to think about it.
	// Off the top of my head, I think I need to emulate the file system behavior as accurately
	// as possible. But, does that get me into an impossible situation? Can I know
	// completely the API for the file system manager object? This, I believe, is where I
	// get the huge gain of unit testing. The issue I'm thinking about right here FORCES
	// me to clarify and define that API before hand.
	public boolean isValid(String fileName) {
		if (willThrow != null) {
			throw willThrow;
		}
		return this.willBeValid;
	}
	
	public void fuckYou(){}
}
