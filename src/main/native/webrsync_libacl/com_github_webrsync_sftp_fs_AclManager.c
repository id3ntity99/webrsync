//
// Created by lee on 11/19/23.
//
#include <stdlib.h>
#include <string.h>
#include "sftp_acl.h"
#include "com_github_webrsync_sftp_fs_AclManager.h"

jfieldID get_fid(JNIEnv *env, jobject target_obj, char *field_name, char *field_type) {
    jclass targetClass = (*env)->GetObjectClass(env, target_obj);
    jfieldID fid = (*env)->GetFieldID(env, targetClass, field_name, field_type);
    (*env)->DeleteLocalRef(env, targetClass);
    return fid;
}

jint get_jint_field(JNIEnv *env, jobject target_obj, char *field_name) {
    jfieldID intFid = get_fid(env, target_obj, field_name, "I");
    jint jint_field = (*env)->GetIntField(env, target_obj, intFid);
    return jint_field;
}

jobject get_object_field(JNIEnv *env, jobject target_obj, char *field_name, char *field_type) {
    jfieldID fid = get_fid(env, target_obj, field_name, field_type);
    jobject obj_field = (*env)->GetObjectField(env, target_obj, fid);
    return obj_field;
}

jclass get_global_ref(JNIEnv *env, const char *classPath) {
    jclass class = (jclass) (*env)->FindClass(env, classPath);
    return (jclass) (*env)->NewGlobalRef(env, class);
}

jmethodID get_constructor(JNIEnv *env, jclass globalRef, const char *params) {
    return (*env)->GetMethodID(env, globalRef, "<init>", params);
}

void iterate_aces
        (JNIEnv *env, jint cnt, jobjectArray arr, struct sftp_ace *ace_arr_ptr) {
    for (int i = 0; i < cnt; i++) {
        jobject aceObj = (*env)->GetObjectArrayElement(env, arr, i); //element = AccessControlEntry
        jobject typeObj = get_object_field(env, aceObj, "type", ACE_TYPE_FIELD_TYPE);
        jobject flagObj = get_object_field(env, aceObj, "flag", ACE_FLAG_FIELD_TYPE);
        jobject maskObj = get_object_field(env, aceObj, "mask", ACE_MASK_FIELD_TYPE);
        jstring whoStrObj = get_object_field(env, aceObj, "who", "Ljava/lang/String;");
        ace_type type = (u_int32_t) get_jint_field(env, typeObj, "value");
        ace_flag flag = (u_int32_t) get_jint_field(env, flagObj, "value");
        ace_mask mask = (u_int32_t) get_jint_field(env, maskObj, "value");
        unsigned char *who = (unsigned char *) (*env)->GetStringUTFChars(env, whoStrObj, 0);
        struct sftp_ace ace = {
                .type = type,
                .flag = flag,
                .mask = mask,
                .who_ptr = who
        };
        ace_arr_ptr[i] = ace;
        (*env)->DeleteLocalRef(env, aceObj);
        (*env)->DeleteLocalRef(env, typeObj);
        (*env)->DeleteLocalRef(env, flagObj);
        (*env)->DeleteLocalRef(env, maskObj);
        (*env)->DeleteLocalRef(env, whoStrObj);
    }
}

jobjectArray create_obj_arr(JNIEnv *env, ace_cnt cnt, struct sftp_acl *acl_ptr) {
    jclass aceClass = get_global_ref(env, ACE_CLASS_PATH);
    jclass aceTypeClass = get_global_ref(env, ACE_TYPE_CLASS_PATH);
    jclass aceFlagClass = get_global_ref(env, ACE_FLAG_CLASS_PATH);
    jclass aceMaskClass = get_global_ref(env, ACE_MASK_CLASS_PATH);
    jobjectArray aceObjArr = (*env)->NewObjectArray(env, (jsize) cnt, aceClass, NULL);
    for (int i = 0; i < cnt; i++) {
        struct sftp_ace curr = acl_ptr->ace_arr_ptr[i];
        jmethodID aceTypeCtor = get_constructor(env, aceTypeClass, ACE_TYPE_PARAMS);
        jobject aceType = (*env)->NewObject(env, aceTypeClass, aceTypeCtor, (jint) curr.type);
        jmethodID aceFlagCtor = get_constructor(env, aceFlagClass, ACE_FLAG_PARAMS);
        jobject aceFlag = (*env)->NewObject(env, aceFlagClass, aceFlagCtor, (jint) curr.flag);
        jmethodID aceMaskCtor = get_constructor(env, aceMaskClass, ACE_MASK_PARAMS);
        jobject aceMask = (*env)->NewObject(env, aceMaskClass, aceMaskCtor, (jint) curr.mask);
        jmethodID aceCtor = get_constructor(env, aceClass, ACE_PARAMS);
        jstring who = (*env)->NewStringUTF(env, (char *) curr.who_ptr);
        jobject ace = (*env)->NewObject(env, aceClass, aceCtor, aceType, aceFlag, aceMask, who);
        (*env)->SetObjectArrayElement(env, aceObjArr, i, ace);
    }
    (*env)->DeleteGlobalRef(env, aceClass);
    (*env)->DeleteGlobalRef(env, aceTypeClass);
    (*env)->DeleteGlobalRef(env, aceFlagClass);
    (*env)->DeleteGlobalRef(env, aceMaskClass);
    return aceObjArr;
}

