package jbruteforce.generator.linear;

abstract class Node {
    int value;

    protected Node() {
        reset();
    }

    public void increment() {
        if (!limitReached()) {
            this.value++;
        }
    }
    
    public Character get() {
        return (char) this.value;
    }

    public abstract boolean limitReached();
    public abstract void reset();
}