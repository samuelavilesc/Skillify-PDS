package com.pds.skillify.model;

public class Course {
	private String name;
    private int progress;
    private String description;
    public Course(String name, int progress, String desc) {
        this.name = name;
        this.progress = progress;
        this.description=desc;
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }

    public int getProgress() {
        return progress;
    }
}
