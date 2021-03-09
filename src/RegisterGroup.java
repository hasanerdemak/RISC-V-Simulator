public class RegisterGroup {
    private int[] registers = new int[32];

    public RegisterGroup(){
        for (int i = 0; i < 32; i++) {
            registers[i] = 0;
        }
    }

    public int getRegister(int i) {
        return registers[i];
    }

    public void setRegister(int i, int value) {
        if (i>0 && i<32)
            registers[i] = value;
    }
}
