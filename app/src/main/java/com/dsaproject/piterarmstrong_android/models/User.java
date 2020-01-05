package com.dsaproject.piterarmstrong_android.models;

import java.io.Serializable;

public class User implements Serializable { //Serializable so that it can be passed between Activities
    //Singleton
    private static User userinstance;

    private String username;
    private String password;
    private Integer health;
    private Integer defense;
    private Integer attack;
    private Integer money;
    private Integer pieces;

    public User(String username, String password, Integer health, Integer defense, Integer attack, Integer money, Integer pieces) {
        this.username = username;
        this.password = password;
        this.health = health;
        this.defense = defense;
        this.attack = attack;
        this.money = money;
        this.pieces = pieces;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /*public User() {
        //Empty constructor
    }*/

    //Singleton
    private User(){}

    public static synchronized User getInstance(){
        if(userinstance == null) {
            userinstance = new User();
        }
        return userinstance;
    }

    //----------------------Methods----------------------//
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

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public Integer getDefense() {
        return defense;
    }

    public void setDefense(Integer defense) {
        this.defense = defense;
    }

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getPieces() {
        return pieces;
    }

    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }

    public void closeInstance(){
        setUsername(null);
        setPassword(null);
        setHealth(null);
        setDefense(null);
        setAttack(null);
        setPieces(null);
        setMoney(null);
    }
    //---------------------------------------------------//
}
