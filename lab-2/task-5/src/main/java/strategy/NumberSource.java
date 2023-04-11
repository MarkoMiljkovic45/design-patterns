package strategy;

public interface NumberSource {

    /**
     * A source of non-negative integers
     * @return next int from source or -1 if source is exhausted
     */
    int poll();
}