jobject new_acl_object(JNIEnv *env, struct sftp_acl *acl_ptr) {
    // Parameters of AccessControlList's constructor
    jclass aclFlagsClass = get_global_ref(env, ACL_FLAGS_CLASS_PATH);
    jclass aclClass = get_global_ref(env, ACL_CLASS_PATH);
    jmethodID aclFlagsCtor = get_constructor(env, aclFlagsClass, ACL_FLAGS_PARAMS);
    jmethodID aclCtor = get_constructor(env, aclClass, ACL_PARAMS);
    jobject aclFlags = (*env)->NewObject(env, aclFlagsClass, aclFlagsCtor, acl_ptr->flag);
    jobjectArray aceObjArr = create_obj_arr(env, acl_ptr->cnt, acl_ptr);
    jobject acl = (*env)->NewObject(env, aclClass, aclCtor, aclFlags, (jint) acl_ptr->cnt, aceObjArr);
    (*env)->DeleteGlobalRef(env, aclFlagsClass);
    (*env)->DeleteGlobalRef(env, aclClass);
    return acl;
}

JNIEXPORT jint JNICALL Java_com_github_webrsync_sftp_fs_AclManager_setSftpAcl
        (JNIEnv *env, jclass class, jstring string, jobject j_acl, jobject j_xattr_flag) {
    int xattrValue = (int) get_jint_field(env, j_xattr_flag, "value");
    jobject aclFlagsObj = get_object_field(env, j_acl, "aclFlags", ACL_FLAGS_FIELD_TYPE);
    jint aclFlags = get_jint_field(env, aclFlagsObj, "value");
    jint aceCnt = get_jint_field(env, j_acl, "aceCount");
    jobjectArray aceObjArr = get_object_field(env, j_acl, "aces", ACE_ARRAY_FIELD_TYPE);
    struct sftp_ace aces[aceCnt];
    iterate_aces(env, aceCnt, aceObjArr, aces);
    struct sftp_acl acl = {
            .flag = aclFlags,
            .cnt = aceCnt,
            .ace_arr_ptr = aces
    };
    const char *path = (*env)->GetStringUTFChars(env, string, 0);
    int res = set_sftp_acl(path, &acl, xattrValue);
    (*env)->ReleaseStringUTFChars(env, string, path);
    return (jint) res;
}

JNIEXPORT jobject JNICALL Java_com_github_webrsync_sftp_fs_AclManager_getSftpAcl
        (JNIEnv *env, jclass class, jstring string) {
    const char *path = (*env)->GetStringUTFChars(env, string, 0);
    size_t acl_size = get_xattr_size(path);
    struct sftp_acl new_acl;
    unsigned char *in = malloc(sizeof(char) * acl_size);
    int res = get_sftp_acl(path, in);
    if (res == -1) //If getting an acl from the specified path fails
        goto free_failed;
    int decode_res = decode_acl(in, &new_acl);
    if (decode_res == -1) //If decoding fails
        goto free_failed;
    jobject aclObj = new_acl_object(env, &new_acl);
    free(in);
    free(new_acl.ace_arr_ptr);
    (*env)->ReleaseStringUTFChars(env, string, path);
    return aclObj;

    free_failed:
    //free(in);
    (*env)->ReleaseStringUTFChars(env, string, path);
    return NULL;
}

JNIEXPORT jboolean JNICALL Java_com_github_webrsync_sftp_fs_AclManager_doesExist
        (JNIEnv *env, jclass class, jstring string) {
    if (string == NULL) {
        char *msg = "The path should not be NULL";
        throw_exception(__func__, __LINE__, __FILE__, msg);
    }
    const char *path = (*env)->GetStringUTFChars(env, string, 0);
    size_t read_bytes = get_xattr_size(path);
    (*env)->ReleaseStringUTFChars(env, string, path);
    if (read_bytes != 0)
        return (jboolean) 1;
    else
        return (jboolean) 0;
}