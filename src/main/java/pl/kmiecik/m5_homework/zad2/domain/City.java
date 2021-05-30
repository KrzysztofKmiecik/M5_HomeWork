package pl.kmiecik.m5_homework.zad2.domain;

import java.util.StringJoiner;

public class City {
    private String name;

    public City(String name) {
        this.name = name;
    }
    public City(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", City.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .toString();
    }
}
