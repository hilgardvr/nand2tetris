import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;


public class JackTokenizerExpressionLessSquareTest {
	@Test
	public void evaluateSuqareSquare() {
		ClassLoader classLoader = getClass().getClassLoader();
		File jackFile = new File(classLoader.getResource("ExpressionLessSquare/Square.jack").getFile());
		File xmlFile = new File(classLoader.getResource("ExpressionLessSquare/SquareT.xml").getFile());
		
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
		File jackFile = new File(classLoader.getResource("ExpressionLessSquare/SquareGame.jack").getFile());
		File xmlFile = new File(classLoader.getResource("ExpressionLessSquare/SquareGameT.xml").getFile());
		
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
		File jackFile = new File(classLoader.getResource("ExpressionLessSquare/Main.jack").getFile());
		File xmlFile = new File(classLoader.getResource("ExpressionLessSquare/MainT.xml").getFile());
		
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
