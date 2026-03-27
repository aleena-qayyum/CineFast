package com.example.a1_23l_0981;

public class Snacks {
    private String name;
    private String description;
    private int price;
    private String imageName;

    public Snacks(String name, String description, int price, String imageName) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageName = imageName;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getPrice() { return price; }
    public String getImageName() { return imageName; }
}