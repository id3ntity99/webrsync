//
// Created by lee on 10/28/23.
//

#include <sys/types.h>
#include <sys/xattr.h>
#include <stdbool.h>

#ifndef JNI_NFCACL_WEBRSYNC_ACL_H
#define JNI_NFCACL_WEBRSYNC_ACL_H

#endif //JNI_NFCACL_WEBRSYNC_ACL_H

typedef u_int32_t acl_flag;
typedef u_int32_t ace_cnt;
typedef u_int32_t ace_type;
typedef u_int32_t ace_flag;
typedef u_int32_t ace_mask;

/**
 * See SFTP Specification, section 7.8 "ACL".
 */
struct sftp_ace {
    ace_type type;
    ace_flag flag;
    ace_mask mask;
    const unsigned char *who_ptr;
};

struct sftp_acl {
    acl_flag flag;
    ace_cnt cnt;
    struct sftp_ace *ace_arr_ptr;
};


//ACE types:
#define ACE4_ACCESS_ALLOWED_ACE_TYPE    0x00000000
#define ACE4_ACCESS_DENIED_ACE_TYPE     0x00000001
#define ACE4_SYSTEM_AUDIT_ACE_TYPE      0x00000002
#define ACE4_SYSTEM_ALARM_ACE_TYPE      0x00000003

//ACE flag:
#define ACE4_FILE_INHERIT_ACE           0x00000001
#define ACE4_DIRECTORY_INHERIT_ACE      0x00000002
#define ACE4_NO_PROPAGATE_INHERIT_ACE   0x00000004
#define ACE4_INHERIT_ONLY_ACE           0x00000008
#define ACE4_SUCCESSFUL_ACCESS_ACE_FLAG 0x00000010
#define ACE4_FAILED_ACCESS_ACE_FLAG     0x00000020
#define ACE4_IDENTIFIER_GROUP           0x00000040

//ACE mask
#define ACE4_READ_DATA                  0x00000001
#define ACE4_LIST_DIRECTORY             0x00000001
#define ACE4_WRITE_DATA                 0x00000002
#define ACE4_ADD_FILE                   0x00000002
#define ACE4_APPEND_DATA                0x00000004
#define ACE4_ADD_SUBDIRECTORY           0x00000004
#define ACE4_READ_NAMED_ATTRS           0x00000008
#define ACE4_WRITE_NAMED_ATTRS          0x00000010
#define ACE4_EXECUTE                    0x00000020
#define ACE4_DELETE_CHILD               0x00000040
#define ACE4_READ_ATTRIBUTES            0x00000080
#define ACE4_WRITE_ATTRIBUTES           0x00000010
#define ACE4_DELETE                     0x00010000
#define ACE4_READ_ACL                   0x00020000
#define ACE4_WRITE_ACL                  0x00040000
#define ACE4_WRITE_OWNER                0x00080000
#define ACE4_SYNCHRONIZE                0x00100000

//ACL flag
#define SFX_ACL_CONTROL_INCLUDED        0x00000001
#define SFX_ACL_CONTROL_PRESENT         0x00000002
#define SFX_ACL_CONTROL_INHERITED       0x00000004
#define SFX_ACL_AUDIT_ALARM_INCLUDED    0x00000010

//xattr name
#define SFX_ACL_XATTR_NAME "user.sftp_acl"

// webrsync class paths and constructor parameters
#define PACKAGE_PATH "com/github/webrsync/sftp/fs/"


#define ACL_FLAGS_CLASS_PATH PACKAGE_PATH "AclFlags"
#define ACL_FLAGS_FIELD_TYPE "L"ACL_FLAGS_CLASS_PATH";"
#define ACL_FLAGS_PARAMS "(I)V"

#define ACE_CLASS_PATH PACKAGE_PATH "AccessControlEntry"
#define ACE_PARAMS "(Lcom/github/webrsync/sftp/fs/AceType;Lcom/github/webrsync/sftp/fs/AceFlag;Lcom/github/webrsync/sftp/fs/AceMask;Ljava/lang/String;)V"
#define ACE_FIELD_TYPE "L"ACE_CLASS_PATH";"
#define ACE_ARRAY_FIELD_TYPE "["ACE_FIELD_TYPE

#define ACE_TYPE_CLASS_PATH PACKAGE_PATH "AceType"
#define ACE_TYPE_FIELD_TYPE "L"ACE_TYPE_CLASS_PATH";"
#define ACE_TYPE_PARAMS "(I)V"

#define ACE_FLAG_CLASS_PATH PACKAGE_PATH "AceFlag"
#define ACE_FLAG_FIELD_TYPE "L"ACE_FLAG_CLASS_PATH";"
#define ACE_FLAG_PARAMS "(I)V"

#define ACE_MASK_CLASS_PATH PACKAGE_PATH "AceMask"
#define ACE_MASK_FIELD_TYPE "L"ACE_MASK_CLASS_PATH";"
#define ACE_MASK_PARAMS "(I)V"

#define ACL_CLASS_PATH PACKAGE_PATH "AccessControlList"
#define ACL_PARAMS "(Lcom/github/webrsync/sftp/fs/AclFlags;I[Lcom/github/webrsync/sftp/fs/AccessControlEntry;)V"


// Functions
extern size_t get_acl_size(struct sftp_acl *acl);

extern void print_bytes(unsigned char *buf, size_t size);

extern int encode_acl(struct sftp_acl *acl, unsigned char *buf, size_t buf_len);

extern int set_sftp_acl(const char *path, struct sftp_acl *acl, int xattr_flag);

extern int get_sftp_acl(const char *path, unsigned char *buf);

extern size_t get_xattr_size(const char *path);

extern int decode_acl(unsigned char *buf, struct sftp_acl *acl_ptr);

extern ssize_t does_acl_exist(const char *path);

extern void throw_exception(const char *func_name, int lineno, char *file_name, char *msg);

extern void throw_exception_errno(const char *func_name, int lineno, char *file_name, int err_num, char *msg);

extern bool is_dir(const char *path);

extern struct sftp_ace *create_new_ace(int type, int flag, int mask, const unsigned char *who);

extern struct sftp_acl *create_new_acl(acl_flag flag, ace_cnt ace_cnt, struct sftp_ace *ace_arr_ptr);