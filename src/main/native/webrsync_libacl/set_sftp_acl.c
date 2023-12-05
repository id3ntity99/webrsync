//
// Created by lee on 11/2/23.
//
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <stdio.h>
#include "sftp_acl.h"

extern int set_sftp_acl(const char *path, struct sftp_acl *acl, int xattr_flag) {
    size_t acl_size = get_acl_size(acl);
    unsigned char *buf = (unsigned char *) malloc(sizeof(char) * acl_size);
    int encode_res = encode_acl(acl, buf, acl_size);
    if (encode_res == -1) {
        printf("%s\n", "[NATIVE]Encoding failed");
        goto failed_free;
    }
    int result = setxattr(path, SFX_ACL_XATTR_NAME, buf, acl_size, xattr_flag);
    if (result == -1) {
        printf("[NATIVE]Exception raised: %s(Status code = %d)\n", strerror(errno), errno);
        goto failed_free;
    }
    return result;

    failed_free:
    free(buf);
    buf = NULL;
    return -1;
}
