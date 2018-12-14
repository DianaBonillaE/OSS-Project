package com.tvoseguridadelectronica.OSS.Domain;

public class ListInventoryWorkOrder {

   private int id;
   private String name;

    public ListInventoryWorkOrder(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ListInventoryWorkOrder() {
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
