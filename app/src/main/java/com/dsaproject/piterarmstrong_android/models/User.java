package com.dsaproject.piterarmstrong_android.models;

import java.io.Serializable;

public class User implements Serializable { //Serializable so that it can be passed between Activities
    //Singleton
    private static User userinstance;

    private String username;
    private String password;
    private int health;
    private int defense;
    private int attack;
    private int money;
    private int pieces;
    private int screen; //(Level)

    public User(String username, String password, int health, int defense, int attack, int money, int pieces, int screen) {
        this.username = username;
        this.password = password;
        this.health = health;
        this.defense = defense;
        this.attack = attack;
        this.money = money;
        this.pieces = pieces;
        this.screen = screen;
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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public int getScreen() { return screen; }

    public void setScreen(int screen) { this.screen = screen; }

    public void closeInstance(){
        setUsername(null);
        setPassword(null);
        setHealth(0);
        setDefense(0);
        setAttack(0);
        setPieces(0);
        setMoney(0);
        setScreen(0);
    }
    //---------------------------------------------------//
}
