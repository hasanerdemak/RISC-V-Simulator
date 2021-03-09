import java.io.*;

public class Benzetim {

    static int program_counter;
    static InstructionMem instructionMem;
    static DataMem dataMem;
    static RegisterGroup registerGroup;

    static int cevrimSayisi;
    static int yurutulenToplamBuyruk;
    static double frequency;
    static int R_BBC;
    static int I_BBC;
    static int B_BBC;
    static int S_BBC;
    static int J_BBC;

    public static void main(String[] args) throws FileNotFoundException {
        String[] islemci_1_veri = executer(args[0], args[1]);
        double yurutmeZamani1 = Double.parseDouble(islemci_1_veri[2]);

        File outFile = new File("sonuclar.txt");
        PrintWriter pw = new PrintWriter(outFile);

        if (args.length==3) { // 2. bir islemci varsa basarimlarini kiyasla
            String[] islemci_2_veri = executer(args[0], args[2]);
            double yurutmeZamani2 = Double.parseDouble(islemci_2_veri[2]);

            if (yurutmeZamani1 > yurutmeZamani2)
                pw.write("Islemci2'nin basarimi Islemci1’in basarimindan " + ((yurutmeZamani1 / yurutmeZamani2) - 1) + " kat daha yuksek.");
            else if (yurutmeZamani1 < yurutmeZamani2)
                pw.write("Islemci1'nin basarimi Islemci2’in basarimindan " + ((yurutmeZamani2 / yurutmeZamani1) - 1) + " kat daha yuksek.");
            else
                pw.write("Iki islemcinin de basarimi ayni.");
        }
        else {
            pw.write("Cevrim Sayisi: " + islemci_1_veri[0] +"\n");
            pw.write("Yurutulen Toplam Buyruk: " + islemci_1_veri[1]+"\n");
            pw.write("Yurutme Zamani: " + islemci_1_veri[2]+"\n");
        }

        File outFile2 = new File("cikti.txt");
        PrintWriter pw2 = new PrintWriter(outFile2);
        for (int i = 0; i < 32; i++) {
            pw2.write("x"+i +": "+registerGroup.getRegister(i)+"\n");
        }
        pw.close();
        pw2.close();
    }
    public static String[] executer(String programFile, String configFile) throws FileNotFoundException {
        cevrimSayisi = 0;
        yurutulenToplamBuyruk = 0;
        program_counter = 0;
        BufferedReader reader;
        try {
            int counter = 0;
            reader = new BufferedReader(new FileReader(configFile));
            String line = reader.readLine();
            while (line != null) {
                int value = Integer.parseInt(line.substring(line.indexOf(' ')+1));
                switch (counter) {
                    case 0-> frequency = value*Math.pow(10,6); //MHz --> Hz
                    case 1-> R_BBC = value;
                    case 2-> I_BBC = value;
                    case 3-> B_BBC = value;
                    case 4-> S_BBC = value;
                    case 5-> J_BBC = value;
                }
                counter++;
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(frequency+ "\n"+R_BBC+ "\n"+ I_BBC+ "\n"+B_BBC+ "\n"+S_BBC+ "\n"+J_BBC+ "\n");
        registerGroup = new RegisterGroup();
        instructionMem = new InstructionMem(programFile);
        dataMem = new DataMem();
        String instruction = instructionMem.getInstruction(program_counter);
        String[] instructionSplt = instructionMem.getInstruction(program_counter).split(" ");


        while (!instructionSplt[0].equals("SON")) {
            switch (instructionSplt[0]) {
                case "add", "sub", "xor", "and", "srl", "sra" -> R_Handler(instruction);
                case "addi", "subi", "xori", "jalr", "lw", "lb", "slti", "srai" -> I_Handler(instruction);
                case "beq", "bge", "blt" -> B_Handler(instruction);
                case "sw", "sb" -> S_Handler(instruction);
                case "jal" -> J_Handler(instruction);
            }
            instruction = instructionMem.getInstruction(program_counter);
            //System.out.println(program_counter+" "+ instruction);
            instructionSplt = instruction.split(" ");

        }

        double yurutmeZamani = cevrimSayisi * (1.0/frequency);
        //System.out.println("program sayaci: "+program_counter);
        //System.out.println("Cevrim Sayisi: "+cevrimSayisi);
        //System.out.println("Yurutulen Toplam Buyruk: "+ yurutulenToplamBuyruk);
        //System.out.println("Yurutme Zamani: "+yurutmeZamani);
        String[] str = new String[]{String.valueOf(cevrimSayisi), String.valueOf(yurutulenToplamBuyruk), String.valueOf(yurutmeZamani)};
        return str;
    }

    public static void R_Handler(String instruction){
        //add, sub, xor, srl, sra, and, or
        String[] str = instruction.split(" ");
        String func = str[0];
        int destRegAddress = Integer.parseInt(str[1].substring(1));
        int sourceReg1Value = registerGroup.getRegister(Integer.parseInt(str[2].substring(1)));
        int sourceReg2Value = registerGroup.getRegister(Integer.parseInt(str[3].substring(1)));

        switch (func) {
            case "add" -> {
                int value = sourceReg1Value + sourceReg2Value;
                registerGroup.setRegister(destRegAddress, value);
            }
            case "sub" -> {
                int value = sourceReg1Value - sourceReg2Value;
                registerGroup.setRegister(destRegAddress, value);
            }
            case "and" -> {
                int value = sourceReg1Value & sourceReg2Value;
                registerGroup.setRegister(destRegAddress, value);
            }
            case "xor" -> {
                int value = sourceReg1Value ^ sourceReg2Value;
                registerGroup.setRegister(destRegAddress, value);
            }
            case "srl" -> {
                int value = sourceReg1Value >>> sourceReg2Value;
                registerGroup.setRegister(destRegAddress, value);
            }
            case "sra" -> {
                int value = sourceReg1Value >> sourceReg2Value;
                registerGroup.setRegister(destRegAddress, value);
            }
            default -> throw new IllegalArgumentException("Hata! Boyle bir islem yok.");
        }
        cevrimSayisi += R_BBC;
        yurutulenToplamBuyruk++;
        program_counter += 4;
    }

    public static void I_Handler(String instruction){
        //addi, subi, slti, xori, srai, jalr, lb, lw
        String[] str = instruction.split(" ");
        String func = str[0];
        int destRegAddress = Integer.parseInt(str[1].substring(1));
        int sourceReg1Value = registerGroup.getRegister(Integer.parseInt(str[2].substring(1)));
        int immValue = Integer.parseInt(str[3],16);
        switch (func) {
            case "addi" -> {
                int value = sourceReg1Value + immValue;
                registerGroup.setRegister(destRegAddress, value);
                program_counter += 4;
            }
            case "subi" -> {
                int value = sourceReg1Value - immValue;
                registerGroup.setRegister(destRegAddress, value);
                program_counter += 4;
            }
            case "xori" -> {
                int value = sourceReg1Value ^ immValue;
                registerGroup.setRegister(destRegAddress, value);
                program_counter += 4;
            }
            case "slti" -> {
                int value = (sourceReg1Value < immValue) ? 1:0;
                registerGroup.setRegister(destRegAddress, value);
                program_counter += 4;
            }
            case "srai" -> {
                int value = sourceReg1Value >> immValue;
                registerGroup.setRegister(destRegAddress, value);
                program_counter += 4;
            }
            case "jalr" -> {
                int value = sourceReg1Value + immValue;
                registerGroup.setRegister(destRegAddress, program_counter+4);//unnecessary. x0 cannot change
                program_counter = value;
            }
            case "lb" -> {
                registerGroup.setRegister(destRegAddress, (byte)dataMem.getDataMem(immValue));
                program_counter += 4;
            }
            case "lw" -> {
                registerGroup.setRegister(destRegAddress, dataMem.getDataMem(immValue));
                program_counter += 4;
            }
            default -> throw new IllegalArgumentException("Hata! Boyle bir islem yok.");
        }
        cevrimSayisi += I_BBC;
        yurutulenToplamBuyruk++;
    }

    public static void B_Handler(String instruction){
        //beq,bge,blt
        String[] str = instruction.split(" ");
        String func = str[0];
        int sourceReg1Value = registerGroup.getRegister(Integer.parseInt(str[1].substring(1)));
        int sourceReg2Value = registerGroup.getRegister(Integer.parseInt(str[2].substring(1)));
        int immValue = Integer.parseInt(str[3],16);
        switch (func) {
            case "beq" -> {
                program_counter += (sourceReg1Value == sourceReg2Value) ? immValue*2 : 4;
            }
            case "bne" -> {
                program_counter += (sourceReg1Value != sourceReg2Value) ? immValue*2 : 4;
            }
            case "bge" -> {
                program_counter += (sourceReg1Value >= sourceReg2Value) ? immValue*2 : 4;
            }
            case "blt" -> {
                program_counter += (sourceReg1Value < sourceReg2Value) ? immValue*2 : 4;
            }
            default -> throw new IllegalArgumentException("Hata! Boyle bir islem yok.");
        }
        cevrimSayisi += B_BBC;
        yurutulenToplamBuyruk++;
    }

    public static void S_Handler(String instruction){
        //sb, sw
        String[] str = instruction.split(" ");
        String func = str[0];
        int sourceReg1Value = registerGroup.getRegister(Integer.parseInt(str[1].substring(1)));
        int sourceReg2Value = registerGroup.getRegister(Integer.parseInt(str[2].substring(1)));
        int immValue = Integer.parseInt(str[3],16);

        int address = sourceReg2Value + immValue;
        switch (func) {
            case "sb" -> {
                dataMem.setDataMem(address, (byte)sourceReg1Value);
            }
            case "sw" -> {
                dataMem.setDataMem(address, sourceReg1Value);
            }
            default -> throw new IllegalArgumentException("Hata! Boyle bir islem yok.");
        }
        cevrimSayisi += S_BBC;
        yurutulenToplamBuyruk++;
        program_counter += 4;
    }

    public static void J_Handler(String instruction){
        //jal
        String[] str = instruction.split(" ");
        //String func = str[0]; //jal
        int destRegAddress = Integer.parseInt(str[1].substring(1)); //Always x1
        String immValue = str[2];

        registerGroup.setRegister(destRegAddress, program_counter+4);
        program_counter += Integer.parseInt(immValue,16) * 2;

        cevrimSayisi += J_BBC;
        yurutulenToplamBuyruk++;
    }

}
