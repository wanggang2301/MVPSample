package com.wg.mvpsample.data;

/**
 * @author: Wangg
 * @Name：Person
 * @Description:
 * @Created on:2017/3/10  10:33.
 */

public class Person {

    private String name;

    private String age;

    public Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
