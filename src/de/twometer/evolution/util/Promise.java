package de.twometer.evolution.util;

public class Promise {

    private Runnable complete;

    private Runnable error;

    public Promise then(Runnable runnable) {
        complete = runnable;
        return this;
    }

    public Promise error(Runnable runnable) {
        error = runnable;
        return this;
    }

    public void resolve() {
        if (complete != null)
            complete.run();
    }

    public void reject() {
        if (error != null)
            error.run();
    }

}
