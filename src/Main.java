public class Main {
    static int program_counter = 0;
    static InstructionMem instructionMem;
    static DataMem dataMem;
    static RegisterGroup registerGroup;
    public static void main(String[] args) {
        registerGroup = new RegisterGroup();
        instructionMem = new InstructionMem("ornek1.txt");
        String instruction = instructionMem.getInstruction(program_counter);
        String[] instructionSplt = instructionMem.getInstruction(program_counter).split(" ");

        while (!instructionSplt[0].equals("SON")) {
            if (instructionSplt[0].equals("add"))
                R_Handler(instruction);
            else I_Handler(instruction);
            instruction = instructionMem.getInstruction(program_counter);
            instructionSplt = instruction.split(" ");
        }
        System.out.println(registerGroup.getRegister(11));

    }

    public static void R_Handler(String instruction){
        //add, sub, sll, slt, sltu, xor, srl, sra, and, or
        //add, sub, xor, srl, sra, and, or
        String[] str = instruction.split(" ");
        String func = str[0];
        if (func.equals("SON")){
            return;
        }
        String destReg = str[1];
        String sourceReg1 = str[2];
        String sourceReg2 = str[3];
        if (func.equals("add")){
            int value = registerGroup.getRegister(Integer.parseInt(sourceReg1.substring(1))) +
                        registerGroup.getRegister(Integer.parseInt(sourceReg2.substring(1)));
            registerGroup.setRegister(Integer.parseInt(destReg.substring(1)), value);
            program_counter+=4;
        }
        else if (func.equals("sub")){
            int value = registerGroup.getRegister(Integer.parseInt(sourceReg1.substring(1))) -
                        registerGroup.getRegister(Integer.parseInt(sourceReg2.substring(1)));
            registerGroup.setRegister(Integer.parseInt(destReg.substring(1)), value);
            program_counter+=4;
        }
        else if (func.equals("and")){
            int value = registerGroup.getRegister(Integer.parseInt(sourceReg1.substring(1))) &
                        registerGroup.getRegister(Integer.parseInt(sourceReg2.substring(1)));
            registerGroup.setRegister(Integer.parseInt(destReg.substring(1)), value);
            program_counter+=4;
        }
        else if (func.equals("or")){
            int value = registerGroup.getRegister(Integer.parseInt(sourceReg1.substring(1))) |
                        registerGroup.getRegister(Integer.parseInt(sourceReg2.substring(1)));
            registerGroup.setRegister(Integer.parseInt(destReg.substring(1)), value);
            program_counter+=4;
        }
        else if (func.equals("xor")){
            int value = registerGroup.getRegister(Integer.parseInt(sourceReg1.substring(1))) ^
                        registerGroup.getRegister(Integer.parseInt(sourceReg2.substring(1)));
            registerGroup.setRegister(Integer.parseInt(destReg.substring(1)), value);
            program_counter+=4;
        }
        else if (func.equals("srl")){
            int value = registerGroup.getRegister(Integer.parseInt(sourceReg1.substring(1))) >>>
                        registerGroup.getRegister(Integer.parseInt(sourceReg2.substring(1)));
            registerGroup.setRegister(Integer.parseInt(destReg.substring(1)), value);
            program_counter+=4;
        }
        else if (func.equals("sra")){
            int value = registerGroup.getRegister(Integer.parseInt(sourceReg1.substring(1))) >>
                        registerGroup.getRegister(Integer.parseInt(sourceReg2.substring(1)));
            registerGroup.setRegister(Integer.parseInt(destReg.substring(1)), value);
            program_counter+=4;
        }
        else
            throw new IllegalArgumentException("Hata! Boyle bir islem yok.");

    }
    public static void I_Handler(String instruction){
        //addi, slti, sltiu, xori, andi, slli, srli, srai, jalr, lb, lw
        //addi, subi, slti, xori, srai, jalr, lb, lw
        String[] str = instruction.split(" ");
        String func = str[0];
        String destReg = str[1];
        String sourceReg1 = str[2];
        String immValue = str[3];
        if (func.equals("addi")){
            int value = registerGroup.getRegister(Integer.parseInt(sourceReg1.substring(1))) +
                    Integer.parseInt(immValue);
            registerGroup.setRegister(Integer.parseInt(destReg.substring(1)), value);
            program_counter+=4;
        }
        else if (func.equals("subi")){
            int value = registerGroup.getRegister(Integer.parseInt(sourceReg1.substring(1))) -
                    Integer.parseInt(immValue);
            registerGroup.setRegister(Integer.parseInt(destReg.substring(1)), value);
            program_counter+=4;
        }
        else if (func.equals("xori")){
            int value = registerGroup.getRegister(Integer.parseInt(sourceReg1.substring(1))) ^
                    Integer.parseInt(immValue);
            registerGroup.setRegister(Integer.parseInt(destReg.substring(1)), value);
            program_counter+=4;
        }
        else if (func.equals("slti")){
            int value = registerGroup.getRegister(Integer.parseInt(sourceReg1.substring(1))) +
                    Integer.parseInt(immValue);
            registerGroup.setRegister(Integer.parseInt(destReg.substring(1)), value);
            program_counter+=4;
        }
        else if (func.equals("srai")){
            int value = registerGroup.getRegister(Integer.parseInt(sourceReg1.substring(1))) +
                    Integer.parseInt(immValue);
            registerGroup.setRegister(Integer.parseInt(destReg.substring(1)), value);
            program_counter+=4;
        }
        else if (func.equals("jalr")){
            int value = registerGroup.getRegister(Integer.parseInt(sourceReg1.substring(1))) +
                    Integer.parseInt(immValue);
            registerGroup.setRegister(Integer.parseInt(destReg.substring(1)), value);
            program_counter+=4;
        }
        else if (func.equals("lb")){
            int value = registerGroup.getRegister(Integer.parseInt(sourceReg1.substring(1))) +
                    Integer.parseInt(immValue);
            registerGroup.setRegister(Integer.parseInt(destReg.substring(1)), value);
            program_counter+=4;
        }
        else if (func.equals("lw")){
            int value = registerGroup.getRegister(Integer.parseInt(sourceReg1.substring(1))) +
                    Integer.parseInt(immValue);
            registerGroup.setRegister(Integer.parseInt(destReg.substring(1)), value);
            program_counter+=4;
        }

    }
    public static void B_Handler(String instruction){
        //beq,bne,bge,blt
        //beq,bge,blt
        String[] str = instruction.split(" ");
        String func = str[0];
        String sourceReg1 = str[1];
        String sourceReg2 = str[2];
        String immValue = str[3];
        System.out.println("B: "+str);
    }
    public static void S_Handler(String instruction){
        //sb, sh, sw
        //sb, sw
        String[] str = instruction.split(" ");
        String func = str[0];
        String sourceReg1 = str[1];
        String sourceReg2 = str[2];
        String immValue = str[3];
        System.out.println("S: "+str);
    }
    public static void J_Handler(String instruction){
        //jal
        String[] str = instruction.split(" ");
        //String func = str[0]; //jal
        //String destReg = str[1];
        String immValue = str[2];

        registerGroup.setRegister(1, program_counter);
        program_counter = Integer.parseInt(immValue, 16);
        System.out.println("J: "+str);
    }

}
