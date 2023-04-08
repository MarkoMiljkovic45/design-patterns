typedef void (*PTRFUN)();

typedef struct {
    PTRFUN *vtable;
    int lower_bound;
    int upper_bound;
    double a;
    double b;
} Linear;

void construct_linear(Linear *, int, int, double, double);
Linear* create_linear(int, int, double, double);
void destroy_linear(Linear *);
double linear_value_at(Linear*, double);
