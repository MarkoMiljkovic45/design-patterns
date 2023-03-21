#include "square.h"
#include "unary_function.h"
#include <stdlib.h>

PTRFUN square_vtable[] = {(PTRFUN) square_value_at, (PTRFUN) negative_value_at};

void construct_square(Square* square, int lower_bound, int upper_bound)
{
    square->vtable      = square_vtable;
    square->lower_bound = lower_bound;
    square->upper_bound = upper_bound;
}

Square* create_square(int lower_bound, int upper_bound)
{
    Square *square = (Square*) malloc(sizeof (Square));
    construct_square(square, lower_bound, upper_bound);

    return square;
}

void destroy_square(Square* square)
{
    free(square);
}

double square_value_at(Square * square, double x)
{
    return x * x;
}