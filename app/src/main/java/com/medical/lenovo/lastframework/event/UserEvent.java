package com.medical.lenovo.lastframework.event;

/**
 * Created by lenovo on 2016/3/30.
 */
public class UserEvent {
    long id;
    String name;
    public  UserEvent(long id,String name) {
        this.id= id;
        this.name= name;
    }
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
