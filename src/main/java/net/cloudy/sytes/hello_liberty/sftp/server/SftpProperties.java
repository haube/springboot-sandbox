package net.cloudy.sytes.hello_liberty.sftp.server;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

@Value
@ConfigurationProperties(prefix = "sftp")
public class SftpProperties {

    int port;
    String username;
    String password;
    Path root;
    Path hostKey;
    Path authorizedKeys;

}