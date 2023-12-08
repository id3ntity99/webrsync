//
// Created by lee on 12/8/23.
//

#include <stdio.h>
#include <string.h>
#include <sys/errno.h>
#include <sys/xattr.h>
#include "sftp_acl.h"

extern ssize_t does_acl_exist(const char *path) {
    ssize_t read = listxattr(path, NULL, 0);
    if (read == -1) {
        char *msg = "Someting went wrong";
        throw_exception_errno(__func__, __LINE__, __FILE__, errno, msg);
    }
    return read;
}
