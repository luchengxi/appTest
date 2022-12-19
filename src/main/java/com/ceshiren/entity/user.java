package com.ceshiren.entity;

public class user {
    //姓名
    private String name;
    //手机号码
    private String number;

    public user() {
    }

    public user(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "user{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
