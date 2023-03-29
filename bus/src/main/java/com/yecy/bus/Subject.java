package com.yecy.bus;



import java.util.Map;

public interface Subject {
    void notifyAllObservers(Map<Observer,Boolean> threadMap);
}
