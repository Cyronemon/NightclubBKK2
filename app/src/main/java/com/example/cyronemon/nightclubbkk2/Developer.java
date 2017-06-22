package com.example.cyronemon.nightclubbkk2;

/**
 * Created by Cyronemon on 6/21/2017.
 */

public class Developer {

    private String devName;

    public Developer() {

    }

    public Developer(String devName) {
        this.devName = devName;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "devName='" + devName + '\'' +
                '}';
    }
}
