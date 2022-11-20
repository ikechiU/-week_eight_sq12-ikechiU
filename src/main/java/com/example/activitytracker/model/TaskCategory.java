package com.example.activitytracker.model;

public enum TaskCategory {
    PENDING("pending"),
    INCOMPLETE("incomplete"),
    IN_PROGRESS("in-progress"),
    DONE("done");

    private String status;

    TaskCategory(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
