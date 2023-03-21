cmake_minimum_required(VERSION 3.24)
project(lab-1 C)

set(CMAKE_C_STANDARD 11)

add_executable(task-1/polymorphism.c task-2/polymorphism.h task-2/main.c task-2/unary_function.c task-2/unary_function.h task-2/square.c task-2/square.h task-2/linear.c task-2/linear.h task-3/polymorphism_memory_cost.cpp task-5/manual_call_of_virtual_function.cpp task-6/object_construction.cpp)
