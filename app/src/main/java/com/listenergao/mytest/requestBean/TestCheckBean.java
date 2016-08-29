package com.listenergao.mytest.requestBean;

/**
 * Created by ListenerGao on 2016/8/29.
 */
public class TestCheckBean {
    private int id;
    private String name;

    public TestCheckBean() {
    }

    public TestCheckBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestCheckBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
