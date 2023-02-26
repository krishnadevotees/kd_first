package com.example.visonofman.ModelClass;

public class UpdateProfileModel {
    String name,email,disc,image;

    public UpdateProfileModel(String name, String email, String disc, String image) {
        this.name = name;
        this.email = email;
        this.disc = disc;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
