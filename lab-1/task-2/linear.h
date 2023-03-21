typedef void (*PTRFUN)();

typedef struct {
    PTRFUN *vtable;
    int lower_bound;
    int upper_bound;
    double a;
    double b;
} Linear;

//Constructor
void construct_linear(Linear *, int, int, double, double);
Linear* create_linear(int, int, double, double);

//Destructor
void destroy_linear(Linear *);

//Virtual functions
double linear_value_at(Linear*, double);
//double negative_value_at(Linear*, double);
