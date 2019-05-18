import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class VmWriter {
	private BufferedWriter writer;

	public VmWriter (String filePath) {
		String fileName = filePath.substring(0, filePath.lastIndexOf("."));
		try {
			writer = new BufferedWriter(new FileWriter(fileName + ".vm"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void WritePush(String segment, int index) {
		WriteLine("push " + segment + " " + Integer.toString(index));
	}

	public void WritePop(String segment, int index) {
		WriteLine("pop " + segment + " " + Integer.toString(index));
	}

	public void WriteArithmetic(String command) {
		WriteLine(command);
	}

	public void WriteLabel(String label) {
		WriteLine(label + ":");
	}

	public void WriteGoto(String label) {
	}

	public void WriteIf(String label) {
	}

	public void WriteCall(String name, int nArgs) {
	}

	public void WriteFunction(String name, int nLocals) {
	}
	
	public void WriteReturn() {
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

