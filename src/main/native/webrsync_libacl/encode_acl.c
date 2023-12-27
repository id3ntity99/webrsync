//
// Created by lee on 10/28/23.
//

#include <stdio.h>
#include <string.h>
#include "sftp_acl.h"

void convert_le_bytes(unsigned char *bytes, u_int32_t n) {
    bytes[0] = n & 0xFF;
    bytes[1] = (n >> 8) & 0xFF;
    bytes[2] = (n >> 16) & 0xFF;
    bytes[3] = (n >> 24) & 0xFF;
}

void write_uint32_le(u_int32_t n, unsigned char **pos, size_t *size_ptr) {
    unsigned char bytes[4];
    convert_le_bytes(bytes, n);
    for (int i = 0; i < sizeof(bytes); i++) {
        **pos = bytes[i];
        (*pos)++; // Move pointer by one
        (*size_ptr)++;//Increment the size by one byte
    }
}

void write_char_le(const unsigned char *s, unsigned char **pos, size_t *size_ptr) {
    size_t len = strlen(s) + 1; // Add 1 byte to the result of strlen for the null terminator.
    for (int i = 0; i < len; i++) {
        (**pos) = s[i];
        (*pos)++;
        (*size_ptr)++;
    }
}

void encode_acl_ace_arr(struct sftp_ace *ace_arr_ptr, ace_cnt cnt, unsigned char **pos, size_t *size_ptr) {
    for (int i = 0; i < cnt; i++) {
        struct sftp_ace *current = ((struct sftp_ace *) &(ace_arr_ptr[i]));
        write_uint32_le(current->type, pos, size_ptr);
        write_uint32_le(current->flag, pos, size_ptr);
        write_uint32_le(current->mask, pos, size_ptr);
        write_char_le(current->who_ptr, pos, size_ptr);
    }
}

void raise_null_acl_exception() {
    printf("Something went wrong. struct sftp_acl *acl cannot be NULL.\n");
}

/**
 * Encode acl into buffer.
 * @param buf Pointer to an array of unsigned characters.
 * @return 0 if encode succeed, -1 if encode failed.
 */
extern int encode_acl(struct sftp_acl *acl, unsigned char *buf, size_t buf_len) {
    if (acl == NULL) {
        raise_null_acl_exception();
        goto failed;
    }
    size_t encoded_size = 0;
    unsigned char *curr_pos = buf;
    write_uint32_le(acl->flag, &curr_pos, &encoded_size);
    write_uint32_le(acl->cnt, &curr_pos, &encoded_size);
    encode_acl_ace_arr(acl->ace_arr_ptr, acl->cnt, &curr_pos, &encoded_size);
    if (buf_len != encoded_size)
        goto failed;
    return (int) encoded_size;

    failed:
    buf = NULL;
    return -1;
}
