package com.project.qlquancoffeeapi.entity;

public enum ProductType {
    DRINKS("Drink"), DESSERTS("Dessert");
    private  final String displayName;

    ProductType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
