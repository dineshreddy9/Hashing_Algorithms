package dxt161330;

public interface HashingAlgorithm <T> {
    public boolean add(T x);
    public boolean contains(T x);
    public T remove(T x);
}
