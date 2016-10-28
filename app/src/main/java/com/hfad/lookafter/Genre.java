package com.hfad.lookafter;

/**
 * Created by Niki on 2016-10-24.
 */

public class Genre {
    private String name;

    public static final Genre[]  genres = {
            new Genre("Wiersze"),
            new Genre("Baśnie"),
            new Genre("Legendy")
    };

   private Genre (String name){
       this.name = name;
   }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
