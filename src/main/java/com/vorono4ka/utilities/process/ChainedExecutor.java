package com.vorono4ka.utilities.process;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

public class ChainedExecutor<T> {
    private final List<Element> chainElements = new LinkedList<>();

    private ChainedExecutor() {
    }

    public static <T> ChainedExecutor<T> create() {
        return new ChainedExecutor<>();
    }

    public ChainedExecutor<T> add(Callable<T> executeAction) {
        chainElements.add(new Element(executeAction, null, null));
        return this;
    }

    public ChainedExecutor<T> add(Callable<T> executeAction, Consumer<T> startAction, Consumer<T> exitAction) {
        chainElements.add(new Element(executeAction, startAction, exitAction));
        return this;
    }

    public boolean run(ChainRunningAction<T> runningMethod) throws Exception {
        for (Element element : chainElements) {
            T result = element.execute();
            element.executeStartAction(result);
            runningMethod.accept(result);
            element.executeExitAction(result);
        }

        return true;
    }

    private class Element {
        private final Callable<T> executeAction;
        private final Consumer<T> startAction;
        private final Consumer<T> exitAction;

        public Element(Callable<T> executeAction, Consumer<T> startAction, Consumer<T> exitAction) {
            this.executeAction = executeAction;
            this.startAction = startAction;
            this.exitAction = exitAction;
        }

        public T execute() throws Exception {
            return executeAction.call();
        }

        public void executeStartAction(T result) {
            if (startAction == null) return;
            startAction.accept(result);
        }

        public void executeExitAction(T result) {
            if (exitAction == null) return;
            exitAction.accept(result);
        }
    }

    @FunctionalInterface
    public interface ChainRunningAction<V> {
        void accept(V element) throws Exception;
    }
}
