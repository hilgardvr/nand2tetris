import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;

public class VMTranslator {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid number of arguments");
        	return;
        }
		try {
			FileReader fileReader = new FileReader(args[0]);
			BufferedReader reader = new BufferedReader(fileReader);
			List<String> file = new ArrayList<String>();
			String line = null;
			while ((line = reader.readLine()) != null) {
				file.add(line);
			}
			
			Parser parser = new Parser();
			parser.ParseFile(file, args[0]);			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
