public class AddUnaryFunction implements IntUnaryFunction {
    private int addend = 0;
    public AddUnaryFunction(int add) {
        addend = add;
    }
    @Override
    public int apply(int x) {
        return x + addend;
    }

    public int getAddend() {
        return addend;
    }

    public void setAddend(int addend) {
        this.addend = addend;
    }
}
