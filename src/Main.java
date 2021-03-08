public class Main {
    static int program_counter = 0;
    static InstructionMem instructionMem;
    static DataMem dataMem;
    static RegisterGroup registerGroup;
    static Executer executer;
    public static void main(String[] args) {
        registerGroup = new RegisterGroup();
        instructionMem = new InstructionMem("ornek1.txt");
        dataMem = new DataMem();
        executer = new Executer(program_counter, registerGroup, dataMem);
        String instruction = instructionMem.getInstruction(program_counter);
        String[] instructionSplt = instructionMem.getInstruction(program_counter).split(" ");

        while (!instructionSplt[0].equals("SON")) {
            if (    instructionSplt[0].equals("add") || instructionSplt[0].equals("sub") ||
                    instructionSplt[0].equals("xor") || instructionSplt[0].equals("and") ||
                    instructionSplt[0].equals("srl") || instructionSplt[0].equals("sra") )
                executer.R_Handler(instruction);
            else if(instructionSplt[0].equals("addi") || instructionSplt[0].equals("subi") ||
                    instructionSplt[0].equals("xori") || instructionSplt[0].equals("jalr") ||
                    instructionSplt[0].equals("lw") || instructionSplt[0].equals("lb") ||
                    instructionSplt[0].equals("slti") || instructionSplt[0].equals("srai") )
                executer.I_Handler(instruction);
            else if(instructionSplt[0].equals("beq") || instructionSplt[0].equals("bge") ||
                    instructionSplt[0].equals("blt") )
                executer.B_Handler(instruction);
            else if(instructionSplt[0].equals("sw") || instructionSplt[0].equals("sb") )
                executer.S_Handler(instruction);
            else if(instructionSplt[0].equals("jal") )
                executer.J_Handler(instruction);

            instruction = instructionMem.getInstruction(program_counter);
            instructionSplt = instruction.split(" ");
        }
        System.out.println(registerGroup.getRegister(14));

    }



}
