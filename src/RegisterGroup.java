
public class RegisterGroup {
    private int[] registers = new int[32];

    public RegisterGroup(){
        for (int i = 0; i < 32; i++) {
            registers[i] = 0;
        }
    }

    public int getRegister(int i) {
        if (i<0 || i>31)
            throw new IllegalArgumentException("Register icin yanlis index");
        return registers[i];
    }

    public void setRegister(int i, int value) {
        if (i==0)
            throw new IllegalArgumentException("0. Register'in degeri degistirilemez.");
        if (i<0 || i>31)
            throw new IllegalArgumentException("Register icin yanlis index");
        registers[i] = value;
    }
}
