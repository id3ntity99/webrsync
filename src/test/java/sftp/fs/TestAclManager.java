package sftp.fs;

import com.github.webrsync.sftp.fs.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestAclManager {
    private static String path;
    private static final String WHO = "hello-from-java";

    @BeforeAll
    public static void setup() {
        String propsPath = System.getProperty("user.dir") + "/src/main/resources/app.properties";
        try (InputStream in = new FileInputStream(propsPath)) {
            Properties props = new Properties();
            props.load(in);
            path = props.getProperty("dummy.path");
            System.out.println(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Order(1)
    @Test
    void testSetSftpAcl() {
        AccessControlEntry ace = new AccessControlEntry(
                AceType.ACE4_ACCESS_ALLOWED_ACE_TYPE,
                AceFlag.ACE4_DIRECTORY_INHERIT_ACE,
                AceMask.ACE4_READ_DATA,
                WHO);
        int aceCount = 1;
        AccessControlEntry[] aceArr = {ace};
        AccessControlList acl = new AccessControlList(AclFlags.SFX_ACL_CONTROL_INCLUDED, aceCount, aceArr);
        int res;
        if (AclManager.doesExist(path))
            res = AclManager.setSftpAcl(path, acl, SetXattrFlag.XATTR_REPLACE);
        else
            res = AclManager.setSftpAcl(path, acl, SetXattrFlag.XATTR_CREATE);
        assertEquals(0, res);
    }

    @Order(2)
    @Test
    void testGetSftpAcl() {
        AccessControlList acl = AclManager.getSftpAcl(path);
        assertEquals(AclFlags.SFX_ACL_CONTROL_INCLUDED.value(), acl.aclFlags().value());
        assertEquals(1, acl.aceCount());
        AccessControlEntry ace = acl.aces()[0];
        assertEquals(AceType.ACE4_ACCESS_ALLOWED_ACE_TYPE.value(), ace.type().value());
        assertEquals(AceFlag.ACE4_DIRECTORY_INHERIT_ACE.value(), ace.flag().value());
        assertEquals(AceMask.ACE4_READ_DATA.value(), ace.mask().value());
        assertEquals(WHO, ace.who());
    }

    @Order(3)
    @Test
    void testSettingAclMultipleAces() {
        AccessControlEntry ace1 = new AccessControlEntry(
                AceType.ACE4_ACCESS_ALLOWED_ACE_TYPE,
                AceFlag.ACE4_DIRECTORY_INHERIT_ACE,
                AceMask.ACE4_READ_DATA,
                WHO
        );
        AccessControlEntry ace2 = new AccessControlEntry(
                AceType.ACE4_ACCESS_ALLOWED_ACE_TYPE,
                AceFlag.ACE4_DIRECTORY_INHERIT_ACE,
                AceMask.ACE4_WRITE_DATA,
                WHO
        );
        int cnt = 2;
        AccessControlEntry[] aces = {ace1, ace2};
        AccessControlList acl = new AccessControlList(AclFlags.SFX_ACL_CONTROL_INCLUDED, cnt, aces);
        int res = -1;
        if (AclManager.doesExist(path)) {
            res = AclManager.setSftpAcl(path, acl, SetXattrFlag.XATTR_REPLACE);
        } else {
            res = AclManager.setSftpAcl(path, acl, SetXattrFlag.XATTR_CREATE);
        }
        assertEquals(0, res);
    }

    @Order(4)
    @Test
    void testGettingAclWithMultipleAces() {
        AccessControlList acl = AclManager.getSftpAcl(path);
        assertEquals(2, acl.aceCount());
        AccessControlEntry ace1 = acl.aces()[0];
        AceType type1 = ace1.type();
        assertEquals(AceType.ACE4_ACCESS_ALLOWED_ACE_TYPE.value(), type1.value());
        AceFlag flag1 = ace1.flag();
        assertEquals(AceFlag.ACE4_DIRECTORY_INHERIT_ACE.value(), flag1.value());
        AceMask mask1 = ace1.mask();
        assertEquals(AceMask.ACE4_READ_DATA.value(), mask1.value());
        String who1 = ace1.who();
        assertEquals(WHO, who1);

        AccessControlEntry ace2 = acl.aces()[1];
        AceType type2 = ace2.type();
        assertEquals(AceType.ACE4_ACCESS_ALLOWED_ACE_TYPE.value(), type2.value());
        AceFlag flag2 = ace2.flag();
        assertEquals(AceFlag.ACE4_DIRECTORY_INHERIT_ACE.value(), flag2.value());
        AceMask mask2 = ace2.mask();
        assertEquals(AceMask.ACE4_WRITE_DATA.value(), mask2.value());
        String who2 = ace2.who();
        assertEquals(WHO, who2);
    }
}
