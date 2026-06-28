package net.cloudy.sytes.hello_liberty.sftp.server;

import org.apache.sshd.common.file.virtualfs.VirtualFileSystemFactory;
import org.apache.sshd.common.keyprovider.FileKeyPairProvider;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.config.keys.AuthorizedKeysAuthenticator;
import org.apache.sshd.sftp.server.SftpSubsystemFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("sftp")
@Configuration
public class SftpServerConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public SshServer sshServer(SftpProperties props) {

        SshServer server = SshServer.setUpDefaultServer();
        server.setPort(props.getPort());
        AuthorizedKeysAuthenticator authenticator =
                new AuthorizedKeysAuthenticator(props.getAuthorizedKeys());
        server.setPublickeyAuthenticator(authenticator);
        FileKeyPairProvider provider =
                new FileKeyPairProvider(props.getHostKey());
        server.setKeyPairProvider(provider);
        server.setSubsystemFactories(List.of(
                new SftpSubsystemFactory.Builder().build()));
        server.setFileSystemFactory(
                new VirtualFileSystemFactory(props.getRoot()));

        return server;
    }
}