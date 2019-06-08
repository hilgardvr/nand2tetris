import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class JackCompiler {

	public List<String> ReadFile(File fileToRead) {
		System.out.println("Analyzer and tokenizer starting with " + fileToRead);
		JackTokenizer tokenizer = new JackTokenizer();
		List<String> file = new ArrayList<String>();
		try {
			FileReader fileReader = new FileReader(fileToRead);
			BufferedReader reader = new BufferedReader(fileReader);

			String line = null;
			while ((line = reader.readLine()) != null) {
				file.add(line);
			}

			reader.close();
			fileReader.close();
		} catch (Exception e) {
			System.out.println("Error reading file " + fileToRead);
			e.printStackTrace();
		}
		System.out.println("Analyzer and tokenizer done with " + fileToRead);
		return (tokenizer.TokenizeFile(file));
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Invalid number of arguments");
			return;
		}
		
		File fileOrDir = new File(args[0]);
		JackCompiler jackizer = new JackCompiler();
		CompilationEngine engine = new CompilationEngine();
		List<String> tokens = new ArrayList<String>();
	
		//it's a directory		
		if (fileOrDir.exists() && fileOrDir.isDirectory()) {
			System.out.println("Looking for .jack files inside directory " + args[0]);
			
			File[] dirFiles = fileOrDir.listFiles();
			if (dirFiles != null) {
				for (File child : dirFiles) {
					if (child.getName().indexOf('.') > 0) {
						String fileExtention = child.getName().substring(child.getName().lastIndexOf("."));
						if (fileExtention.equals(".jack")) {
							System.out.println("Generating code for: " + child);
							tokens = (jackizer.ReadFile(child));
							engine.ParseTokens(tokens, child.getAbsolutePath());
						}
					}
				}
			}
		//its a single file
		} else if (fileOrDir.exists()) {
			System.out.println("Reading and parsing single file: " + fileOrDir.getName());
			
			if (fileOrDir.getName().indexOf('.') > 0) {
				String fileExtention = fileOrDir.getName().substring(fileOrDir.getName().lastIndexOf("."));
				if (fileExtention.equals(".jack")) {
					System.out.println("Generating code for: " + args[0]);
					tokens.addAll(jackizer.ReadFile(fileOrDir));
					engine.ParseTokens(tokens, fileOrDir.getAbsolutePath());
				}
			}
		} else {
			System.out.println("Failed to find file or directory");
		}
		return;
	}
}
