//
// Created by user on 12/27/2023.
//
#include <sys/stat.h>
#include <stdbool.h>

extern bool is_dir(const char *path) {
    struct stat stats;
    stat(path, &stats);
    return (stats.st_mode & S_IFMT) == S_IFDIR;
}