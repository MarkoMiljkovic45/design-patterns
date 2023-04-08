typedef void (*PTRFUN)();

typedef struct {
    PTRFUN *vtable;
    int lower_bound;
    int upper_bound;
} Square;

void construct_square(Square*, int, int);
Square* create_square(int, int);
void destroy_square(Square*);
double square_value_at(Square*, double);
