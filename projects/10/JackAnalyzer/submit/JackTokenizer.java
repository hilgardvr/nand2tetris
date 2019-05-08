import java.util.List;
import java.util.ArrayList;
import java.lang.*;

public class JackTokenizer {

	private Boolean CheckForKeyword(String token) {
		switch (token) {
			case "class":
			case "constructor":
			case "function":
			case "method":
			case "field":
			case "static":
			case "var":
			case "int":
			case "char":
			case "boolean":
			case "void":
			case "true":
			case "false":
			case "null":
			case "this":
			case "that":
			case "let":
			case "do":
			case "if":
			case "else":
			case "while":
			case "return":
				return true;
			default:
				return false;
		}
	}


	private List<String> TokenizeItems(List<String> file) {
		
		List<String> tokens = new ArrayList<String>();
		tokens.add("<tokens>");		
		for (String line : file) {
			
			String token = "";
			int lineLength = line.length();
			int ctr = 0;

			while (ctr < lineLength) {
				if (Character.isLetter(line.charAt(ctr)) || line.charAt(ctr) == '_') {
					while (Character.isLetter(line.charAt(ctr)) || line.charAt(ctr) == '_') {
						token += line.charAt(ctr);
						ctr++;
					}
					if (CheckForKeyword(token)) {
						token = "<keyword> " + token + " </keyword>";
					} else {
						token = "<identifier> " + token + " </identifier>";
					}
					tokens.add(token);
					token = "";
				}
				if ((int)line.charAt(ctr) >= 48 && (int)line.charAt(ctr) <= 57) {
					while ((int)line.charAt(ctr) >= 48 && (int)line.charAt(ctr) <= 57) {
						token += line.charAt(ctr);
						ctr++;
					}
					token = "<integerConstant> " + token + " </integerConstant>";
					tokens.add(token);
					token = "";
				}
				if (line.charAt(ctr) == '\"') {
					ctr++;
					while (line.charAt(ctr) != '\"' && ctr < lineLength) {
						token += line.charAt(ctr);
						ctr++;
					}
					token = "<stringConstant> " + token + " </stringConstant>";
					tokens.add(token);
					token = "";
					ctr++;
				}
				if (line.charAt(ctr) == '{' || line.charAt(ctr) == '}' || line.charAt(ctr) == '[' || line.charAt(ctr) == ']' || line.charAt(ctr) == '(' || line.charAt(ctr) == ')'
					|| line.charAt(ctr) == '.' || line.charAt(ctr) == ',' || line.charAt(ctr) == ';' || line.charAt(ctr) == '+' || line.charAt(ctr) == '-' || line.charAt(ctr) == '*'
					|| line.charAt(ctr) == '/' || line.charAt(ctr) == '|' || line.charAt(ctr) == '=' || line.charAt(ctr) == '~') {
					token += line.charAt(ctr);
					token = "<symbol> " + token + " </symbol>";
					tokens.add(token);
					token = "";
					ctr++;
				} else if (line.charAt(ctr) == '&') {
					tokens.add("<symbol> &amp; </symbol>");
					ctr++;
				} else if (line.charAt(ctr) == '<') {
					tokens.add("<symbol> &lt; </symbol>");
					ctr++;
				} else if (line.charAt(ctr) == '>') {
					tokens.add("<symbol> &gt; </symbol>");
					ctr++;
				}
				if (line.charAt(ctr) == ' ' || line.charAt(ctr) == '\n') { 
					ctr++;
				}
			}
		}
		tokens.add("</tokens>");		
		return tokens;
	}

	private List<String> RemoveSingleComments(List<String> file) {
		
		List<String> exFile = new ArrayList<String>();
		
		for (String line : file) {
			int comment = line.indexOf("//");
			if (comment != -1) {
				String newLine = line.substring(0, comment).trim();
				if (newLine.length() > 0)
					exFile.add(newLine.trim() + "\n");
			} else {
				String newLine = line.trim();
				if (newLine.length() > 0)
					exFile.add(line.trim() + "\n");
			}
		}
		return exFile;
	}

	private List<String> RemoveMultiLineComments(List<String> file) {

		List<String> exFile = new ArrayList<String>();
		boolean multiBool = false;

		for (String line : file) {
			String newLine;
			int multiLine = line.indexOf("/*");

			if (!multiBool && multiLine > -1) {
				newLine = line.substring(0, multiLine).trim();
				multiBool = true;
				if (newLine.length() > 0)
					exFile.add(newLine + "\n");
			}	
			if (multiBool) {
				int endComment = line.indexOf("*/");
				if (endComment == -1) {
					continue;
				} else {
					newLine = line.substring(endComment + 2).trim();
					if (newLine.length() > 0)
						exFile.add(newLine + "\n");	
					multiBool = false;
				}
			} else {
				if (line.trim().length() > 0)
					exFile.add(line.trim() + "\n");
			}
		}
		return exFile;
	}


	public List<String> TokenizeFile(List<String> file) {
		return (TokenizeItems (RemoveSingleComments (RemoveMultiLineComments(file))));
	}
}
