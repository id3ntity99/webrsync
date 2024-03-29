/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_github_webrsync_sftp_fs_AclManager */

#ifndef _Included_com_github_webrsync_sftp_fs_AclManager
#define _Included_com_github_webrsync_sftp_fs_AclManager
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_github_webrsync_sftp_fs_AclManager
 * Method:    setSftpAcl
 * Signature: (Ljava/lang/String;Lcom/github/webrsync/sftp/fs/AccessControlList;Lcom/github/webrsync/sftp/fs/SetXattrFlag;)I
 */
JNIEXPORT jint JNICALL Java_com_github_webrsync_sftp_fs_AclManager_setSftpAcl
  (JNIEnv *, jclass, jstring, jobject, jobject);

/*
 * Class:     com_github_webrsync_sftp_fs_AclManager
 * Method:    getSftpAcl
 * Signature: (Ljava/lang/String;)Lcom/github/webrsync/sftp/fs/AccessControlList;
 */
JNIEXPORT jobject JNICALL Java_com_github_webrsync_sftp_fs_AclManager_getSftpAcl
  (JNIEnv *, jclass, jstring);

/*
 * Class:     com_github_webrsync_sftp_fs_AclManager
 * Method:    doesExist
 * Signature: (Ljava/lang/String;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_github_webrsync_sftp_fs_AclManager_doesExist
  (JNIEnv *, jclass, jstring);

#ifdef __cplusplus
}
#endif
#endif
