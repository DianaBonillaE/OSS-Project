package com.tvoseguridadelectronica.OSS.Domain;

public class Device {
	
	private String serialNumber;
	private String name;
	private String manufacturerModel;
	private String description;
	private int quantity;
	private DeviceState state;
	private InventoryCategory category;
	private ModelBrand brand;
	private MeasurementUnit unit;
	
	public Device() {
		this.state=new DeviceState();
		this.category=new InventoryCategory();
		this.brand=new ModelBrand();
		this.unit=new MeasurementUnit();
	}

	public Device(String serialNumber, String name, String manufacturerModel, String description, int quantity,
			DeviceState state, InventoryCategory category, ModelBrand brand, MeasurementUnit unit) {
		this.serialNumber = serialNumber;
		this.name = name;
		this.manufacturerModel = manufacturerModel;
		this.description = description;
		this.quantity = quantity;
		this.state = state;
		this.category = category;
		this.brand = brand;
		this.unit = unit;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufacturerModel() {
		return manufacturerModel;
	}

	public void setManufacturerModel(String manufacturerModel) {
		this.manufacturerModel = manufacturerModel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public DeviceState getState() {
		return state;
	}

	public void setState(DeviceState state) {
		this.state = state;
	}

	public InventoryCategory getCategory() {
		return category;
	}

	public void setCategory(InventoryCategory category) {
		this.category = category;
	}

	public ModelBrand getBrand() {
		return brand;
	}

	public void setBrand(ModelBrand brand) {
		this.brand = brand;
	}

	public MeasurementUnit getUnit() {
		return unit;
	}

	public void setUnit(MeasurementUnit unit) {
		this.unit = unit;
	}

	@Override
	public String toString() {
		return "Device [serialNumber=" + serialNumber + ", name=" + name + ", manufacturerModel=" + manufacturerModel
				+ ", description=" + description + ", quantity=" + quantity + ", state=" + state + ", category="
				+ category + ", brand=" + brand + ", unit=" + unit + "]";
	}
	
	
	
	

}
