#include <iostream>
#include <list>
#define DELTA 5

using namespace std;

class Point {
    int x;
    int y;
};

class Shape {
public:
    virtual void draw() = 0;
    virtual void move(int delta) = 0;
};

class Circle: public Shape {
public:
    virtual void draw() {
        cerr <<"in drawCircle\n";
    }

    virtual void move(int delta) {
        cerr <<"in moveCircle\n";
    }

    double radius_;
    Point center_;
};

class Square: public Shape {
public:
    virtual void draw() {
        cerr <<"in drawSquare\n";
    }

    virtual void move(int delta) {
        cerr <<"in moveSquare\n";
    }

    double side_;
    Point center_;
};

class Rhomb: public Shape {
public:
    virtual void draw() {
        cerr <<"in drawRhomb\n";
    }

    virtual void move(int delta) {
        cerr <<"in moveRhomb\n";
    }

    double side_;
    Point center_;
};

void drawShapes(const list<Shape*>& shapes) {
    list<Shape*>::const_iterator it;

    for (it=shapes.begin(); it != shapes.end(); it++) {
        (*it)->draw();
    }
}

void moveShapes(const list<Shape*>& shapes, int delta) {
    list<Shape*>::const_iterator it;

    for (it=shapes.begin(); it != shapes.end(); it++) {
        (*it)->move(delta);
    }
}

int main() {
    list<Shape*> shapes = {new Circle, new Square, new Square, new Circle, new Rhomb};

    drawShapes(shapes);
    moveShapes(shapes, DELTA);
}