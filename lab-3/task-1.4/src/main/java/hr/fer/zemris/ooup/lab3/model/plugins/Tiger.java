package hr.fer.zemris.ooup.lab3.model.plugins;

import hr.fer.zemris.ooup.lab3.model.Animal;

public class Tiger extends Animal {

    private final String animalName;

    public Tiger(String animalName) {
        this.animalName = animalName;
    }

    @Override
    public String name() {
        return animalName;
    }

    @Override
    public String greet() {
        return "roar";
    }

    @Override
    public String menu() {
        return "meat";
    }
}
