#include "myfactory.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <dlfcn.h>

void* getHandle(char const* libname)
{
	void *handle;
	char *path = (char*) malloc(strlen(libname) + 6); // ./ + libname + .so + \0
	strcpy(path, "./");
	strcat(path, libname);
	strcat(path, ".so\0");

	handle = dlopen(path, RTLD_LAZY);
	free(path);	

	if (!handle) {
	   return NULL;
	}

	return handle;
}

void* myfactory(char const* libname, char const* ctorarg)
{
	void* (*creator)(char const*);
	void *handle = getHandle(libname);
	
	if (!handle) {
	   return NULL;
	}

	creator = (void* (*)(char const*)) dlsym(handle, "create");
		
	char *error = dlerror();
	if (error != NULL) {
	   fprintf(stderr, "%s\n", error);
	   exit(EXIT_FAILURE);
	}

	return creator(ctorarg);
}

int size(char const* libname)
{
	int (*sizeOfObject)();
	void *handle = getHandle(libname);
	
	if (!handle) {
	   return -1;
	}

	sizeOfObject = (int (*)()) dlsym(handle, "size");
		
	char *error = dlerror();
	if (error != NULL) {
	   fprintf(stderr, "%s\n", error);
	   exit(EXIT_FAILURE);
	}

	return sizeOfObject();
}

void construct(char const* libname, char const* ctorarg, void* mem)
{
	void (*constructor)(void*, char const*);
	void *handle = getHandle(libname);

	if (!handle) {
	   return;
	}

	constructor = (void (*)(void*, char const*)) dlsym(handle, "construct");

	char *error = dlerror();
	if (error != NULL) {
	   fprintf(stderr, "%s\n", error);
	   exit(EXIT_FAILURE);
	}

	constructor(mem, ctorarg);
}