#include <stdlib.h>
#include <stdio.h>
#include "unary_function.h"

PTRFUN unary_function_vtable[] = {NULL, (PTRFUN) negative_value_at};

void construct_unary_function(Unary_Function* uf, int lower_bound, int upper_bound)
{
    uf->vtable      = unary_function_vtable;
    uf->lower_bound = lower_bound;
    uf->upper_bound = upper_bound;
}

Unary_Function* create_unary_function(int lower_bound, int upper_bound)
{
    Unary_Function *uf = (Unary_Function*) malloc(sizeof (Unary_Function));
    construct_unary_function(uf, lower_bound, upper_bound);
    return uf;
}

void delete_unary_function(Unary_Function* uf)
{
    free(uf);
}

double negative_value_at(Unary_Function * uf, double x)
{
    double (*value_at)(double) = (double(*)(double)) uf->vtable[0];
    return -value_at(x);
}

void tabulate(Unary_Function* uf) {
    double (*value_at)(Unary_Function*, double) = (double(*)(Unary_Function*, double)) uf->vtable[0];

    for(int i = uf->lower_bound; i <= uf->upper_bound; i++)
    {
        printf("f(%d)=%lf\n", i, value_at(uf, i));
    }
}