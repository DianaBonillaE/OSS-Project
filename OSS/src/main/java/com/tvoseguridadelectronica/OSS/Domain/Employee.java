package com.tvoseguridadelectronica.OSS.Domain;

public class Employee {
    String id;
    String name;
    String lastName;
    String position;
    Role role;
    String username;
    String password;

    public Employee(String id, String name, String lastName, String position, Role role, String username, String password) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.position = position;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public Employee() {
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return role.getName();
    }
}
