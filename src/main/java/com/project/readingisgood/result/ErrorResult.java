package com.project.readingisgood.result;

public class ErrorResult extends Result{

    public ErrorResult(String message) {
        super(false, message);
    }
}
