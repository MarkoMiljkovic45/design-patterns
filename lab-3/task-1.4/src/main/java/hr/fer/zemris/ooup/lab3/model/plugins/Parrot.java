package hr.fer.zemris.ooup.lab3.model.plugins;

import hr.fer.zemris.ooup.lab3.model.Animal;

public class Parrot extends Animal {

    private final String animalName;

    public Parrot(String animalName) {
        this.animalName = animalName;
    }

    @Override
    public String name() {
        return animalName;
    }

    @Override
    public String greet() {
        return "gawk";
    }

    @Override
    public String menu() {
        return "seeds";
    }
}
