package com.github.webrsync.sftp;

import com.github.webrsync.sftp.attrib.SftpFileAttributes;

public interface AttributivePacket extends RequestPacket {
    SftpFileAttributes attributes();
    ExtensionTable extensions();
}
