package observer;

import context.NumberSequence;

public interface NumberSequenceObserver {
    void update(NumberSequence sequence);
}
