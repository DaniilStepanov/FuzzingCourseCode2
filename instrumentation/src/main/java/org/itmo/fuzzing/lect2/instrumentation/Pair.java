package org.itmo.fuzzing.lect2.instrumentation;

public class Pair<T, S> {
    public T first;
    public S second;

    public Pair(T first, S second) {
        this.first = first;
        this.second = second;
    }

    public S getValue() {
        return second;
    }

    public T getKey() {
        return first;
    }
}
