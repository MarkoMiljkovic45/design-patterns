package hr.fer.zemris.ooup.lab3.model;

public abstract class Animal {

    public abstract String name();
    public abstract String greet();
    public abstract String menu();

    public void animalPrintGreeting() {
        System.out.println(name() + " greets " + greet() + "!");
    }

    public void animalPrintMenu() {
        System.out.println(name() + " likes " + menu() + ".");
    }
}
