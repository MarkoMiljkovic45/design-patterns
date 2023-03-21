#include <stdio.h>

class Base {
public:
    Base() { method(); }

    virtual void virtualMethod() { printf("I'm the base implementation\n"); }

    void method()
    {
        printf("Method says: ");
        virtualMethod();
    }
};

class Derived: public Base {
public:
    Derived(): Base() { method(); }

    virtual void virtualMethod() { printf("I'm the derived implementation\n"); }
};

int main()
{
    Derived* pd = new Derived();
    pd->method();
}