//
// Created by user on 12/27/2023.
//
#include <stdlib.h>
#include "sftp_acl.h"


void cpy_aces(struct sftp_ace *old, struct sftp_ace *new, ace_cnt new_cnt) {
    for (int i = 1; i < new_cnt; i++) {
        new[i] = old[i];
    }
}

/**
 * Set new ACE to the ACL of path specified.
 * This function add only a single new ACE at a time.
 * @param path A path for adding new ACE.
 * @param ace A new ACE.
 * @return 0 if succeed, and non-zero if failed.
 */
extern int set_ace(const char *path, struct sftp_ace *ace) {
    //Get existing ACL from the path.
    struct sftp_acl *acl_ptr = malloc(sizeof(struct sftp_acl)); //Alloc mem for ACL
    size_t size = get_xattr_size(path); //Get the size of the ACL of specified path
    unsigned char *buf = malloc(sizeof(char) * size); //Alloc mem for tmp buf.
    int res = get_sftp_acl(path, buf);
    if (res != 0)
        goto exception;
    int decode_res = decode_acl(buf, acl_ptr);
    if (decode_res != 0)
        goto exception;

    //Add a new ACE to the ACL that was just decoded.
    struct sftp_ace *old_ace_arr = acl_ptr->ace_arr_ptr;
    ace_cnt new_cnt = acl_ptr->cnt + 1;
    struct sftp_ace *new_ace_arr = malloc(new_cnt * sizeof(struct sftp_ace));
    new_ace_arr[0] = *ace; //Shallow copy.
    cpy_aces(old_ace_arr, new_ace_arr, new_cnt); //Cpy the contents of the old arr to the new.
    acl_ptr->ace_arr_ptr = new_ace_arr;
    free(old_ace_arr); // Release the old ACE arr since it is no longer in use

    //Set the modified ACL to the path given.
    set_sftp_acl(path, acl_ptr, XATTR_REPLACE);
    //Free all the allocated mem that are no longer in use.
    free(buf);
    free(new_ace_arr);
    free(acl_ptr);
    return 0;

    exception:
    free(buf);
    free(acl_ptr);
    throw_exception(__FUNCTION__, __LINE__, __FILE__, "Something went wrong");
    return -1;
}