package com.tvoseguridadelectronica.OSS.Domain;

public class WorkOrderType {

    int id;
    String name;

    public WorkOrderType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public WorkOrderType() {
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
        return "WorkOrderType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
