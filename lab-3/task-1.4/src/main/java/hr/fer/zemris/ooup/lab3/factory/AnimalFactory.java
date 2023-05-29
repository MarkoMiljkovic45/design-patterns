package hr.fer.zemris.ooup.lab3.factory;

import hr.fer.zemris.ooup.lab3.model.Animal;

import java.lang.reflect.Constructor;

public class AnimalFactory {

    private static final String pluginPackage = "hr.fer.zemris.ooup.lab3.model.plugins.";

    public static Animal newInstance(String animalKind, String name) {
        try {
            Class<Animal> clazz = (Class<Animal>) Class.forName(pluginPackage + animalKind);
            Constructor<?> ctr = clazz.getConstructor(String.class);
            return (Animal) ctr.newInstance(name);
        }
        catch (Exception e) {
            return null;
        }
    }
}
