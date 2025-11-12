package com.example.demo.model;

import jakarta.validation.constraints.NotBlank;

public class AddressForm {

    @NotBlank(message = "Address is required")
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
