package model;

public class Person implements java.io.Serializable{
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    @Override
    public String toString() {
        return  String.format(" Name: %-20s Age: %3d", this.name, this.age) + "\n";
    }
}
