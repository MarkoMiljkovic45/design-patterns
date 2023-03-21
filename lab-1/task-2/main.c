#include "unary_function.h"
#include "square.h"
#include "linear.h"
#include <stdio.h>

int main() {

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