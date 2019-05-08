package Parser;

import java.util.List;
import java.util.ArrayList;

import CodeWriter.CodeWriter; 

public class Parser {

	private CodeWriter codeWriter;
	
	public void ParseFile(List<String> fileToParse, String filePath) {
		codeWriter = new CodeWriter(filePath);

		for (String line : fileToParse) {
			ParseLine(line);
		}

		codeWriter.CloseWriter();
	}

	private void ParseLine(String line) {
		int comment = line.indexOf("//");
		String newLine;
		if (comment != -1) {
			newLine = line.substring(0, comment);
		} else {
			newLine = line;
		}
		if (newLine.length() > 0) {
			String[] lineArr = newLine.split(" ");
			codeWriter.GenerateCode(lineArr);
		}
	}
}	
