import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CompilationEngine {
	private BufferedWriter writer;
	private String currentFile;	
	private List<String> tokens;
	private int index;
	private int indent;

	public void ParseTokens(List<String> tokenizedFile, String filePath) {
		this.tokens = tokenizedFile;
		//String fileName = filePath.substring(0, filePath.lastIndexOf("."));
		int last = filePath.lastIndexOf("/") == -1 ? 0 : filePath.lastIndexOf("/") + 1;
		String fileName = filePath.substring(last, filePath.lastIndexOf("."));
		try {
			writer = new BufferedWriter(new FileWriter(fileName + ".xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		HandleTokens();	
		CloseWriter();
		this.tokens = null;
	}

	private void HandleTokens() {
		int tokensSize = this.tokens.size();
		//main loop over tokens 
		while (this.index < tokensSize) {
			String xml = getXmlTag(this.tokens.get(index));
			switch (xml) {
				case "keyword": {
					HandleKeyword();
					break;
				}
				default: {
					System.out.println(xml+ " not yet implemented........");
					break;
				}
			}
			this.index++;
		}
	}

	private void HandleKeyword() {
		String tag = getInnerTag(this.tokens.get(this.index));
		String xml = getXmlTag(this.tokens.get(this.index));
		//check correct syntax
		if (!xml.equals("keyword")) {
			System.out.println("Expected keyword tab but got : " + getCurrent());
		}
		System.out.println("Handling: **" + getInnerTag(this.tokens.get(index)) + "**");
		switch (tag) {
			case "class":
				CompileClass();
				break;
			default:
				break;
		}
			
				
	}	

	private void CompileClass() {
		WriteLine("<class>");
		this.indent++;
		WriteLine(this.tokens.get(this.index));
		this.index++;
		//should find a className
		WriteCurrent();
		this.index++;

		//should be a {
		WriteCurrent();
		this.index++;

		//classVarDec*
		while (getInnerTag(getCurrent()).equals("field") || getInnerTag(getCurrent()).equals("static")) {
			CompileClassVarDec();
		}	
	
		//subroutineDec*
		while (getInnerTag(getCurrent()).equals("constructor") || getInnerTag(getCurrent()).equals("function") || getInnerTag(getCurrent()).equals("method")) {
			CompileSubroutineDec();
		}

		//}
		WriteCurrent();
		this.index++;
		this.indent--;
		WriteLine("</class>");
	}

	private void CompileSubroutineDec() {
		WriteLine("<subroutineDec>");
		this.indent++;
		//const | method | function
		WriteCurrent();
		this.index++;
		//void | type
		WriteCurrent();
		this.index++;
		//subroutine name
		WriteCurrent();
		this.index++;
		//(
		WriteCurrent();
		this.index++;
		//parameter list
		CompileParameterList();
		//)
		WriteCurrent();
		this.index++;
		CompileSubroutineBody();
		this.indent--;
		WriteLine("</subroutineDec>");
	}

	private void CompileParameterList() {
		WriteLine("<parameterList>");
		this.indent++;
		// if any parameters
		while (!getInnerTag(getCurrent()).equals(")")) {
			WriteCurrent();
			this.index++;
		}
		this.indent--;
		WriteLine("</parameterList>");
	}

	private void CompileSubroutineBody() {
		WriteLine("<subroutineBody>");
		this.indent++;
		//{
		WriteCurrent();
		this.index++;
		//var declaration if and while
		while (getInnerTag(getCurrent()).equals("var")) {
			CompileVarDec();
		}
		//statements
		CompileStatements();
		//}
		WriteCurrent();
		this.index++;		
		this.indent--;
		WriteLine("</subroutineBody>");
	}

	private void CompileVarDec() {
		WriteLine("<varDec>");
		this.indent++;
		while (!getInnerTag(getCurrent()).equals(";")) {
			WriteCurrent();
			this.index++;
		}
		//;
		WriteCurrent();
		this.index++;
		this.indent--;
		WriteLine("</varDec>");
	}

	private void CompileStatements() {
		WriteLine("<statements>");
		this.indent++;
		//while there are statments
		String statement = getInnerTag(getCurrent());
		while (statement.equals("let") || statement.equals("if") 
			|| statement.equals("while") || statement.equals("do")
			|| statement.equals("return")) {
			switch (statement) {
				case "let":
					CompileLet();
					break;
				case "if":
					CompileIf();
					break;
				case "while":
					CompileWhile();
					break;
				case "do":
					CompileDo();
					break;
				case "return":
					CompileReturn();
					break;
			}
			statement = getInnerTag(getCurrent());
		}
		this.indent--;
		WriteLine("</statements>");
	}	

	private void CompileLet() {
		WriteLine("<letStatement>");
		this.indent++;
		//let
		WriteCurrent();
		this.index++;
		//varName
		WriteCurrent();
		this.index++;
		//if array variable
		if (getInnerTag(getCurrent()).equals("[")) {
			//[
			WriteCurrent();
			this.index++;
			//expression
			CompileExpression();
			//]
			WriteCurrent();
			this.index++;
		}
		//=
		WriteCurrent();
		this.index++;
		//expression
		CompileExpression();
		//;
		WriteCurrent();
		this.index++;
		this.indent--;
		WriteLine("</letStatement>");
	}

	private void CompileIf() {
		WriteLine("<ifStatement>");
		this.indent++;
		//if
		WriteCurrent();
		this.index++;
		//(
		WriteCurrent();
		this.index++;
		//expression
		CompileExpression();
		//)
		WriteCurrent();
		this.index++;
		//{
		WriteCurrent();
		this.index++;
		//statements
		CompileStatements();
		//}
		WriteCurrent();
		this.index++;
		//else statement
		if (getInnerTag(getCurrent()).equals("else")) {
			//else
			WriteCurrent();
			this.index++;
			//{
			WriteCurrent();
			this.index++;
			//statements
			CompileStatements();
			//}
			WriteCurrent();
			this.index++;
		}
		this.indent--;
		WriteLine("</ifStatement>");
	}

	private void CompileWhile() {
		WriteLine("<whileStatement>");
		this.indent++;
		//while
		WriteCurrent();
		this.index++;
		//(
		WriteCurrent();
		this.index++;
		//expression
		CompileExpression();
		//)
		WriteCurrent();
		this.index++;
		//{
		WriteCurrent();
		this.index++;
		//statements
		CompileStatements();
		//}
		WriteCurrent();
		this.index++;
		this.indent--;
		WriteLine("</whileStatement>");
	}

	private void CompileDo() {
		WriteLine("<doStatement>");
		this.indent++;
		//do
		WriteCurrent();
		this.index++;
		//subroutineCall
		CompileSubroutineCall();
		//;
		WriteCurrent();
		this.index++;
		this.indent--;
		WriteLine("</doStatement>");
	}

	//todo
	private void CompileSubroutineCall() {
		//WriteLine("<subroutineCall>");
		//this.indent++;
		//identifier
		WriteCurrent();
		this.index++;
		//if subroutine()
		if (getInnerTag(getCurrent()).equals("(")) {
			//(
			WriteCurrent();
			this.index++;
			//ExressionList
			CompileExpressionList();		
		// identifier.subroutine()
		} else {
			//.
			WriteCurrent();
			this.index++;
			//subroutine identifier
			WriteCurrent();
			this.index++;
			//(
			WriteCurrent();
			this.index++;
			//expressionlist
			CompileExpressionList();
		}	
		//)
		WriteCurrent();
		this.index++;
		//this.indent--;
		//WriteLine("</subroutineCall>");
	}

	private void CompileExpressionList() {
		WriteLine("<expressionList>");
		this.indent++;
		//check if arguments
		if (!getInnerTag(getCurrent()).equals(")")) {
			//expression
			CompileExpression();
		}
		//while more expressions
		while (!getInnerTag(getCurrent()).equals(")")) {
			//,
			WriteCurrent();
			this.index++;
			//expression
			CompileExpression();	
		}
		this.indent--;
		WriteLine("</expressionList>");
	}

	
	private void CompileReturn() {
		WriteLine("<returnStatement>");
		this.indent++;
		//return
		WriteCurrent();
		this.index++;
		//if return value
		if (!getInnerTag(getCurrent()).equals(";")) {
			//expression
			CompileExpression();
		}
		//;
		WriteCurrent();
		this.index++;
		this.indent--;
		WriteLine("</returnStatement>");
	}

	private void CompileExpression() {
		WriteLine("<expression>");
		this.indent++;
		//term
		CompileTerm();
		while (!getXmlTag(getCurrent()).equals("symbol")) {
			//Op
			WriteCurrent();
			this.index++;
			CompileTerm();
		}
		this.indent--;
		WriteLine("</expression>");
	}

	private void CompileTerm() {
		WriteLine("<term>");
		this.indent++;
		String xmlTag = getXmlTag(getCurrent());
		switch (xmlTag) {
			//sting constants
			case "stringConstant":
			case "integerConstant":
			//true, false, null, this
			case "keyword":
				WriteCurrent();
				this.index++;
				break;
			//(expr) or uranaryop
			case "symbol":
				//( expr )
				if (getInnerTag(getCurrent()).equals("(")) {
					WriteCurrent();
					this.index++;
					CompileExpression();
					//)
					WriteCurrent();
					this.index++;
				//uranary op
				} else {
					WriteCurrent();
					this.index++;
					CompileTerm();
				}
				break;
			//var, var[expr], subRoutine
			case "identifier":
				//check for subroutine call
				if (getInnerTag(this.tokens.get(this.index + 1)).equals("(")
				|| getInnerTag(this.tokens.get(this.index + 1)).equals(".")) {
					CompileSubroutineCall();
				//var or var[expr]
				} else {
					WriteCurrent();
					this.index++;
					if (getInnerTag(getCurrent()).equals("[")) {
						//[
						WriteCurrent();
						this.index++;
						CompileExpression();
						//]
						WriteCurrent();
						this.index++;
					}
				}
				break;
		}
		this.indent--;
		WriteLine("</term>");
	}

	private void CompileClassVarDec() {
		WriteLine("<classVarDec>");
		this.indent++;
		//static | field
		WriteCurrent();
		this.index++;
		// type
		WriteCurrent();
		this.index++;
		// varName
		WriteCurrent();
		this.index++;
		// if more than one variable declared in line
		while (getInnerTag(getCurrent()).equals(",")) {
			WriteCurrent();
			this.index++;
			WriteCurrent();
			this.index++;
		}
		WriteCurrent();
		this.index++;
		this.indent--;
		WriteLine("</classVarDec>");
	}	

	private String getXmlTag(String token) {
		return token.substring(token.indexOf("<") + 1, token.indexOf(">"));
	}

	private String getInnerTag(String token) {
		return token.substring(token.indexOf(">") + 2, token.lastIndexOf("<") - 1);
	}

	private String getCurrent() {
		return this.tokens.get(this.index);
	}

	private void WriteLine(String line) {
		try {
			for (int ctr = 0; ctr < this.indent; ctr++) {
				this.writer.write("  ");
			}
			this.writer.write(line + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void WriteCurrent() {
		try {
			for (int ctr = 0; ctr < this.indent; ctr++) {
				this.writer.write("  ");
			}
			this.writer.write(this.tokens.get(this.index) + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void CloseWriter() {
		try {
			this.writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}	
