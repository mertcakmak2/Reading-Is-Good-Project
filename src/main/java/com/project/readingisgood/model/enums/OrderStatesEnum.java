package com.project.readingisgood.model.enums;

public enum OrderStatesEnum {
    RECEIVED("RECEIVED"),
    CREATED("CREATED"),
    CANCELED("CANCELED");

    private String state;

    public String getState()
    {
        return this.state;
    }

    private OrderStatesEnum(String state)
    {
        this.state = state;
    }

}

