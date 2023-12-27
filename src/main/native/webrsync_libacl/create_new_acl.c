//
// Created by user on 12/27/2023.
//
#include <stdlib.h>
#include "sftp_acl.h"

extern struct sftp_acl *create_new_acl(acl_flag flag, ace_cnt ace_cnt, struct sftp_ace *ace_arr_ptr) {
    struct sftp_acl *acl = malloc(sizeof(struct sftp_acl));
    acl->flag = flag;
    acl->cnt = ace_cnt;
    acl->ace_arr_ptr = ace_arr_ptr;
    return acl;
}