package com.yecy.bus;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


public class Bus {
    private volatile static Bus instance;
    private static final Handler handler = new Handler(Looper.getMainLooper());
    private Map<Class<?>, ArrayList<Observer>> eventMap = new ConcurrentHashMap<>();
    private Map<Observer, Boolean> threadMap = new ConcurrentHashMap<>();

    public static Bus getInstance() {
        if (instance == null) {
            synchronized (Bus.class) {
                if (instance == null) {
                    instance = new Bus();
                }
            }
        }
        return instance;
    }

    public void runInUiThread(Runnable runnable){
        if(runnable != null){
            handler.post(runnable);
        }
    }

    private Bus() {
    }

    public <T extends Event<T>> void register(Observer<T> observer, Class<?> clazz) {
        threadMap.put(observer, false);
        if (eventMap.containsKey(clazz)) {
            Objects.requireNonNull(eventMap.get(clazz)).add(observer);
        } else {
            ArrayList<Observer> observers = new ArrayList<>();
            observers.add(observer);
            eventMap.put(clazz, observers);
        }
    }

    public <T extends Event<T>> void registerInUi(Observer<T> observer, Class<?> clazz) {
        threadMap.put(observer, true);
        if (eventMap.containsKey(clazz)) {
            Objects.requireNonNull(eventMap.get(clazz)).add(observer);
        } else {
            ArrayList<Observer> observers = new ArrayList<>();
            observers.add(observer);
            eventMap.put(clazz, observers);
        }
    }

    public <T extends Event<T>> void unregister(Observer<T> observer, Class<T> clazz) {
        if (eventMap.containsKey(clazz)) {
            Objects.requireNonNull(eventMap.get(clazz)).remove(observer);
        }
        threadMap.remove(observer);
    }

    public <T extends Event<T>> void addEvent(Class<?> clzClass, Event<T> event) {
        if (!eventMap.containsKey(clzClass)) {
            eventMap.put(clzClass, event.observers);
        }
    }

    public <T extends Event<T>> void postEvent(Event<T> event) {
        event.observers.clear();
        event.observers.addAll(eventMap.get(event.getClass()));
        event.notifyAllObservers(threadMap);
    }

}
