import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class InstructionMem {
    private static HashMap<Integer,String> instructionMem;
    public InstructionMem(String fileName){
        instructionMem = new HashMap<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                //adres ve buyruk kısmını key-value pairi olarak kaydet
                if (!line.equals(""))
                    instructionMem.put(Integer.parseInt(line.substring(2,line.indexOf(' ')),16), line.substring(line.indexOf(' ')+1));
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
