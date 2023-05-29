#include "main.h"
#include "myfactory.h"

#include <stdio.h>
#include <stdlib.h>
#include <alloca.h>

void animalPrintGreeting(Animal* animal);
void animalPrintMenu(Animal* animal);

int main(int argc, char *argv[])
{
  for (int i = 0; i < argc/2; ++i) {
    Animal* p = (Animal*) myfactory(argv[1+2*i], argv[1+2*i+1]);
    Animal* s = (Animal*) alloca(size(argv[1+2*i]));
    construct(argv[1+2*i], argv[1+2*i+1], s);

    if (!p) {
      printf("Creation of plug-in object %s failed.\n", argv[1+2*i]);
      continue;
    }

    animalPrintGreeting(p);
    animalPrintMenu(p);
    free(p);

    printf("Stack versions:\n");
    animalPrintGreeting(s);
    animalPrintMenu(s);
    //No need to free object on stack

    printf("\n");
  }
}



void animalPrintGreeting(Animal* animal)
{
    printf("%s greets: %s!\n", animal->vtable[0](animal), animal->vtable[1]());
}

void animalPrintMenu(Animal* animal)
{
    printf("%s likes: %s!\n", animal->vtable[0](animal), animal->vtable[2]());
}