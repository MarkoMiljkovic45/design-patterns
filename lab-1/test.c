#include <stdio.h>
#include <stdlib.h>

typedef char const* (*PTRFUN)();

char const* foo1() {
    return "foo1";
}

char const* foo2() {
    return "foo2";
}

struct Animal {
    PTRFUN *foo;
    char* name;
};

PTRFUN foo[] = {foo1, foo2};

int main(void) {
    struct Animal a;

    a.foo = foo;

    printf("%s\n%s\n", a.foo[0](), a.foo[1]());
}