package database;

public class GetCounter {
    private final String value;
    private final int count;

    public GetCounter(String value, int count) {
        this.value = value;
        this.count = count;
    }

    public String getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }
}
