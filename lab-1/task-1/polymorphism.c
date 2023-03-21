#include <stdio.h>
#include <stdlib.h>
#include "polymorphism.h"

#define DEFAULT_DOG_NAME "Dog"
#define N_DOGS 5

PTRFUN dog_table[] = {dogGreet, dogMenu};
PTRFUN cat_table[] = {catGreet, catMenu};

int main()
{
    testAnimals();

    //Animal objects can be created both on the stack and in the heap
    struct Animal stack_dog;
    stack_dog.name   = "Stack dog";
    stack_dog.vtable = dog_table;

    struct Animal* heap_dog =(struct Animal*) malloc(sizeof (struct Animal));
    heap_dog->name   = "Heap dog";
    heap_dog->vtable = dog_table;

    //Creating multiple Dogs
    struct Animal** dogs = creteMultipleDogs(N_DOGS);
    printf("\nDog group greet:\n");
    for (int i = 0; i < N_DOGS; i++) animalPrintGreeting(dogs[i]);

    return 0;
}

void animalPrintGreeting(struct Animal* animal)
{
    printf("%s greets: %s!\n", animal->name, animal->vtable[0]());
}

void animalPrintMenu(struct Animal* animal)
{
    printf("%s likes: %s!\n", animal->name, animal->vtable[1]());
}

void constructDog(struct Animal* animal, char const* name)
{
    animal->vtable = dog_table;
    animal->name = name;
}

void constructCat(struct Animal* animal, char const* name)
{
    animal->vtable = cat_table;
    animal->name = name;
}

struct Animal* createDog(char const* name)
{
    struct Animal* animal =(struct Animal*) malloc(sizeof (struct Animal));
    constructDog(animal, name);

    return animal;
}

struct Animal* createCat(char const* name)
{
    struct Animal* animal =(struct Animal*) malloc(sizeof (struct Animal));
    constructCat(animal, name);

    return animal;
}

void testAnimals(void)
{
    struct Animal* p1 = createDog("Hamlet");
    struct Animal* p2 = createCat("Ofelija");
    struct Animal* p3 = createDog("Polonije");

    animalPrintGreeting(p1);
    animalPrintGreeting(p2);
    animalPrintGreeting(p3);

    animalPrintMenu(p1);
    animalPrintMenu(p2);
    animalPrintMenu(p3);

    free(p1); free(p2); free(p3);
}

char const* dogGreet(void)
{
    return "woof!";
}

char const* dogMenu(void)
{
    return "boiled beef";
}

char const* catGreet(void)
{
    return "meow!";
}

char const* catMenu(void)
{
    return "canned tuna";
}

struct Animal** creteMultipleDogs(int n)
{
    struct Animal** dogs = (struct Animal**) malloc(n * sizeof(struct Animal*));

    for (int i = 0; i < n; i++)
    {
        dogs[i] = (struct Animal*) malloc(sizeof (struct Animal));
        constructDog(dogs[i], DEFAULT_DOG_NAME);
    }

    return dogs;
}