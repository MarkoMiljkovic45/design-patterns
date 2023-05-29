#include "main.h"

#include <stdlib.h>

char const* name(void* this);
char const* greet();
char const* menu();

typedef struct {
	PTRFUN* vtable;
	char const* name;
} Tiger;

PTRFUN tiger_vtable[] = {name, greet, menu};

int size()
{
	return sizeof(Tiger);
}

void construct(void* mem, char const* name)
{
	Tiger *tiger = (Tiger*) mem;

	tiger->vtable = tiger_vtable;
	tiger->name = name;
}

void* create(char const* name)
{
	Tiger* tiger = (Tiger*) malloc(sizeof(Tiger));
	construct(tiger, name);

	return tiger;
}

char const* name(void* this)
{
	Tiger* tiger = (Tiger*) this;
	return tiger->name;
}

char const* greet()
{
	return "roar";
}

char const* menu()
{
	return "meat";
}