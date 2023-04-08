#include "linear.h"
#include "unary_function.h"
#include <stdlib.h>

PTRFUN linear_vtable[] = {(PTRFUN) linear_value_at, (PTRFUN) negative_value_at};

void construct_linear(Linear * linear, int lower_bound, int upper_bound, double a, double b)
{
    linear->vtable      = linear_vtable;
    linear->lower_bound = lower_bound;
    linear->upper_bound = upper_bound;
    linear->a           = a;
    linear->b           = b;
}

Linear* create_linear(int lower_bound, int upper_bound, double a, double b)
{
    Linear *linear = (Linear*) malloc(sizeof (Linear));
    construct_linear(linear, lower_bound, upper_bound, a, b);

    return linear;
}

void destroy_linear(Linear * linear)
{
    free(linear);
}

double linear_value_at(Linear * linear, double x)
{
    return linear->a * x + linear->b;
}
