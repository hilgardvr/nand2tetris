import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;


public class JackTokenizerSquareTest {
	@Test
	public void evaluateExpression() {
		JackTokenizer tokenizer = new JackTokenizer();
		List<String> testList = new ArrayList<String>();
		testList.add("if (x < 0) {");
		testList.add("	let state = \"negative\";");
		testList.add("}");
		List<String> correctList = new ArrayList<String>();
		correctList.add("<tokens>");
		correctList.add("<keyword> if </keyword>");
		correctList.add("<symbol> ( </symbol>");
		correctList.add("<identifier> x </identifier>");
		correctList.add("<symbol> &lt; </symbol>");
		correctList.add("<integerConstant> 0 </integerConstant>");
		correctList.add("<symbol> ) </symbol>");
		correctList.add("<symbol> { </symbol>");
		correctList.add("<keyword> let </keyword>");
		correctList.add("<identifier> state </identifier>");
		correctList.add("<symbol> = </symbol>");
		correctList.add("<stringConstant> negative </stringConstant>");
		correctList.add("<symbol> ; </symbol>");
		correctList.add("<symbol> } </symbol>");
		correctList.add("</tokens>");
		assertEquals(tokenizer.TokenizeFile(testList), correctList);
	}	

	@Test
	public void evaluateSuqareSquare() {
		ClassLoader classLoader = getClass().getClassLoader();
		File jackFile = new File(classLoader.getResource("Square/Square.jack").getFile());
		File xmlFile = new File(classLoader.getResource("Square/SquareT.xml").getFile());
		
		JackAnalyzer analyze = new JackAnalyzer();
		List<String> readFile = analyze.ReadFile(jackFile);
		List<String> testFile = new ArrayList<String>();
		
		try {
			FileReader fileReader = new FileReader(xmlFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = null;
			while ((line = reader.readLine()) != null) {
				testFile.add(line);
			}
			reader.close();
			fileReader.close();
		} catch (Exception e) {
			System.out.println("Error reading file in JackAnalyzerTest");
			e.printStackTrace();
		}
		assertEquals(readFile, testFile);
	}
	
	@Test
	public void evaluateSquareGame() {
		ClassLoader classLoader = getClass().getClassLoader();
		File jackFile = new File(classLoader.getResource("Square/SquareGame.jack").getFile());
		File xmlFile = new File(classLoader.getResource("Square/SquareGameT.xml").getFile());
		
		JackAnalyzer analyze = new JackAnalyzer();
		List<String> readFile = analyze.ReadFile(jackFile);
		List<String> testFile = new ArrayList<String>();
		
		try {
			FileReader fileReader = new FileReader(xmlFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = null;
			while ((line = reader.readLine()) != null) {
				testFile.add(line);
			}
			reader.close();
			fileReader.close();
		} catch (Exception e) {
			System.out.println("Error reading file in JackAnalyzerTest");
			e.printStackTrace();
		}
		assertEquals(readFile, testFile);
	}

	@Test
	public void evaluateSquareMain() {
		ClassLoader classLoader = getClass().getClassLoader();
		File jackFile = new File(classLoader.getResource("Square/Main.jack").getFile());
		File xmlFile = new File(classLoader.getResource("Square/MainT.xml").getFile());
		
		JackAnalyzer analyze = new JackAnalyzer();
		List<String> readFile = analyze.ReadFile(jackFile);
		List<String> testFile = new ArrayList<String>();
		
		try {
			FileReader fileReader = new FileReader(xmlFile);
			BufferedReader reader = new BufferedReader(fileReader);
			String line = null;
			while ((line = reader.readLine()) != null) {
				testFile.add(line);
			}
			reader.close();
			fileReader.close();
		} catch (Exception e) {
			System.out.println("Error reading file in JackAnalyzerTest");
			e.printStackTrace();
		}
		assertEquals(readFile, testFile);
	}	
}
