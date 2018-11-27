package com.tvoseguridadelectronica.OSS.Domain;

import java.util.List;

public class WorkOrder {

    int id;
    String description;
    WorkOrderType workOrderType;
    Client client;
    List<Employee> employees;

    public WorkOrder(int id, String description, WorkOrderType workOrderType, Client client, List<Employee> employees) {
        this.id = id;
        this.description = description;
        this.workOrderType = new WorkOrderType();
        this.client = new Client();
        this.employees = employees;
    }

    public WorkOrder() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public WorkOrderType getWorkOrderType() {
        return workOrderType;
    }

    public void setWorkOrderType(WorkOrderType workOrderType) {
        this.workOrderType = workOrderType;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "WorkOrder{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", workOrderType=" + workOrderType +
                ", client=" + client +
                ", employees=" + employees +
                '}';
    }
}
