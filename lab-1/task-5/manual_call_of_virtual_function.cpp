#include <cstdio>
#define X 45

class B {
public:
    virtual int first()=0;
    virtual int second(int)=0;
};

class D: public B {
public:
    virtual int first() { return 42; }
    virtual int second(int x) { return first()+x; }
};

void manual_call(B* pb)
{
    unsigned long *vtable =  *(unsigned long**)pb;

    printf("First  function result: %d\n", ((int(*)(void*))vtable[0])(pb));
    printf("Second function result: %d\n", ((int(*)(void*, int))vtable[1])(pb, X));
}

int main()
{
    B *d = new D();
    manual_call(d);
    delete d;
    return 0;
}