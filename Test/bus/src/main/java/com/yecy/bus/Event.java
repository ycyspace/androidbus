package com.yecy.bus;



import java.util.ArrayList;
import java.util.Map;

public abstract class Event<T extends Event<T>> implements Subject {
    ArrayList<Observer> observers = new ArrayList<>();

    public Event() {
        Bus.getInstance().addEvent(getClass(), this);
    }

    public abstract T initEvent();



    @Override
    public void notifyAllObservers(Map<Observer, Boolean> threadMap) {
        for (Observer<T> observer : observers) {
            if (observer != null) {
                if(threadMap.get(observer)!= null ){
                    if(Boolean.TRUE.equals(threadMap.get(observer))){
                        Bus.getInstance().runInUiThread(() -> observer.update(initEvent()));
                    }else observer.update(initEvent());
                }

            }
        }
    }
}
