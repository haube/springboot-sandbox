package net.cloudy.sytes.hello_liberty.sftp.server;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("sftp")
@EnableConfigurationProperties(SftpProperties.class)
@Configuration
public class SftpConfiguration {
}