import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CodeWriter {
	
	static int eqCtr = 0;
	static int ltCtr = 0;
	static int grCtr = 0;
	static int pushCtr = 0;
	static int returnCtr = 0;
	private BufferedWriter writer;
	private String currentFile;	

	public CodeWriter(String file) {
		String fileName;
		if (file.indexOf('.') > 0) {
			fileName = file.substring(0, file.lastIndexOf("."));
		} else {
			fileName = file;
		}
		try {
			writer = new BufferedWriter(new FileWriter(fileName + ".asm"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void InitVmMemory() {
		String[] sysCall = {"call", "Sys.init", "0"};
		WriteLine("//Bootstrap code");
		WriteLine("@256");
		WriteLine("D=A");
		WriteLine("@SP");
		WriteLine("M=D");
		WriteLine("@300");
		WriteLine("D=A");
		WriteLine("@LCL");
		WriteLine("M=D");
		WriteLine("@400");
		WriteLine("D=A");
		WriteLine("@ARG");
		WriteLine("M=D");
		DoCall(sysCall);
	}

	public void GenerateCode(String[] line, String file) {
		this.currentFile = file;
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
			case "pop":
				DoPop(line);
				break;
			case "push":
				DoPush(line);
				break;
			case "label":
				DoLabel(line);
				break;
			case "goto":
				DoGoto(line);
				break;	
			case "if-goto":
				DoIfGoto(line);
				break;
			case "function":
				DoFunction(line);
				break;
			case "return":
				DoReturn();
				break;
			case "call":
				DoCall(line);
				break;
			default:
				System.out.println("Operation not found: " + line[0]);
				break;
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

	private void DoLabel(String[] line) {
		WriteLine("//Adding label " + line[1]);
		WriteLine("(" + line[1] + ")");
	}


	private void DoGoto(String[] line) {
		WriteLine("//Juming to " + line[1]);
		WriteLine("@" + line[1]);
		WriteLine("0;JMP");
	}

	private void DoIfGoto(String[] line) {
		WriteLine("//Conditional jump to " + line[1]);
		WriteLine("@SP");
		WriteLine("M=M-1");
		WriteLine("A=M");
		WriteLine("D=M");
		WriteLine("@" + line[1]);
		WriteLine("D;JNE"); //not equal?
	}	


	private void DoFunction(String[] line) {
		Integer i = Integer.parseInt(line[2]);
		Integer ctr = 0;
		WriteLine("//Function " + line[1]);
		WriteLine("(" + line[1] + ")");
		while (i > 0) {
			PushConstant("0");
			PopToMem("local", Integer.toString(ctr));
			PushFromMem("local", Integer.toString(ctr));
			ctr++;
			i--;
		}
	}

	private void DoReturn() {
		WriteLine("//Returning from function");
		WriteLine("//Saving return value");
		WriteLine("@LCL");
		WriteLine("D=M");
		WriteLine("@R15");
		WriteLine("M=D");
		WriteLine("@5");
		WriteLine("D=A");
		WriteLine("@R15");
		WriteLine("A=M-D");
		WriteLine("D=M");
		WriteLine("@R15");
		WriteLine("M=D");
		WriteLine("//Pushing return value to ARG");
		WriteLine("@SP");
		WriteLine("A=M-1");
		WriteLine("D=M");
		WriteLine("@ARG");
		WriteLine("A=M");
		WriteLine("M=D");
		WriteLine("//Resetting stackpointer");
		WriteLine("D=A+1");
		WriteLine("@SP");
		WriteLine("M=D");
		WriteLine("//Resetting memory after return");
		WriteLine("//Resetting THAT");
		WriteLine("@LCL");
		WriteLine("M=M-1");
		WriteLine("A=M");
		WriteLine("D=M");
		WriteLine("@THAT");
		WriteLine("M=D");
		WriteLine("//Resetting THIS");
		WriteLine("@LCL");
		WriteLine("M=M-1");
		WriteLine("A=M");
		WriteLine("D=M");
		WriteLine("@THIS");
		WriteLine("M=D");
		WriteLine("//Resetting ARG");
		WriteLine("@LCL");
		WriteLine("M=M-1");
		WriteLine("A=M");
		WriteLine("D=M");
		WriteLine("@ARG");
		WriteLine("M=D");
		WriteLine("//Resetting local");
		WriteLine("@LCL");
		WriteLine("M=M-1"); //points to saved local value
		WriteLine("A=M");
		WriteLine("D=M");
		WriteLine("@LCL");
		WriteLine("M=D"); // reset LCL to save value
		WriteLine("//Jumping to saved return location");
		WriteLine("@R15");
		WriteLine("A=M");
		WriteLine("0;JMP");
	}

	private void DoCall(String[] line) {
		Integer numArgs =  Integer.parseInt(line[2]);
		Integer lclOffset = numArgs + 5;
		WriteLine("//Calling function " + line[1]);
		WriteLine("@RETURNID_" + String.valueOf(returnCtr));
		WriteLine("D=A");
		WriteLine("@SP");
		WriteLine("A=M");
		WriteLine("M=D");	//pushing return address
		WriteLine("@SP");
		WriteLine("M=M+1");
		
		WriteLine("@LCL");
		WriteLine("D=M");
		WriteLine("@SP");
		WriteLine("A=M");
		WriteLine("M=D");	//saving LCL
		WriteLine("@SP");
		WriteLine("M=M+1");
		WriteLine("@ARG");
		WriteLine("D=M");
		WriteLine("@SP");
		WriteLine("A=M");
		WriteLine("M=D");	//saving ARG
		WriteLine("@SP");
		WriteLine("M=M+1");
		WriteLine("@THIS");
		WriteLine("D=M");
		WriteLine("@SP");
		WriteLine("A=M");
		WriteLine("M=D");	//saving THIS
		WriteLine("@SP");
		WriteLine("M=M+1");
		WriteLine("@THAT");
		WriteLine("D=M");
		WriteLine("@SP");
		WriteLine("A=M");
		WriteLine("M=D");	//saving THAT
		WriteLine("@SP");
		WriteLine("M=M+1");
		
		//setting ARG for to function arguments
		WriteLine("D=M");
		WriteLine("@ARG");
		WriteLine("M=D");
		while (lclOffset > 0) {
			WriteLine("M=M-1");
			lclOffset--;
		}

		//setting LCL to function local args
		WriteLine("@SP");
		WriteLine("D=M");
		WriteLine("@LCL");
		WriteLine("M=D");

		//making function call
		WriteLine("@" + line[1]);
		WriteLine("0;JMP");

		//adding return label
		WriteLine("(RETURNID_" + String.valueOf(returnCtr) + ")");
		CodeWriter.returnCtr++;	
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
		//Integer i = Integer.parseInt(index) + 16;
		//String staticReg = String.valueOf(i);
		WriteLine("//Pushing from static " + index);
		//WriteLine("@" + staticReg);
		WriteLine("@" + currentFile + "." + index);
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
		//Integer i = Integer.parseInt(index) + 16;
		//String staticReg = String.valueOf(i);
		WriteLine("//Popping to static " + index);
		WriteLine("@SP");
		WriteLine("M=M-1");
		WriteLine("A=M");
		WriteLine("D=M");
		//WriteLine("@" + staticReg);
		WriteLine("@" + currentFile + "." + index);
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
