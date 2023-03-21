#include <stdbool.h>

typedef void (*PTRFUN)();

typedef struct {
    PTRFUN *vtable;
    int lower_bound;
    int upper_bound;
} Unary_Function;

//Constructor
void construct_unary_function(Unary_Function*, int, int);
Unary_Function* create_unary_function(int, int);

//Destructor
void delete_unary_function(Unary_Function*);

//Virtual functions
//double unary_function_value_at(Unary_Function*, double);
double negative_value_at(Unary_Function*, double);

//Non-virtual functions
void tabulate(Unary_Function*);

//Static functions
bool same_functions_for_ints(Unary_Function*, Unary_Function*, double);