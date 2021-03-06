import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CompilationEngine {
	//writes xml code
	private BufferedWriter writer;
	private VmWriter vmWriter;
	private String className;
	private String methodName;
	private List<String> tokens;
	private List<SymbolTableItem> classSymbolTable;
	private List<SymbolTableItem> methodSymbolTable;
	private int index;
	private int indent;
	private int fieldIndex;
	private int staticIndex;
	private int argumentIndex;
	private int localIndex;
	private int ifIndex;
	private int whileIndex;
	private String subType;

	public void ParseTokens(List<String> tokenizedFile, String filePath) {
		this.tokens = tokenizedFile;
		this.index = 0;
		this.indent = 0;
		this.ifIndex = 0;
		this.whileIndex = 0;
		
		vmWriter = new VmWriter(filePath);
		String fileName = filePath.substring(0, filePath.lastIndexOf("."));
		try {
			writer = new BufferedWriter(new FileWriter(fileName + ".xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		HandleTokens();	
		CloseWriter();
		vmWriter.CloseWriter();
	}

	private void HandleTokens() {
		int tokensSize = this.tokens.size();
		//main loop over tokens 
		while ((this.index < tokensSize) && !getXmlTag(getCurrent()).equals("keyword")) {
			this.index++;
		}
		HandleKeyword();
	}

	private void HandleKeyword() {
		String tag = getInnerTag(this.tokens.get(this.index));
		String xml = getXmlTag(this.tokens.get(this.index));
		//check correct syntax
		if (!xml.equals("keyword")) {
			System.out.println("Expected keyword tab but got : " + getCurrent());
		}
		switch (tag) {
			case "class":
				CompileClass();
				break;
			default:
				System.out.println("Class not found");
				break;
		}
	}	

	private void CompileClass() {
		this.fieldIndex = 0;
		this.staticIndex = 0;
		this.classSymbolTable = new ArrayList<SymbolTableItem>();
		WriteLine("<class>");
		this.indent++;
		WriteLine(this.tokens.get(this.index));
		this.index++;
		//should find a className
		System.out.println("Class identifier: " + getInnerTag(getCurrent()));
		this.className = getInnerTag(getCurrent());
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
		for (int i = 0; i < this.classSymbolTable.size(); i++) {
			System.out.println(this.classSymbolTable.get(i).toString());
		}
		System.out.println("End of class -----------\n");
	}

	private void CompileSubroutineDec() {
		this.methodSymbolTable = new ArrayList<SymbolTableItem>();
		this.argumentIndex = 0;
		this.localIndex = 0;
		WriteLine("<subroutineDec>");
		this.indent++;
		//const | method | function
		this.subType = getInnerTag(getCurrent());
		WriteCurrent();
		this.index++;
		//void | type
		WriteCurrent();
		this.index++;
		//subroutine name
		System.out.println("Subroutine identifier: " + getInnerTag(getCurrent()));
		this.methodName = getInnerTag(getCurrent());
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
		vmWriter.WriteReturn();
		WriteLine("</subroutineDec>");
		for (int i = 0; i < this.methodSymbolTable.size(); i++) {
			System.out.println(this.methodSymbolTable.get(i).toString());
		}
		System.out.println("End of method -----------\n");
	}

	private void CompileParameterList() {
		WriteLine("<parameterList>");
		this.indent++;
		//add implicit this argument for methods
		if (this.subType.equals("method")) {
			methodSymbolTable.add(new SymbolTableItem("this", "argument", className, this.argumentIndex));
			this.argumentIndex++;
		}
		// if any parameters
		while (!getInnerTag(getCurrent()).equals(")")) {
			String name;
			String type;
			//type		
			WriteCurrent();
			type = getInnerTag(getCurrent());	
			this.index++;
			//name
			WriteCurrent();
			name = getInnerTag(getCurrent());
			this.index++;
			//add parameter
			methodSymbolTable.add(new SymbolTableItem(name, "argument", type, this.argumentIndex));
			//if ,
			if (getInnerTag(getCurrent()).equals(",")) {
				WriteCurrent();
				this.index++;
			}
			this.argumentIndex++;
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
		//count number of locals
		int ctr = 0;
		for (SymbolTableItem item : this.methodSymbolTable) {
			if (item.getCategory().equals("local")) {
				ctr++;
			}
		}
		vmWriter.WriteFunction(this.className + "." + this.methodName, ctr);
		if (this.subType.equals("constructor")) {
			vmWriter.WriteLine("//Creating object of type " + this.className);
			//handle constructor
			int classVars = 0;
			//get number of class variables
			for (SymbolTableItem item : this.classSymbolTable) {
				if (!item.getCategory().equals("static")) {	
					classVars++;
				}
			}
			//push number of class vars
			vmWriter.WritePush("constant", Integer.toString(classVars));	
			//alloc memory for the new object
			vmWriter.WriteCall("Memory.alloc", "1");
			//pop return address in this
			vmWriter.WritePop("pointer", "0");
		} else if (this.subType.equals("method")) {
			//push current object onto the stack
			vmWriter.WritePush("argument", "0");
			//pop into this
			vmWriter.WritePop("pointer", "0");
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
		String type = null;
		String name = null;
		WriteLine("<varDec>");
		this.indent++;
		//var
		WriteCurrent();
		this.index++;
		//type
		type = getInnerTag(getCurrent());
		WriteCurrent();
		this.index++;
		//name
		name = getInnerTag(getCurrent());
		WriteCurrent();
		this.index++;
		//add to symboltablde
		methodSymbolTable.add(new SymbolTableItem(name, "local", type, this.localIndex));
		this.localIndex++;
		while (!getInnerTag(getCurrent()).equals(";")) {
			//,
			if (getInnerTag(getCurrent()).equals(",")) {
				WriteCurrent();
				this.index++;
			}
			//name
			name = getInnerTag(getCurrent());
			WriteCurrent();
			this.index++;	
			//add to symboltable
			methodSymbolTable.add(new SymbolTableItem(name, "local", type, this.localIndex));
			this.localIndex++;
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
		String varName = getInnerTag(getCurrent());
		WriteCurrent();
		this.index++;
		//if array variable
		if (getInnerTag(getCurrent()).equals("[")) {
			//push array variable address
			vmWriter.WritePush(getVarSegment(varName), getVarIndex(varName));
			//[
			WriteCurrent();
			this.index++;
			//expression
			CompileExpression();
			//array address + expression
			vmWriter.WriteArithmetic("add");
			//]
			WriteCurrent();
			this.index++;
			//=
			WriteCurrent();
			this.index++;
			//expression
			CompileExpression();
			//pop result of expr2
			vmWriter.WritePop("temp", "0");
			//pop array pointer
			vmWriter.WritePop("pointer", "1");
			//push result op expr2
			vmWriter.WritePush("temp", "0");
			//pop into assigned array address
			vmWriter.WritePop("that", "0");
			//;
			WriteCurrent();
			this.index++;
			this.indent--;
			WriteLine("</letStatement>");
		} else {
			//=
			WriteCurrent();
			this.index++;
			//expression
			CompileExpression();
			//pop result into varname
			vmWriter.WritePop(getVarSegment(varName), getVarIndex(varName));
			//;
			WriteCurrent();
			this.index++;
			this.indent--;
			WriteLine("</letStatement>");
		}
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
		//negate result of expression
		vmWriter.WriteArithmetic("not");
		//generate labels
		String L1 = "iflabel." + this.methodName + Integer.toString(this.ifIndex);
		this.ifIndex++;
		String L2 = "iflabel." + this.methodName + Integer.toString(this.ifIndex);
		this.ifIndex++;
		//if true go to else block
		vmWriter.WriteIf(L1);
		//)
		WriteCurrent();
		this.index++;
		//{
		WriteCurrent();
		this.index++;
		//statements
		CompileStatements();
		//goto L2
		vmWriter.WriteGoto(L2);
		//}
		WriteCurrent();
		this.index++;
		//emit L1 label for else block
		vmWriter.WriteLabel(L1);
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
	 	//emit L2 label for after if statement
		vmWriter.WriteLabel(L2);
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
		//generate label for loop exit
		String L1 = "whilelabel." + this.methodName + Integer.toString(this.whileIndex);
		this.whileIndex++;
		//generate label for loop exit
		String L2 = "whilelabel." + this.methodName + Integer.toString(this.whileIndex);
		this.whileIndex++;
		//write L1 for loop entry
		vmWriter.WriteLabel(L1);
		//expression
		CompileExpression();
		//negate expression
		vmWriter.WriteArithmetic("not");
		//if true jump out of loop
		vmWriter.WriteIf(L2);
		//)
		WriteCurrent();
		this.index++;
		//{
		WriteCurrent();
		this.index++;
		//statements
		CompileStatements();
		//reevaluate loop condition
		vmWriter.WriteGoto(L1);
		//}
		WriteCurrent();
		//emite exit loop label
		vmWriter.WriteLabel(L2);
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
		//pop return value into temp 5
		vmWriter.WritePop("temp", "5");
		this.index++;
		this.indent--;
		WriteLine("</doStatement>");
	}

	//add local classname to to local sub call
	private void CompileSubroutineCall() {
		int nArgs = 0;
		//identifier
		WriteCurrent();
		String subName = getInnerTag(getCurrent());
		this.index++;
		//if subroutine()
		if (getInnerTag(getCurrent()).equals("(")) {
			//push this address
			vmWriter.WritePush("pointer", "0");
			nArgs++;
			//get classname
			subName = this.className + "." + subName;
			//(
			WriteCurrent();
			this.index++;
			//ExressionList
			nArgs += CompileExpressionList();		
		// identifier.subroutine()
		//if method - add this argument and add class name to call
		} else {
			//check if identifier is a variable
			if (getVarSegment(subName) != null) {
				//push var address
				vmWriter.WritePush(getVarSegment(subName), getVarIndex(subName));
				nArgs++;
				//add identifier class type
				subName = getVarType(subName);
			} 
			//.
			subName += getInnerTag(getCurrent());
			WriteCurrent();
			this.index++;
			//subroutine identifier
			WriteCurrent();
			subName += getInnerTag(getCurrent());
			this.index++;
			//(
			WriteCurrent();
			this.index++;
			//expressionlist
			nArgs += CompileExpressionList();
		}	
		//method name
		vmWriter.WriteCall(subName, Integer.toString(nArgs));	
		//)
		WriteCurrent();
		this.index++;
	}

	private int CompileExpressionList() {
		int counter = 0;
		WriteLine("<expressionList>");
		this.indent++;
		//check if arguments
		if (!getInnerTag(getCurrent()).equals(")")) {
			//expression
			CompileExpression();
			counter++;
		}
		//while more expressions
		while (!getInnerTag(getCurrent()).equals(")")) {
			//,
			WriteCurrent();
			this.index++;
			//expression
			CompileExpression();	
			counter++;
		}
		this.indent--;
		WriteLine("</expressionList>");
		return counter;
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
		} else {
			//push garbage as return value
			vmWriter.WriteLine("//pushing return value");
			vmWriter.WritePush("constant", "0");
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
		String cur = getInnerTag(getCurrent());
		while (cur.equals("+") || cur.equals("-") || cur.equals("*") 
				|| cur.equals("/") || cur.equals("&amp;") || cur.equals("|") 
				|| cur.equals("&lt;") || cur.equals("&gt;") || cur.equals("=")) {
			//Op
			WriteCurrent();
			this.index++;
			CompileTerm();
			switch (cur) {
				case "+":
					vmWriter.WriteArithmetic("add");
					break;
				case "-":
					vmWriter.WriteArithmetic("sub");
					break;
				case "*":
					vmWriter.WriteCall("Math.multiply", "2");
					break;
				case "/":
					vmWriter.WriteCall("Math.divide", "2");
					break;
				case "&amp;":
					vmWriter.WriteArithmetic("and");
					break;
				case "|":
					vmWriter.WriteArithmetic("or");
					break;
				case"&lt;":
					vmWriter.WriteArithmetic("lt");
					break;
				case "&gt;":
					vmWriter.WriteArithmetic("gt");
					break;
				case "=":
					vmWriter.WriteArithmetic("eq");
					break;
				default:
					vmWriter.WriteLine("Not found: " + cur);
					break;
			}
			cur = getInnerTag(getCurrent());
		}
		this.indent--;
		WriteLine("</expression>");
	}

	private void CompileTerm() {
		WriteLine("<term>");
		this.indent++;
		String xmlTag;
		if (this.index < this.tokens.size()) { 
			xmlTag = getXmlTag(getCurrent());
		} else {
			xmlTag = "END";
		}
		switch (xmlTag) {
			//sting constants
			case "stringConstant":
				String str = getInnerTag(getCurrent());
				int len = str.length();
				vmWriter.WritePush("constant", Integer.toString(len));
				vmWriter.WriteCall("String.new", "1");
				int ctr = 0;
				while (ctr < len) {
					int charVal = str.charAt(ctr);
					vmWriter.WritePush("constant", Integer.toString(charVal));
					vmWriter.WriteCall("String.appendChar", "2");
					ctr++;
				}
				this.index++;
				break;
			case "integerConstant":
				vmWriter.WritePush("constant", getInnerTag(getCurrent()));
				this.index++;
				break;
			//true, false, null, this
			case "keyword":
				WriteCurrent();
				String keywordValue = getInnerTag(getCurrent());
				switch (keywordValue) {
					case "true":
						vmWriter.WritePush("constant", "0");
						vmWriter.WriteArithmetic("not");
						break;
					case "false":
						vmWriter.WritePush("constant", "0");
						break;
					case "null":
						vmWriter.WritePush("null", "todo");
						break;
					case "this":
						vmWriter.WritePush("pointer", "0");
						break;
					default:
						vmWriter.WriteLine("// ------- not found --------> " + keywordValue);
						break;
				}
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
				//negate
				} else if (getInnerTag(getCurrent()).equals("-")) {
					vmWriter.WritePush("constant", "0");
					WriteCurrent();
					this.index++;
					CompileTerm();
					vmWriter.WriteArithmetic("sub");
				//uranary op
				} else {
					WriteCurrent();
					this.index++;
					CompileTerm();
					//do not on compiled term
					vmWriter.WriteArithmetic("not");
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
					String varName = getInnerTag(getCurrent());
					vmWriter.WritePush(getVarSegment(varName), getVarIndex(varName));	
					WriteCurrent();
					this.index++;
					if (getInnerTag(getCurrent()).equals("[")) {
						//[
						WriteCurrent();
						this.index++;
						CompileExpression();
						//array base + expr
						vmWriter.WriteArithmetic("add");
						//pop into pointer 1
						vmWriter.WritePop("pointer", "1");
						//push value of pointer 1
						vmWriter.WritePush("that", "0");
						//]
						WriteCurrent();
						this.index++;
					}
				}
				break;
			case "END":
			default:
				break;
		}
		this.indent--;
		WriteLine("</term>");
	}

	private void CompileClassVarDec() {
		String category;	 
		String type;
		String name;
		int symbolIndex = -1;
		WriteLine("<classVarDec>");
		this.indent++;
		//static | field
		category = getInnerTag(getCurrent()); 
		if (category.equals("static")) {
			symbolIndex = this.staticIndex;
			this.staticIndex++;
		} else if (category.equals("field")) {
			symbolIndex = this.fieldIndex;
			this.fieldIndex++;
		} else {
			System.out.println("-------> Unknown variable catogory: " + category + " <--------");
		}
		WriteCurrent();
		this.index++;
		// type
		type = getInnerTag(getCurrent());
		WriteCurrent();
		this.index++;
		// varName
		name = getInnerTag(getCurrent());
		WriteCurrent();
		this.index++;
		classSymbolTable.add(new SymbolTableItem(name, category, type, symbolIndex));	
		// if more than one variable declared in line
		while (getInnerTag(getCurrent()).equals(",")) {
			//,
			WriteCurrent();
			this.index++;
			//varName
			WriteCurrent();
			name = getInnerTag(getCurrent());
			this.index++;
			//increment symbol index
			if (category.equals("static")) {
				symbolIndex = this.staticIndex;
				this.staticIndex++;
			} else if (category.equals("field")) {
				symbolIndex = this.fieldIndex;
				this.fieldIndex++;
			} else {
				System.out.println("-------> Unknown variable catogory: " + category + " <--------");
			}
			//add symbol
			classSymbolTable.add(new SymbolTableItem(name, category, type, symbolIndex));	
		}
		WriteCurrent();
		this.index++;
		this.indent--;
		WriteLine("</classVarDec>");
	}	
	
	private String getVarSegment(String varName) {
		for (SymbolTableItem t : methodSymbolTable) {
			if (t.getName().equals(varName)) {
				return (t.getCategory());
			}
		}
		for (SymbolTableItem t : classSymbolTable) {
			if (t.getName().equals(varName)) {
				if (t.getCategory().equals("static")) {
					return ("static");
				} else {
					return ("this");
				}
			}
		} 
		return null;
	}

	private String getVarIndex(String varName) {
		for (SymbolTableItem t : methodSymbolTable) {
			if (t.getName().equals(varName)) {
				return (t.getIndex());
			}
		}
		for (SymbolTableItem t : classSymbolTable) {
			if (t.getName().equals(varName)) {
				return (t.getIndex());
			}
		}
		return null;
	}

	private String getVarType(String varName) {
		for (SymbolTableItem t : methodSymbolTable) {
			if (t.getName().equals(varName)) {
				return (t.getType());
			}
		}
		for (SymbolTableItem t : classSymbolTable) {
			if (t.getName().equals(varName)) {
				return (t.getType());
			}
		}
		return null;
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
