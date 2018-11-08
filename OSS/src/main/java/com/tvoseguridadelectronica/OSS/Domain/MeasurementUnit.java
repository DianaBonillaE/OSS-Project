package com.tvoseguridadelectronica.OSS.Domain;

public class MeasurementUnit {

    private  int id;
    private String name;

    public MeasurementUnit() {
    }

    public MeasurementUnit(int id, String name) {
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
        return "MeasurementUnit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
