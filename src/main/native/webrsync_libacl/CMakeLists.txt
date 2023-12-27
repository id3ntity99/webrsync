cmake_minimum_required(VERSION 3.26)
project(webrsync_sftp_acl C)

find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})


set(CMAKE_C_STANDARD 11)

add_library(webrsync_sftp_acl SHARED
        sftp_acl.h
        decode_acl.c
        encode_acl.c
        get_acl_size.c
        get_sftp_acl.c
        set_sftp_acl.c
        com_github_webrsync_sftp_fs_AclManager.c
        com_github_webrsync_sftp_fs_AclManager.h
        does_acl_exist.c
        throw_exception.c
        throw_exception.c
        is_directory.c
        is_directory.c
        do_file_inherit.c
        set_ace.c
        set_ace.c
        create_new_ace.c
        create_new_ace.c
        create_new_acl.c
        create_new_acl.c
)
