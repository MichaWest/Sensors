package com.example.sensors;

public class Person {
    private String name;
    private String vorname;
    private String password;
    private String mail;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    // Получение координат устройства пользователя
    public int[] getCoord(){
        return null;
    }
}
