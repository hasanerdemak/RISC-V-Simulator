public class Executer {
    public void R_Handler(String instruction){
        //add, sub, sll, slt, sltu, xor, srl, sra, and, or
        //add, sub, xor, srl, sra, and, or
        String[] str = instruction.split(" ");
        String func = str[0];
        String destReg = str[1];
        String sourceReg1 = str[2];
        String sourceReg2 = str[3];

        System.out.println("R: "+str);
    }
    public void I_Handler(String instruction){
        //addi, slti, sltiu, xori, andi, slli, srli, srai, jalr, lb, lw
        //addi, subi, slti, xori, srai, jalr, lb, lw
        String[] str = instruction.split(" ");
        String func = str[0];
        String destReg = str[1];
        String sourceReg1 = str[2];
        String immValue = str[3];
        System.out.println("I: "+str);
    }
    public void B_Handler(String instruction){
        //beq,bne,bge,blt
        //beq,bge,blt
        String[] str = instruction.split(" ");
        String func = str[0];
        String sourceReg1 = str[1];
        String sourceReg2 = str[2];
        String immValue = str[3];
        System.out.println("B: "+str);
    }
    public void S_Handler(String instruction){
        //sb, sh, sw
        //sb, sw
        String[] str = instruction.split(" ");
        String func = str[0];
        String sourceReg1 = str[1];
        String sourceReg2 = str[2];
        String immValue = str[3];
        System.out.println("S: "+str);
    }
    public void J_Handler(String instruction){
        //jal
        String[] str = instruction.split(" ");
        String func = str[0]; //jal
        String destReg = str[1];
        String immValue = str[2];

        System.out.println("J: "+str);
    }
}
