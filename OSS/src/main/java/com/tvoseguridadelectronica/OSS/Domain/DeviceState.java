package com.tvoseguridadelectronica.OSS.Domain;

public class DeviceState {
	
	private int id;
	private String state;
	
	public DeviceState(int id, String state) {
		this.id = id;
		this.state = state;
	}

	public DeviceState() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "DeviceState [id=" + id + ", state=" + state + "]";
	}
	
		

}
