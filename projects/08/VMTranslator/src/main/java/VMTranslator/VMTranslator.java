package VMTranslator;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

import Parser.Parser;
import CodeWriter.CodeWriter;

public class VMTranslator {

	Parser parser;
	
	private VMTranslator(String file) {
		parser = new Parser(file);
	}

	private void InitMemory() {
		System.out.println("Directory passed as argument - initialising VM memory");
		parser.InitVmMemory();
	}

	private void ReadAndParseFile(File fileToRead) {
		try {
			FileReader fileReader = new FileReader(fileToRead);
			BufferedReader reader = new BufferedReader(fileReader);

			List<String> file = new ArrayList<String>();
			String line = null;
			while ((line = reader.readLine()) != null) {
				file.add(line);
			}

			parser.ParseFile(file, fileToRead.getName());
			reader.close();
			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Invalid number of arguments");
			return;
		}
		
		File fileOrDir = new File(args[0]);
		
		if (fileOrDir.exists() && fileOrDir.isDirectory()) {
			
			VMTranslator vmTranslate = new VMTranslator(fileOrDir.getPath() + "/" + fileOrDir.getName());
			File[] dirFiles = fileOrDir.listFiles();
			if (dirFiles != null) {
				vmTranslate.InitMemory();
				for (File child : dirFiles) {
					if (child.getName().indexOf('.') > 0) {
						String fileExtention = child.getName().substring(child.getName().lastIndexOf("."));
						if (fileExtention.equals(".vm")) {
							System.out.println("Generating code for: " + child.getName());
							vmTranslate.ReadAndParseFile(child);
						}
					}
				}
			}
			vmTranslate.parser.Close();
		} else if (fileOrDir.exists()) {
			System.out.println("Reading and parsing single file: " + fileOrDir.getName());
			
			VMTranslator vmTranslate = new VMTranslator(fileOrDir.getPath());
			if (fileOrDir.getName().indexOf('.') > 0) {
				String fileExtention = fileOrDir.getName().substring(fileOrDir.getName().lastIndexOf("."));
				if (fileExtention.equals(".vm")) {
					vmTranslate.ReadAndParseFile(fileOrDir);
				}
			}
			vmTranslate.parser.Close();
		}
	}
}
