package com.mindhub.homebanking.models;

//Clase cliente
public class Client {
    private Long id;
    private String dni;
    private String name;
    private String lastname;

    public Client(){

    }

    //Constructor
    public Client(String dni, String name, String lastname){
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
    }

    public Long getId() {
        return id;
    }

    public String getDni() {
        return dni;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
