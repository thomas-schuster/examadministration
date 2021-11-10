package de.hspf.swt.exam.administration.cdi.model;

public enum LanguageLevel {

    A1("Beginner"), A2("Elementary English"), B1("Intermediate English"), B2("Upper-Intermediate English"), C1("Advanced English"), C2("Proficiency English");

    private final String description;

    private LanguageLevel(String description) {

        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
}
