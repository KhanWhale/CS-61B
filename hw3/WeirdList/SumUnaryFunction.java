public class SumUnaryFunction implements IntUnaryFunction {
    private int sum = 0;
    public SumUnaryFunction() {
        sum = 0;
    }
    @Override
    public int apply(int x) {
        sum += x;
        return x;
    }
    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }
}
