package com.example;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class HelloBean {

    private String name;

    private Status status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void greet() {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(FacesMessage.Severity.SUCCESS,
                        "Greeting", "Hello, " + name + "!"));
    }
}
