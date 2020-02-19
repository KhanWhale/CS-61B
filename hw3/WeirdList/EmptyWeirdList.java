public class EmptyWeirdList extends WeirdList {
    private int head;

    public EmptyWeirdList() {
        this.head = 0;
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

    public int getHead(){
        return this.head;
    }

    public void setHead(int head) {
        this.head = head;
    }
}
