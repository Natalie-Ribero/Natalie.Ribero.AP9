package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;

public class ClientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;

    public ClientDTO(Client client){

    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
