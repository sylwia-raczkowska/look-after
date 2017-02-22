package com.hfad.lookafter;

/**
 * Created by Mohru on 06.02.2017.
 */

public enum AppTheme {
    DarkTheme("DarkTheme"), LightTheme("LightTheme");

    private String theme;

    AppTheme(String theme) {
        this.theme = theme;
    }

    public String getValue() {
        return this.theme;
    }

    public AppTheme fromString(String theme){
        return AppTheme.valueOf(theme);
    }
}
