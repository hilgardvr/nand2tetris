package Parser;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

import CodeWriter.CodeWriter; 

public class Parser {

	private CodeWriter codeWriter;

	public Parser(String file) {
		codeWriter = new CodeWriter(file);
	}
		
	public void ParseFile(List<String> fileToParse, String fileName) {

		for (String line : fileToParse) {
			//ParseLine(line);
			int comment = line.indexOf("//");
			String newLine;
			if (comment != -1) {
				newLine = line.substring(0, comment).trim();
			} else {
				newLine = line.trim();
			}
			if (newLine.length() > 0) {
				String[] lineArr = newLine.split(" ");
				codeWriter.GenerateCode(lineArr, fileName);
			}
		}

	}

	public void InitVmMemory() {
		codeWriter.InitVmMemory();
	}

	private void ParseLine(String line) {
	}
	
	public void Close() {
		codeWriter.CloseWriter();
	}
}	
