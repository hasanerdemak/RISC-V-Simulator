import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class InstructionMem {
    private static HashMap<Integer,String> instructionMem;
    public InstructionMem(String fileName){
        instructionMem = new HashMap<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                instructionMem.put(Integer.parseInt(line.substring(2,line.indexOf(' ')),16), line.substring(line.indexOf(' ')+1));
                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getInstruction(int address) {
        return instructionMem.get(address);
    }

}
