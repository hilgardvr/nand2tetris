import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;


public class HackAssembler {

    private FileReader fileReader;
    private BufferedReader reader;
    private List<String> file;
    private Map<String, String> map;


    //constructor
    public HackAssembler(String filePath) {
        try {
            String line = null;
            this.fileReader = new FileReader(filePath);
            this.reader = new BufferedReader(fileReader);
            this.file = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                file.add(line);
            }
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'", filePath);
        }
    }


    //converts hack assembly to binary and writes to outfile - pure function
    private void WriteFile(String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".hack"));
            for (String line : this.file) {
                System.out.println(line);
                if (line.charAt(0) == '@') {
                    String sub = line.substring(1, line.length());
                    if (this.map.containsKey(sub)) {
                        sub = this.map.get(sub);
                    }
                    Integer subInt = Integer.parseInt(sub);
                    String binary = String.format("%16s", Integer.toBinaryString(subInt)).replace(' ', '0');
                    writer.write(binary + "\n");
                } else {
                    String dest = "null";
                    String comp = "null";
                    String jump = "null";

                    int indexEqual = line.indexOf("=");
                    if (indexEqual != -1) {
                        dest = line.substring(0, indexEqual);
                        line = line.substring(indexEqual + 1);
                    }

                    int indexSemiColon = line.indexOf(";");
                    if (indexSemiColon != -1) {
                        jump = line.substring(indexSemiColon + 1, line.length());
                        line = line.substring(0, indexSemiColon);
                    }
                    comp = line;
                    String binary = "111";
                    if (comp.indexOf('M') != -1) {
                        binary += "1";
                    } else {
                        binary += "0";
                    }
                    binary += GetCompCode(comp);
                    binary += GetDestCode(dest);
                    binary += GetJumpCode(jump);
                    writer.write(binary + "\n");
                }
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("Exception writing to .hack file");
            e.printStackTrace();
        }        
    }

    private String GetCompCode(String comp) {
        switch(comp) {
            case "0":
                return "101010";
            case "1":
                return "111111";
            case "-1":
                return "111010";
            case "D":
                return "001100";
            case "A":
            case "M":
                return "110000";
            case "!D":
                return "001101";
            case "!A":
            case "!M":
                return "110001";
            case "-D":
                return "001111";
            case "-A":
            case "-M":
                return "110011";
            case "D+1":
                return "011111";
            case "A+1":
            case "M+1":
                return "110111";
            case "D-1":
                return "001110";
            case "A-1":
            case "M-1":
                return "110010";
            case "D+A":
            case "D+M":
                return "000010";
            case "D-A":
            case "D-M":
                return "010011";
            case "A-D":
            case "M-D":
                return "000111";
            case "D&A":
            case "D&M":
                return "000000";
            case "D|A":
            case "D|M":
                return "010101";
        }
        System.out.println("------ CompCode not found ----");
        System.out.println(comp);
        return null;
    }

    private String GetDestCode(String dest) {
        switch (dest) {
            case "null":
                return "000";
            case "M":
                return "001";
            case "D":
                return "010";
            case "MD":
            case "DM":
                return "011";
            case "A":
                return "100";
            case "AM":
            case "MA":
                return "101";
            case "AD":
            case "DA":
                return "110";
            case "AMD":
            case "ADM":
            case "MAD":
            case "MDA":
            case "DAM":
            case "DMA":
                return "111";
        }
        return null;
    }

    private String GetJumpCode(String jump) {
        switch (jump) {
            case "null":
                return "000";
            case "JGT":
                return "001";
            case "JEQ":
                return "010";
            case "JGE":
                return "011";
            case "JLT":
                return "100";
            case "JNE":
                return "101";
            case "JLE":
                return "110";
            case "JMP":
                return "111";
        }
        return null;
    }

    //looks up value and replace in hashmap - pure function
    private List<String> CreateLessFile() {
        List<String> newFile = new ArrayList<String>();
        for (int i = 0; i < this.file.size(); i++) {
            String fileLine = this.file.get(i);

            if (fileLine.indexOf('(') == 0) {
                continue;
            } else if (fileLine.indexOf('@') == 0) {
                String sub = fileLine.substring(1, fileLine.length());
                if (this.map.containsKey(sub)) {
                    fileLine = this.map.get(sub);
                    System.out.println("Value retrieved: " + fileLine);
                    fileLine = "@" + fileLine;
                }
            }
            newFile.add(fileLine);
        }
        return newFile;
    }

    //creates the address table - modifies files
    private Map<String, String> CreateTable() {
        HashMap<String, String> createTableMap = new HashMap<String, String>();

        createTableMap.put("SP", "0");
        createTableMap.put("LCL", "1");
        createTableMap.put("ARG", "2");
        createTableMap.put("THIS", "3");
        createTableMap.put("THAT", "4");
        createTableMap.put("R0", "0");
        createTableMap.put("R1", "1");
        createTableMap.put("R2", "2");
        createTableMap.put("R3", "3");
        createTableMap.put("R4", "4");
        createTableMap.put("R5", "5");
        createTableMap.put("R6", "6");
        createTableMap.put("R7", "7");
        createTableMap.put("R8", "8");
        createTableMap.put("R9", "9");
        createTableMap.put("R10", "10");
        createTableMap.put("R11", "11");
        createTableMap.put("R12", "12");
        createTableMap.put("R13", "13");
        createTableMap.put("R14", "14");
        createTableMap.put("R15", "15");
        createTableMap.put("SCREEN", "16384");
        createTableMap.put("KBD", "24576");

        // first pass
        System.out.println("entering");
        Integer ctr = 0;
        for (String fileLine : this.file) {
            if (fileLine.indexOf('(') == 0) {
                System.out.println(fileLine);
                String sub = fileLine.substring(1, fileLine.length() - 1);
                createTableMap.put(sub, ctr.toString());
            } else {
                ctr++;
            }
        }

        System.out.println("entering");
        //second pass
        Integer variableCounter = 16;  

        for (int i = 0; i < this.file.size(); i++) {
            if (this.file.get(i).indexOf('@') == 0) {
                String sub = this.file.get(i).substring(1, this.file.get(i).length());
                try {
                    Integer address = Integer.parseInt(sub);
                }
                catch (NumberFormatException e) {
                    if (createTableMap.containsKey(sub)) {
                        continue;
                    } else {
                        System.out.println("Putting: " + sub + " at: " + variableCounter.toString());
                        createTableMap.put(sub, variableCounter.toString());                
                        System.out.println(createTableMap);
                        variableCounter++;
                    }
                }
            }       
        }
        System.out.println(createTableMap);
        return (createTableMap);
    }

    //removes comments and whitespace - pure function
    private List<String> CleanFile(List<String> file) {
        System.out.println("--- Cleaning file ---");

        List<String> cleanFile = new ArrayList<String>();

        for (String line : this.file) {
            int comment = line.indexOf("//");
            if (comment != -1) {
                line = line.substring(0, comment);
            }
            line = line.replaceAll("\\s","");
            if (line.length() > 0) {
                cleanFile.add(line);
            }
        }

        return (cleanFile);
    }    

    //opens and readers file
    public static void main(String[] args) {
        System.out.println("--- Started assembler ---\n");

        HackAssembler assembler;

        if (args.length != 1) {
            System.out.println("Invalid number of arguments");
            return;
        } else {
            assembler = new HackAssembler(args[0]);
        }

        if (assembler.file != null) {
            assembler.file = assembler.CleanFile(assembler.file);
            assembler.map = assembler.CreateTable();
            assembler.file = assembler.CreateLessFile();
            int indexSlash = args[0].lastIndexOf('/') == -1 ? 0 : args[0].lastIndexOf('/') + 1;
            String fileName = args[0].substring(indexSlash, args[0].lastIndexOf("."));
            assembler.WriteFile(fileName);
        }
        return;
    }
}
