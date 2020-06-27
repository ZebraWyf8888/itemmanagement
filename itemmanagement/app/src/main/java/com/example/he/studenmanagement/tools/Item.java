package com.example.he.studenmanagement.tools;

/**
 * 保存项目信息的实体类
 * Created by he on 2020/6/26.
 */
public class Item {
    private String name;//项目名称
    private String complete;//是否完成
    private String id;
    private String endtime;
    private String starttime;
    private String progress;//进度
    private String state;//状态：未开始、进行中、已结束、挂起
    private String person;//负责人
   //private String order;//待用


    public Item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public Item(String name, String complete, String id, String endtime, String starttime, String progress, String state, String person) {
        this.name = name;
        this.complete = complete;
        this.id = id;
        this.endtime = endtime;
        this.starttime = starttime;
        this.progress = progress;
        this.state = state;
        this.person = person;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", complete='" + complete + '\'' +
                ", id='" + id + '\'' +
                ", endtime='" + endtime + '\'' +
                ", starttime='" + starttime + '\'' +
                ", progress='" + progress + '\'' +
                ", state='" + state + '\'' +
                ", person='" + person + '\'' +
                '}';
    }
}
