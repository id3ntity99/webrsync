//
// Created by user on 12/27/2023.
//
#include <stdlib.h>
#include "sftp_acl.h"

/**
 * Create new ACE. The result must be released by using free().
 * @param type ACE Type
 * @param flag ACE Flag
 * @param mask ACE Mask
 * @param who ACE Who
 * @return A pointer to struct sftp_ace. This pointer MUST be released, because it is created using malloc().
 */
extern struct sftp_ace *create_new_ace(int type, int flag, int mask, const unsigned char *who) {
    struct sftp_ace *ace_ptr = malloc(sizeof(struct sftp_ace));
    ace_ptr->type = type;
    ace_ptr->flag = flag;
    ace_ptr->mask = mask;
    ace_ptr->who_ptr = who;
    return ace_ptr;
}
