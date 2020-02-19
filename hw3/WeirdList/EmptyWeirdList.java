public class EmptyWeirdList extends WeirdList {
    private int head;
    private WeirdList tail;

    public EmptyWeirdList() {
        this.head = 0;
        this.tail = null;
    }

    @Override
    public int length(){
        return 0;
    }

    @Override
    public String toString(){
        return "";
    }

    @Override
    public WeirdList map(IntUnaryFunction func) {
        return this;
    }
}
