public class DataMem {
    private int[] dataMem;
    public DataMem(){
        dataMem = new int[(int) Math.pow(2,18)]; //(2^20)%4
    }

    public int getDataMem(int address) {
        return dataMem[address];
    }

    public void setDataMem(int address, int data) {
        dataMem[address] =  data;
    }
}
