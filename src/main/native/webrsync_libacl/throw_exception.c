//
// Created by user on 12/8/2023.
//
#include <stdio.h>
#include <string.h>

extern void throw_exception(const char *func_name, int lineno, char *file_name, char *msg) {
    printf("[NATIVE] Exception in \"%s\" (%s:%d):\n", func_name, file_name, lineno);
    printf("[NATIVE] %s\n", msg);
}

extern void throw_exception_errno(const char *func_name, int lineno, char *file_name, int err_num, char *msg) {
    throw_exception(func_name, lineno, file_name, msg);
    printf("[NATIVE] %s(status code = %d)\n", strerror(err_num), err_num);
}
