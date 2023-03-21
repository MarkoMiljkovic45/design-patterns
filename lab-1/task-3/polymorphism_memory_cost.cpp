#include <cstdio>

class CoolClass{
public:
    virtual void set(int x){x_=x;};
    virtual int get(){return x_;};
private:
    int x_;
};

class PlainOldClass{
public:
    void set(int x){x_=x;};
    int get(){return x_;};
private:
    int x_;
};

int main() {
    printf("sizeof(CoolClass)    : %lu\n", sizeof(CoolClass));
    printf("sizeof(PlainOldClass): %lu\n", sizeof(PlainOldClass));

    return 0;
}