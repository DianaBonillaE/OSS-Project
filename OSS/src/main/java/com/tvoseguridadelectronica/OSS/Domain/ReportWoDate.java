package com.tvoseguridadelectronica.OSS.Domain;

public class ReportWoDate {

    String nameClient;
    String date;
    String description;


    public ReportWoDate(String nameClient, String date, String description) {
        this.nameClient = nameClient;
        this.date = date;
        this.description = description;
    }

    public ReportWoDate() {
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ReportWoDate{" +
                "nameClient='" + nameClient + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
