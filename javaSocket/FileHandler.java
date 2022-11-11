package javaSocket;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileHandler
{
	private String fileName;

	public FileHandler(String _filename)
	{
		this.fileName = _filename;
	}

	String getStringFromFile() throws IOException
	{
		//https://stackoverflow.com/questions/326390/how-do-i-create-a-java-string-from-the-contents-of-a-file
		String content = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
		return content;
	}

	public List<String> getFileDataAsList() throws IOException
	{
		List<String> splittedStringList = new ArrayList<String>();
		final int charLenPerRequest = 100;
		try {
			String content = getStringFromFile();
			for(int i = 0; i < content.length(); i+=charLenPerRequest) {
				int startIndex = i*charLenPerRequest;
				int endIndex = Math.min(content.length()-1, (i+1)*charLenPerRequest-1 );
				String splilttedString = content.substring(startIndex, endIndex);
				splittedStringList.add(splilttedString);
			}
		}
		catch(Error e) {
			e.printStackTrace();
		}
		return splittedStringList;
	}

	public String getTextFormStringList(List<String> splittedStringList)
	{
		String outputString = splittedStringList.stream().map(Object::toString).collect(Collectors.joining(""));
		return outputString;
	}
}