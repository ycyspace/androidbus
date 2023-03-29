package com.yecy.bus;

public interface Observer<T extends Event<T>> {
    void update(T event);
}
