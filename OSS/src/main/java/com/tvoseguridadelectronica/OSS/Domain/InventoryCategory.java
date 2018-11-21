package com.tvoseguridadelectronica.OSS.Domain;

public class InventoryCategory {

    private int id;
    private String name;

    public InventoryCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public InventoryCategory() {
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
		return "InventoryCategory [id=" + id + ", name=" + name + "]";
	}
    
    
}
