#include "main.h"

#include <stdlib.h>

char const* name(void* this);
char const* greet();
char const* menu();

typedef struct {
	PTRFUN* vtable;
	char const* name;
} Parrot;

PTRFUN parrot_vtable[] = {name, greet, menu};

int size() {
	return sizeof(Parrot);
}

void construct(void* mem, char const* name)
{
	Parrot* parrot = (Parrot*) mem;

	parrot->vtable = parrot_vtable;
	parrot->name = name;
}

void* create(char const* name)
{
	Parrot* parrot = (Parrot*) malloc(sizeof(Parrot));
	construct(parrot, name);

	return parrot;
}

char const* name(void* this)
{
	Parrot* parrot = (Parrot*) this;
	return parrot->name;
}

char const* greet()
{
	return "gawk";
}

char const* menu()
{
	return "seeds";
}