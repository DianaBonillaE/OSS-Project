package com.tvoseguridadelectronica.OSS.Domain;

import java.util.List;

public class Client {

    private String id;
    private String name;
    private String contactName;


    public Client() {
    }

    public Client(String id, String name, String contactName) {
        this.id = id;
        this.name = name;
        this.contactName = contactName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", contactName=" + contactName + "]";
	}
    
    
}
