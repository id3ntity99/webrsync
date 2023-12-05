// C program for webrsync project. This program is delegated to call stat() system call and return the result as
// java byte array.
#include "com_github_webrsync_sftp_SftpFileAttributes.h"

#include <string.h>
#include <sys/stat.h>
#include <stdlib.h>
#include <unistd.h>
#include <pwd.h>

jintArray convert_to_jint_arr(JNIEnv *env, int len, int *arr) {
    jintArray j_arr = (*env)->NewIntArray(env, len);
    (*env)->SetIntArrayRegion(env, j_arr, 0, len, arr);
    return j_arr;
}

jbyteArray convert_to_jbyte_arr(JNIEnv *env, int len, char *arr) {
    jbyteArray j_bArray = (*env)->NewByteArray(env, len);
    (*env)->SetByteArrayRegion(env, j_bArray, 0, len, (jbyte *) arr);
    return j_bArray;
}

jintArray encode(JNIEnv *env, struct stat stats, int buf_size) {
    const int len = 13;
    int *buf = malloc(sizeof(int) * len);
    buf[0] = (int) stats.st_dev;
    buf[1] = (int) stats.st_ino;
    buf[2] = (int) stats.st_mode;
    buf[3] = (int) stats.st_nlink;
    buf[4] = (signed int) stats.st_uid;
    buf[5] = (signed int) stats.st_gid;
    buf[6] = (int) stats.st_rdev;
    buf[7] = (signed int) stats.st_size;
    buf[8] = (signed int) stats.st_blksize;
    buf[9] = (signed int) stats.st_blocks;
    buf[10] = (int) stats.st_atim.tv_sec;
    buf[11] = (int) stats.st_mtim.tv_sec;
    buf[12] = (int) stats.st_ctim.tv_sec;
    jintArray j_iArray = convert_to_jint_arr(env, len, buf);
    free(buf);
    return j_iArray;
}

struct stat call_stat(JNIEnv *env, jstring uri) {
    struct stat stats;
    char const *path = (*env)->GetStringUTFChars(env, uri, 0);
    stat(path, &stats);
    (*env)->ReleaseStringUTFChars(env, uri, path);
    return stats;
}

void print_arr(char *arr, int len) {
    for (int i = 0; i < len; i++)
        printf("%c", arr[i]);
    printf("\n");
}

JNIEXPORT jintArray JNICALL Java_com_github_webrsync_sftp_SftpFileAttributes_stat
        (JNIEnv *env, jobject java_obj, jstring uri) {
    struct stat stat_result = call_stat(env, uri);
    jintArray encoded = encode(env, stat_result, 256);
    return encoded;
}

JNIEXPORT jbyteArray JNICALL Java_com_github_webrsync_sftp_SftpFileAttributes_getLocalDomainName
        (JNIEnv *env, jobject java_obj) {
    const int n_entry = 128;
    const int c_size = sizeof(char);
    const int bytes = n_entry * c_size;
    char *name = calloc(n_entry, c_size);
    getdomainname(name, n_entry);
    int name_len = (int) strlen(name);
    jbyteArray j_bArray = convert_to_jbyte_arr(env, name_len, name);
    free(name);
    return j_bArray;
}

JNIEXPORT jbyteArray JNICALL Java_com_github_webrsync_sftp_SftpFileAttributes_getOwnerName
        (JNIEnv *env, jobject java_obj, jint uid) {
    struct passwd *password = getpwuid(uid);
    char *name = password->pw_name;
    int name_len = (int) strlen(name);
    jbyteArray j_bArray = convert_to_jbyte_arr(env, name_len, name);
    free(name);
    return j_bArray;
}
