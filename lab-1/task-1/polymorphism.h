char const* dogGreet(void);
char const* dogMenu(void);
char const* catGreet(void);
char const* catMenu(void);

typedef char const* (*PTRFUN)();

struct Animal {
    PTRFUN *vtable;
    char const* name;
};

void animalPrintGreeting(struct Animal*);
void animalPrintMenu(struct Animal*);

void constructDog(struct Animal*, char const*);
void constructCat(struct Animal*, char const*);

struct Animal* createDog(char const*);
struct Animal* createCat(char const*);

void testAnimals(void);

struct Animal** creteMultipleDogs(int);