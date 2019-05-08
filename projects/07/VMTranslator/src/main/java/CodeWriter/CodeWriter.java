package CodeWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CodeWriter {
	
	static int eqCtr = 0;
	static int ltCtr = 0;
	static int grCtr = 0;
	static int pushCtr = 0;
	private BufferedWriter writer;
	
	public CodeWriter(String filePath) {
		String fileName = filePath.substring(0, filePath.lastIndexOf("."));
		try {
			writer = new BufferedWriter(new FileWriter(fileName + ".asm"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void GenerateCode(String[] line) {
		if (line.length == 1) {
			switch (line[0]) {
				case "add":
					DoAdd();
					break; 	
				case "sub":
					DoSub();
					break; 	
				case "neg":
					DoNeg();
					break; 	
				case "eq":
					DoEq();
					break; 	
				case "gt":
					DoGt();
					break; 	
				case "lt":
					DoLt();
					break; 	
				case "and":
					DoAnd();
					break; 	
				case "or":
					DoOr();
					break; 	
				case "not":
					DoNot();
					break; 	
				default:
					System.out.println("Arithmetic op not found: " + line[0]);
					break;
			} 
		} else if (line[0].equals("pop")) {
			DoPop(line);
		} else if (line[0].equals("push")) {
			DoPush(line);
		}
	}

	private void DoAdd() {
		WriteLine("//Adding");
		WriteLine("@SP");
		WriteLine("M=M-1");
		WriteLine("A=M");
		WriteLine("D=M");
		WriteLine("A=A-1");
		WriteLine("M=D+M");
	}
		
	private void DoSub() {
		WriteLine("//Subbing");
		WriteLine("@SP");
		WriteLine("M=M-1");
		WriteLine("A=M");
		WriteLine("D=M");
		WriteLine("A=A-1");
		WriteLine("M=M-D");
	}

	private void DoNeg() {
		WriteLine("//Negging");
		WriteLine("@SP");
		WriteLine("A=M-1");
		WriteLine("M=-M");
	}

	private void DoEq() {
		WriteLine("//Equals?");
		WriteLine("@SP");
		WriteLine("M=M-1");
		WriteLine("A=M");
		WriteLine("D=M");
		WriteLine("A=A-1");
		WriteLine("D=D-M");
		WriteLine("@DOEQEQUALS" + String.valueOf(eqCtr));
		WriteLine("D;JEQ");
		WriteLine("@DOEQNOTEQ" + String.valueOf(eqCtr));
		WriteLine("D;JNE");
		
		WriteLine("(DOEQNOTEQ" + String.valueOf(eqCtr) + ")");
		WriteLine("@SP");
		WriteLine("A=M-1");
		WriteLine("M=0");
		WriteLine("@DOEQCONT" + String.valueOf(eqCtr));
		WriteLine("0;JMP");
		
		WriteLine("(DOEQEQUALS" + String.valueOf(eqCtr) + ")");
		WriteLine("@SP");
		WriteLine("A=M-1");
		WriteLine("M=-1");
		
		WriteLine("(DOEQCONT" + String.valueOf(eqCtr) + ")");
		CodeWriter.eqCtr++;
	}

	private void DoGt() {
		WriteLine("//Greater?");
		
		WriteLine("@SP");
		WriteLine("M=M-1");
		WriteLine("A=M");
		WriteLine("D=M");
		WriteLine("A=A-1");
		WriteLine("D=M-D");
		WriteLine("@DOGTGT" + String.valueOf(grCtr));
		WriteLine("D;JGT");
		WriteLine("@DOGTNOTGT" + String.valueOf(grCtr));
		WriteLine("D;JLE");
		
		WriteLine("(DOGTGT" + String.valueOf(grCtr) + ")");
		WriteLine("@SP");
		WriteLine("A=M-1");
		WriteLine("M=-1");
		WriteLine("@DOGTCONT" + String.valueOf(grCtr));
		WriteLine("0;JMP");
		
		WriteLine("(DOGTNOTGT" + String.valueOf(grCtr) + ")");
		WriteLine("@SP");
		WriteLine("A=M-1");
		WriteLine("M=0");
		
		WriteLine("(DOGTCONT" + String.valueOf(grCtr) + ")");
		CodeWriter.grCtr++;
	}

	private void DoLt() {
		WriteLine("//Less?");

		WriteLine("@SP");
		WriteLine("M=M-1");
		WriteLine("A=M");
		WriteLine("D=M");
		WriteLine("A=A-1");
		WriteLine("D=M-D");
		WriteLine("@DOLTLT" + String.valueOf(ltCtr));
		WriteLine("D;JLT");
		WriteLine("@DOLTNOTLT" + String.valueOf(ltCtr));
		WriteLine("D;JGE");
		
		WriteLine("(DOLTLT" + String.valueOf(ltCtr) + ")");
		WriteLine("@SP");
		WriteLine("A=M-1");
		WriteLine("M=-1");
		WriteLine("@DOLTCONT" + String.valueOf(ltCtr));
		WriteLine("0;JMP");
		
		WriteLine("(DOLTNOTLT" + String.valueOf(ltCtr) + ")");
		WriteLine("@SP");
		WriteLine("A=M-1");
		WriteLine("M=0");
		
		WriteLine("(DOLTCONT" + String.valueOf(ltCtr) + ")");
		CodeWriter.ltCtr++;
	}
	
	private void DoAnd() {
		WriteLine("//Anding");
		WriteLine("@SP");
		WriteLine("M=M-1");
		WriteLine("A=M");
		WriteLine("D=M");
		WriteLine("A=A-1");
		WriteLine("M=D&M");
	}

	private void DoOr() {
		WriteLine("//Oring");
		WriteLine("@SP");
		WriteLine("M=M-1");
		WriteLine("A=M");
		WriteLine("D=M");
		WriteLine("A=A-1");
		WriteLine("M=D|M");
	}

	private void DoNot() {
		WriteLine("//Notting");
		WriteLine("@SP");
		WriteLine("A=M");
		WriteLine("A=A-1");
		WriteLine("M=!M");
	}
	
	private void DoPush(String[] line) {
		switch (line[1]) {
			case "constant":
				PushConstant(line[2]);	
				break;
			case "local":
			case "argument":
			case "this":
			case "that":
				PushFromMem(line[1], line[2]);
				break;
			case "temp":
				PushFromTemp(line[2]);
				break;
			case "pointer":
				PushFromPointer(line[2]);
				break;
			case "static":
				PushFromStatic(line[2]);
				break;
		}
	}

	private void DoPop(String[] line) {
		switch (line[1]) {
			case "local":
			case "argument":
			case "this":
			case "that":
				PopToMem(line[1], line[2]);
				break;
			case "temp":
				PopToTemp(line[2]);
				break;
			case "pointer":
				PopToPointer(line[2]);
				break;
			case "static":
				PopToStatic(line[2]);
				break;
		}
	}

	private void PushConstant(String val) {
		WriteLine("//Pushing constant " + val);
		WriteLine("@" + val);
		WriteLine("D=A");
		WriteLine("@SP");
		WriteLine("A=M");
		WriteLine("M=D");
		WriteLine("@SP");
		WriteLine("M=M+1");
	}
	
	private String WhereLookup(String where, String index) {
		switch (where) {
			case "local":
				return "LCL";
			case "argument":
				return "ARG";
			case "this":
				return "THIS";
			case "that":
				return "THAT";
		}
		return "*** Not found : " + where + " ***";
	}
	
	private void PushFromMem(String whereString, String index) {
		String where = WhereLookup(whereString, index);
		Integer i = Integer.parseInt(index);
		WriteLine("//Pushing from " + where + " " + index);
		WriteLine("@" + where);
		WriteLine("A=M");
		while (i > 0) {
			WriteLine("A=A+1");
			i--;
		}
		WriteLine("D=M");
		WriteLine("@SP");
		WriteLine("A=M");
		WriteLine("M=D");
		WriteLine("@SP");
		WriteLine("M=M+1");
	}

	private void PushFromTemp(String index) {
		Integer i = Integer.parseInt(index) + 5;
		String tempReg = String.valueOf(i);
		WriteLine("//Pushing from temp " + index);
		WriteLine("@" + tempReg);
		WriteLine("D=M");
		WriteLine("@SP");
		WriteLine("A=M");
		WriteLine("M=D");
		WriteLine("@SP");
		WriteLine("M=M+1");
	}

	private void PushFromPointer(String index) {
		WriteLine("//Pushing from pointer " + index);
		if (index.equals("0")) {
			WriteLine("@THIS");
		} else if (index.equals("1")) {
			WriteLine("@THAT");
		}
		WriteLine("D=M");
		WriteLine("@SP");
		WriteLine("A=M");
		WriteLine("M=D");
		WriteLine("@SP");
		WriteLine("M=M+1");
	}

	private void PushFromStatic(String index) {
		Integer i = Integer.parseInt(index) + 16;
		String staticReg = String.valueOf(i);
		WriteLine("//Pushing from static " + index);
		WriteLine("@" + staticReg);
		WriteLine("D=M");
		WriteLine("@SP");
		WriteLine("A=M");
		WriteLine("M=D");
		WriteLine("@SP");
		WriteLine("M=M+1");
	}

	private void PopToMem(String whereString, String index) {
		String where = WhereLookup(whereString, index);
		Integer i = Integer.parseInt(index);
		WriteLine("//Popping to " + where + " " + index);
		WriteLine("@SP");
		WriteLine("M=M-1");
		WriteLine("A=M");
		WriteLine("D=M");
		WriteLine("@" + where);
		WriteLine("A=M");
		while (i > 0) {
			WriteLine("A=A+1");
			i--;
		}
		WriteLine("M=D");
	}

	private void PopToTemp(String index) {
		Integer i = Integer.parseInt(index) + 5;
		String tempReg = String.valueOf(i);
		WriteLine("//Popping to temp " + index);
		WriteLine("@SP");
		WriteLine("M=M-1");
		WriteLine("A=M");
		WriteLine("D=M");
		WriteLine("@" + tempReg);
		WriteLine("M=D");
	}

	private void PopToPointer(String index) {
		WriteLine("//Popping to pointer " + index);
		WriteLine("@SP");
		WriteLine("M=M-1");
		WriteLine("A=M");
		WriteLine("D=M");
		if (index.equals("0")) {
			WriteLine("@THIS");
		} else if (index.equals("1")) {
			WriteLine("@THAT");
		}
		WriteLine("M=D");
	}

	private void PopToStatic(String index) {
		Integer i = Integer.parseInt(index) + 16;
		String staticReg = String.valueOf(i);
		WriteLine("//Popping to static " + index);
		WriteLine("@SP");
		WriteLine("M=M-1");
		WriteLine("A=M");
		WriteLine("D=M");
		WriteLine("@" + staticReg);
		WriteLine("M=D");
	}

	private void WriteLine(String line) {
		try {
			this.writer.write(line + "\n");
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
