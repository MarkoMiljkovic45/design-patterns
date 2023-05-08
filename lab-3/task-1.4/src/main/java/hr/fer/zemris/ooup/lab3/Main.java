package hr.fer.zemris.ooup.lab3;

import hr.fer.zemris.ooup.lab3.factory.AnimalFactory;
import hr.fer.zemris.ooup.lab3.model.Animal;

public class Main {

    public static void main(String[] args) {
        for (int i = 0; i < args.length / 2; i++) {
            String className  = args[2*i];
            String animalName = args[2*i + 1];

            Animal animal = AnimalFactory.newInstance(className, animalName);

            if (animal != null) {
                animal.animalPrintGreeting();
                animal.animalPrintMenu();
            }
        }
    }
}
