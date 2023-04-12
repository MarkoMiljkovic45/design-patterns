#include <iostream>
#include <cstring>
#include <vector>
#include <set>

template <typename Iterator, typename Predicate>
Iterator mymax(Iterator first, Iterator last, Predicate pred) {
    Iterator max = first;

    while (first != last) {
        if (pred(*max, *first) != 1) {
            max = first;
        }
        first++;
    }

    return max;
}

int gt_int(const int a, const int b) {
    return a > b ? 1 : 0;
}

int gt_char(const char c1, const char c2) {
    return c1 > c2 ? 1 : 0;
}

int gt_str(const char *s1, const char *s2) {
    return strcmp(s1, s2) > 0 ? 1 : 0;
}

template <typename Iterator, typename Function>
void forEach(Iterator first, Iterator last, Function func) {
    while(first != last) {
        func(*first);
        first++;
    }
}

void printInt(const int num) {
    std::cout << num << " ";
}

void printChar(const char ch) {
    std::cout << "'" << ch << "' ";
}

void printSting(const char* str) {
    std::cout << str << " ";
}

int main() {
    int arr_int[] = { 1, 3, 5, 7, 4, 6, 9, 2, 0 };
    size_t arr_int_size = sizeof(arr_int) / sizeof(*arr_int);
    std::vector<int> vector_int(arr_int, arr_int + arr_int_size);
    std::set<int> set_int(arr_int, arr_int + arr_int_size);

    char arr_char[]="Suncana strana ulice";
    size_t arr_char_size = sizeof(arr_char) / sizeof(*arr_char);
    std::vector<char> vector_char(arr_char, arr_char + arr_char_size);
    std::set<char> set_char(arr_char, arr_char + arr_char_size);

    const char* arr_str[] = {
            "Gle", "malu", "vocku", "poslije", "kise",
            "Puna", "je", "kapi", "pa", "ih", "njise"
    };
    size_t arr_str_size = sizeof(arr_str) / sizeof(*arr_str);
    std::vector<const char*> vector_str(arr_str, arr_str + arr_str_size);
    std::set<const char*> set_str(arr_str, arr_str + arr_str_size);

    std::cout << "[ ";
    forEach(&arr_int[0], &arr_int[arr_int_size], printInt);
    std::cout << "]\n";
    std::cout << "Array  max: " << *mymax(&arr_int[0], &arr_int[arr_int_size], gt_int) << "\n";
    std::cout << "Vector max: " << *mymax(vector_int.begin(), vector_int.end(), gt_int) << "\n";
    std::cout << "Set    max: " << *mymax(set_int.begin(), set_int.end(), gt_int) << "\n";

    std::cout << "[ ";
    forEach(&arr_char[0], &arr_char[arr_char_size], printChar);
    std::cout << "]\n";
    std::cout << "Array  max: " << *mymax(&arr_char[0], &arr_char[arr_char_size], gt_char) << "\n";
    std::cout << "Vector max: " << *mymax(vector_char.begin(), vector_char.end(), gt_char) << "\n";
    std::cout << "Set    max: " << *mymax(set_char.begin(), set_char.end(), gt_char) << "\n";

    std::cout << "[ ";
    forEach(&arr_str[0], &arr_str[arr_str_size], printSting);
    std::cout << "]\n";
    std::cout << "Array  max: " << *mymax(&arr_str[0], &arr_str[arr_str_size], gt_str) << "\n";
    std::cout << "Vector max: " << *mymax(vector_str.begin(), vector_str.end(), gt_str) << "\n";
    std::cout << "Set    max: " << *mymax(set_str.begin(), set_str.end(), gt_str) << "\n";
}
