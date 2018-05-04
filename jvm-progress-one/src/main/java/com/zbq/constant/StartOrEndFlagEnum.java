package com.zbq.constant;

/**
 * @author zhangboqing
 * @date 2018/5/2
 */
public enum StartOrEndFlagEnum {
    START(1,"启动"),END(2,"结束");

    private int id;
    private String name;
    private StartOrEndFlagEnum(int id,String name) {
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
}
