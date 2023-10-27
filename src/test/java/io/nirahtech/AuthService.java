package io.nirahtech;

import java.util.UUID;

import io.nirahtech.cloud.services.common.Host;
import io.nirahtech.cloud.services.common.Service;
import io.nirahtech.cloud.services.common.ServiceIdentifier;

public class AuthService implements Service {

    @Override
    public ServiceIdentifier getIdentifier() {
        return new ServiceIdentifier(UUID.randomUUID());
    }

    @Override
    public String getName() {
        return "auth";
    }

    @Override
    public String getProtocol() {
        return "sso";
    }

    @Override
    public Host getHost() {
        return new Host("192.168.1.1", "LOCALDOMAIN.LOCALHOST");
    }

    @Override
    public int getPort() {
        return 443;
    }

    @Override
    public String getNamespace() {
        return "auth";
    }
    
}
