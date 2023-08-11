package com.example.mobile_adproject.models;

import androidx.annotation.NonNull;

public class CollectionPoint {
    private Long id;
     private String name;
     private String address;
     private int status;
     private String qrCode;

    public CollectionPoint(Long id, String name, String address, int status, String qrCode) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.status = status;
        this.qrCode = qrCode;
    }
}
