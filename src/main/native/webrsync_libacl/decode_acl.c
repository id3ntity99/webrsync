//
// Created by lee on 11/12/23.
//
#include <stdlib.h>
#include <stdio.h>
#include "sftp_acl.h"

u_int32_t le_bytes_to_uint32(unsigned char *bytes) {
    u_int32_t uint32;
    uint32 = bytes[0] & 0xFFFFFFFF;
    uint32 = (bytes[1] << 8) | uint32;
    uint32 = (bytes[2] << 16) | uint32;
    uint32 = (bytes[3] << 24) | uint32;
    return uint32;
}

u_int32_t read_uint32(unsigned char **pos) {
    unsigned char bytes[4];
    for (int i = 0; i < 4; i++) {
        bytes[i] = **pos; // Get value stored in the current position(address) of memory
        ++(*pos); // Increment the current position by one
    }
    return le_bytes_to_uint32(bytes);
}

size_t get_str_len(unsigned char **pos) {
    unsigned char c = **pos; // An unsigned char c should be the first character of ace.who
    int cnt = 0;
    while (c != '\0') {
        c = *(*pos + cnt++); // Get value of a position
    }
    return cnt;
}

void read_string(unsigned char **pos, unsigned char *out, size_t len) {
    for (int i = 0; i < len; i++) {
        out[i] = **pos;
        ++(*pos);
    }
}

void decode_aces(struct sftp_ace *aces, acl_ace_cnt cnt, unsigned char **pos) {
    for (int i = 0; i < cnt; i++) {
        ace_type4 type = read_uint32(pos);
        ace_flag4 flag = read_uint32(pos);
        ace_mask4 mask = read_uint32(pos);
        size_t who_len = get_str_len(pos);
        unsigned char *who = malloc(sizeof(char) * who_len);
        read_string(pos, who, who_len);
        struct sftp_ace ace = {
                .type = type,
                .flag = flag,
                .mask = mask,
                .who_ptr = who
        };
        aces[i] = ace;
    }
}

extern int decode_acl(unsigned char *buf, struct sftp_acl *acl_ptr) {
    if (buf == NULL || acl_ptr == NULL) {
        printf("[NATIVE]Either unsigned char *buf or struct sftp_acl *acl_ptr can be NULL.\n");
        return -1;
    }
    unsigned char *pos = buf;
    acl_flags flags = read_uint32(&pos);
    acl_ace_cnt cnt = read_uint32(&pos);
    struct sftp_ace *ace_arr = malloc(sizeof(struct sftp_ace) * cnt);
    decode_aces(ace_arr, cnt, &pos);
    acl_ptr->flags = flags;
    acl_ptr->ace_cnt = cnt;
    acl_ptr->ace_arr_ptr = ace_arr;
    return 0;
}