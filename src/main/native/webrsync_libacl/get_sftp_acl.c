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
    return getxattr(path, SFX_ACL_XATTR_NAME, NULL, 0);
}

extern int get_sftp_acl(const char *path, unsigned char *buf) {
    if (buf == NULL || path == NULL) {
        printf("[NATIVE]Either const char *path or unsigned char *buf can be NULL.\n");
        goto free_failed;
    }
    size_t len = get_xattr_size(path);
    ssize_t read = getxattr(path, SFX_ACL_XATTR_NAME, buf, len);
    if (read == -1) {
        printf("[NATIVE]Exception raised while getting an ACL: %s(errno=%d)\n", strerror(errno), errno);
        goto free_failed;
    }
    return 0;

    free_failed:
    free(buf);
    buf = NULL;
    return -1;
}
