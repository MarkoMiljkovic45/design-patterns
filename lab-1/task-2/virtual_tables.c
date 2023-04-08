#include <stdio.h>
#include <stdbool.h>
#include "unary_function.h"
#include "square.h"
#include "linear.h"

bool same_functions_for_ints(Unary_Function* f1, Unary_Function* f2, double tolerance)
{
    double (*value_at_f1)(double) = (double(*)(double)) f1->vtable[0];
    double (*value_at_f2)(double) = (double(*)(double)) f2->vtable[0];

    if(f1->lower_bound != f2->lower_bound) return false;
    if(f1->upper_bound != f2->upper_bound) return false;
    for(int x = f1->lower_bound; x <= f1->upper_bound; x++) {
        double delta = value_at_f1(x) - value_at_f2(x);
        if(delta < 0) delta = -delta;
        if(delta > tolerance) return false;
    }
    return true;
}

int main()
{

    Unary_Function *f1 = (Unary_Function*) create_square(-2, 2);
    tabulate(f1);

    Unary_Function *f2 = (Unary_Function*) create_linear(-2, 2, 5, -2);
    tabulate(f2);

    printf("f1==f2: %s\n", same_functions_for_ints(f1, f2, 1E-6) ? "YES" : "NO");

    double (*f2_negative_value)(Unary_Function *, double) = (double(*)(Unary_Function *, double)) f2->vtable[1];
    printf("neg_val f2(1) = %lf\n", f2_negative_value(f2, 1.0));

    destroy_linear((Linear*) f1);
    destroy_linear((Linear*) f2);

    return 0;
}