typedef void (*PTRFUN)();

typedef struct {
    PTRFUN *vtable;
    int lower_bound;
    int upper_bound;
} Unary_Function;

void construct_unary_function(Unary_Function*, int, int);
Unary_Function* create_unary_function(int, int);
void delete_unary_function(Unary_Function*);
double negative_value_at(Unary_Function*, double);
void tabulate(Unary_Function*);