public class Casino {
    private long balance;

    public Casino() {
        this.balance = 0;
    }

    public long getBalance() {
        return balance;
    }

    public void addBalance(long balance) {
        this.balance += balance;
    }
}
