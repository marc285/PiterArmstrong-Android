package com.dsaproject.piterarmstrong_android.models;

import java.util.List;

public class ObjetoList { //Instance containing the logged User's (User instance) Objects

    private static ObjetoList objetolistinstance;

    private List<Objeto> objetolist;
    //private int listsize;

    private ObjetoList() {
        //this.listsize = 0;
    }

    public static synchronized ObjetoList getInstance(){
        if(objetolistinstance == null)
            objetolistinstance = new ObjetoList();
        return objetolistinstance;
    }

    //----------------------Methods----------------------//
    //public void putObjeto(Objeto obj){this.objetolist.set(listsize, obj);}
    //public Objeto getObjeto(int index) {return this.objetolist.get(index);}
    public void setList(List<Objeto> newlist) {this.objetolist = newlist;}
    public List<Objeto> getList(){ return this.objetolist; }

    public void closeInstance(){
       this.objetolist.clear();
    }
    //---------------------------------------------------//
}
