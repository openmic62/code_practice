public class LogAnalyzer
{
	private boolean lastFileNameValid;

	public boolean isLastFileNameValid() {
		return this.lastFileNameValid;
	}

	public boolean IsValidLogFileName(String fileName)
	{
		// Set property default
		lastFileNameValid = false;

		// first, make sure the fileName contains something
		if (fileName == null || fileName == "") {
			throw new IllegalArgumentException("filename has to be provided");
		}
		String suffix = fileName.substring(fileName.lastIndexOf('.'), fileName.length()).toLowerCase();
		if(!suffix.equals(".slf"))
		///if(!fileName.endsWith(".SLF"))
		{
			return false;
		}

		lastFileNameValid = true;
		return true;
	}
}