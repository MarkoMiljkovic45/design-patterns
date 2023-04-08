#include <stdio.h>
#include <string.h>

/*
 * The compar function is used to compare the elements of the array
 * It should return 1 if the first element is greater than the second and 0 otherwise
 */
const void* mymax(const void *base, size_t nmemb, size_t size, int (*compar)(const void *, const void *)) {
    if (nmemb <= 0) return NULL;

    const void* max  = base;
    size_t offset = size;

    for(int i = 1; i < nmemb; i++) {
        const void* curr = base + offset;

        if (compar(max, curr) != 1) {
            max = curr;
        }

        offset += size;
    }

    return max;
}

int gt_int(const void* first, const void* second) {
    return *((int*) first) > *((int*) second) ? 1 : 0;
}

int gt_char(const void* first, const void* second) {
    return *((char*) first) > *((char*) second) ? 1 : 0;
}

int gt_str(const void* first, const void* second) {
    return strcmp(*((char**) first), *((char**) second)) > 0 ? 1 : 0;
}

void forEach(const void* base, size_t nmemb, size_t size, void (*consumer)(const void*)) {
    size_t offset = 0;

    for (int i = 0; i < nmemb; i++) {
        consumer(base + offset);
        offset += size;
    }
}

void printInt(const void* num) {
    printf("%d ", *((int*) num));
}

void printChar(const void* ch) {
    printf("'%c' ", *((char*) ch));
}

void printStr(const void* str) {
    printf("%s ", *((char**) str));
}

int main() {
    int arr_int[] = { 1, 3, 5, 7, 4, 6, 9, 2, 0 };
    printf("[ ");
    forEach(arr_int, 9, sizeof(int), printInt);
    printf("]\n");
    printf("Max: %d\n", *(int*) mymax(arr_int, 9, sizeof(int), gt_int));

    char arr_char[]="Suncana strana ulice";
    printf("\n[ ");
    forEach(arr_char, 21, sizeof(char), printChar);
    printf("]\n");
    printf("Max: '%c'\n", *(char*) mymax(arr_char, 21, sizeof(char), gt_char));

    const char* arr_str[] = {
            "Gle", "malu", "vocku", "poslije", "kise",
            "Puna", "je", "kapi", "pa", "ih", "njise"
    };
    printf("\n[ ");
    forEach(arr_str, 11, sizeof(char*), printStr);
    printf("]\n");
    printf("Max: %s\n", *(char**) mymax(arr_str, 11, sizeof(char*), gt_str));
}