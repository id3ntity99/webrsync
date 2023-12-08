//
// Created by user on 12/8/2023.
//
#include <stdio.h>
#include <string.h>

#define RESET   "\033[0m"
#define RED     "\033[31m"

extern void throw_exception(const char *func_name, int lineno, char *file_name, char *msg) {
    printf("\n");
    printf(RED "[NATIVE] Exception in \"%s\" (%s:%d):\n" RESET, func_name, file_name, lineno);
    printf(RED "[NATIVE] %s\n" RESET, msg);
}

extern void throw_exception_errno(const char *func_name, int lineno, char *file_name, int err_num, char *msg) {
    throw_exception(func_name, lineno, file_name, msg);
    printf(RED "[NATIVE] %s\n" RESET, strerror(err_num));
}
