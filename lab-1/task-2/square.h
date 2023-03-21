typedef void (*PTRFUN)();

typedef struct {
    PTRFUN *vtable;
    int lower_bound;
    int upper_bound;
} Square;

//Constructor
void construct_square(Square*, int, int);
Square* create_square(int, int);

//Destructor
void destroy_square(Square*);

//Virtual functions
double square_value_at(Square*, double);
//double negative_value_at(Square*, double);
