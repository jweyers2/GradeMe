package com.example.grademe.domainvalue;


public enum Rating {
    SEHR_GUT(1),
    GUT(2),
    BEFRIEDIGEND(3),
    AUSREICHEND(4),
    MANGELHAFT(5),
    UNGENÜGEND(6);

    private int rateInt;

    Rating(int rateInt){
        this.rateInt = rateInt;
    }
    public int getNoteAsInt(){
        return rateInt;
    }
}

