//
// Created by lee on 11/3/23.
//
#include <sys/errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/xattr.h>
#include <string.h>
#include "sftp_acl.h"

extern size_t get_xattr_size(const char *path) {
    ssize_t read = getxattr(path, SFX_ACL_XATTR_NAME, NULL, 0);
    if (read == -1) {
        char *msg = "Something went wrong";
        throw_exception_errno(__func__, __LINE__, __FILE__, errno, msg);
    }
    return read;
}

extern int get_sftp_acl(const char *path, unsigned char *buf) {
    if (buf == NULL || path == NULL) {
        char *msg = "Either const char *path or unsigned char *buf can be NULL.\n";
        throw_exception(__func__, __LINE__, __FILE__, msg);
        goto free_failed;
    }
    size_t len = get_xattr_size(path);
    ssize_t read = getxattr(path, SFX_ACL_XATTR_NAME, buf, len);
    if (read == -1) {
        char *msg = "Something went wrong while getting an ACL";
        throw_exception_errno(__func__ , __LINE__, __FILE__, errno, msg);
        goto free_failed;
    }
    return 0;

    free_failed:
    free(buf);
    buf = NULL;
    return -1;
}
