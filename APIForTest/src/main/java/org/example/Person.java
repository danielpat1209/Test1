package org.example;

public class Person {

    private int line;
    private String id;
    private String firstName;
    private String lastName;
    private int birthYear;
    private String gender;
    private String city;
    private double price;

    public Person(int line, String id, String firstName, String lastName, int birthYear, String gender, String city, double price) {
        this.line = line;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
        this.gender = gender;
        this.city = city;
        this.price = price;
    }

    // Getters
    public int getLine() { return line; }
    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getBirthYear() { return birthYear; }
    public String getGender() { return gender; }
    public String getCity() { return city; }
    public double getPrice() { return price; }
}
