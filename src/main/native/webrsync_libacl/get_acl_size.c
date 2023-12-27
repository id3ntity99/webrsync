//
// Created by lee on 10/28/23.
//

#include <string.h>
#include "sftp_acl.h"

int get_single_ace_len(struct sftp_ace *ace) {
    const int type_len = sizeof(ace_type);
    const int flag_len = sizeof(ace_flag);
    const int mask_len = sizeof(ace_mask);
    const int who_len = (int) strlen((char *)ace->who_ptr) + 1;

    return type_len + flag_len + mask_len + who_len;
}

int get_all_ace_len(struct sftp_ace *ace_arr, ace_cnt cnt) {
    int len = 0;
    for (int i = 0; i < cnt; i++) {
        len += get_single_ace_len(ace_arr + i);
    }
    return len;
}

/**
 * Size of ACL = 8 + cnt * (12 + n) bytes.
 * 8 bytes come from the sum of the size of ACL flag and acl ace count. (12+n) bytes come from the sum of
 * ACE type, flag, mask and the size of who, which is a string with n-length.
 * @param acl
 * @return
 */
extern size_t get_acl_size(struct sftp_acl *acl) {
    size_t all_ace_len = get_all_ace_len(acl->ace_arr_ptr, acl->cnt);
    return sizeof(acl_flag) + sizeof(ace_cnt) + all_ace_len;
}
