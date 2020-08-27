package kr.hs.dsm_scarfs.shank.security;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class SocketConfig {

    private SocketIOServer server;

    @Bean
    public SocketIOServer webSocketServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setPort(8001);

        SocketIOServer server = new SocketIOServer(config);
        server.start();
        this.server = server;

        return server;
    }

    @PreDestroy
    public void socketStop() {
        server.stop();
    }

}
