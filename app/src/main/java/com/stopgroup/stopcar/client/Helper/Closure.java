package com.stopgroup.stopcar.client.Helper;

import java.util.concurrent.Callable;

interface ClosureInterface<T> extends Callable<Void> {
    void run(T args);
    void callable();
}

public abstract class Closure<T> implements ClosureInterface<T>{
    @Override
    public void run(T args) {

    }

    @Override
    public void callable(){

    }
    @Override
    public Void call() {
        return null;
    }
}