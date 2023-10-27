package io.nirahtech.cloud.services.common;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.util.Objects;

public interface Service {
    ServiceIdentifier getIdentifier();
    String getName();
    String getProtocol();
    Host getHost();
    int getPort();
    String getNamespace();

    default URI getURI() {
        StringBuilder uriBuilder = new StringBuilder();
        if (Objects.nonNull(this.getProtocol())) {
            uriBuilder.append(String.format("%s://", this.getProtocol()));
        }
        if (Objects.nonNull(this.getHost())) {
            uriBuilder.append(String.format("%s", this.getHost().ip()));
        }
        if (this.getPort() > 0) {
            uriBuilder.append(String.format(":%s", this.getPort()));
        }
        if (Objects.nonNull(this.getNamespace())) {
            uriBuilder.append(String.format("/%s", this.getNamespace()));
        }
        return URI.create(uriBuilder.toString());
    }

    default boolean isHostAvailable() {
        boolean isAvailable = false;
        try {
            isAvailable = InetAddress.getByName(this.getHost().ip()).isReachable(500);
        } catch (IOException e) {
            isAvailable = false;
        }
        return isAvailable;
    }

    default boolean isServiceAvailable() {
        boolean isAvailable = false;
        try (Socket socket = new Socket()) {
            socket.connect(InetSocketAddress.createUnresolved(this.getHost().ip(), this.getPort()), 500);
            isAvailable = true;
        } catch (IOException e) {
            isAvailable = false;
        }
        return isAvailable;
    }
}
