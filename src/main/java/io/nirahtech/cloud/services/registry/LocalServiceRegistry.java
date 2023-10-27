package io.nirahtech.cloud.services.registry;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import io.nirahtech.cloud.services.common.Host;
import io.nirahtech.cloud.services.common.Service;
import io.nirahtech.cloud.services.common.ServiceIdentifier;

public class LocalServiceRegistry implements ServiceRegistry {

    private final ServiceIdentifier identifier;
    private final String name;
    private final Host host;
    private final int port;
    private final Map<ServiceIdentifier, Service> services = new HashMap<>();
    private final String protocol;
    private final String namespace;

    public LocalServiceRegistry() {
        this.identifier = new ServiceIdentifier(UUID.randomUUID());
        this.name = this.getClass().getSimpleName();
        InetAddress localHost;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        this.host = new Host(localHost.getHostAddress(), localHost.getHostName());
        this.protocol = "nrtk";
        this.port = 44666;
        this.namespace = null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ServiceIdentifier getIdentifier() {
        return this.identifier;
    }

    @Override
    public Host getHost() {
        return this.host;
    }
    
    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public String getNamespace() {
        return this.namespace;
    }

    @Override
    public Optional<Service> find(ServiceIdentifier identifier) {
        Optional<Service> service = Optional.empty();
        if (Objects.nonNull(identifier)) {
            if (this.services.containsKey(identifier)) {
                service = Optional.ofNullable(this.services.get(identifier));
            }
        }
        return service;

    }

    @Override
    public ServiceIdentifier register(Service service) {
        ServiceIdentifier newIdentifier = null;
        if (Objects.nonNull(service)) {
            if (!this.services.values().contains(service)) {
                if (Objects.nonNull(service.getIdentifier())) {
                    newIdentifier = service.getIdentifier();
                } else {
                    newIdentifier = new ServiceIdentifier(UUID.randomUUID());
                }
                this.services.put(newIdentifier, service);
            } else {
                newIdentifier = this.services.entrySet().stream().filter(entry -> entry.getValue().equals(service)).findFirst().get().getKey();
            }
        }
        return newIdentifier;
    }

    @Override
    public void unregister(ServiceIdentifier serviceIdentifier) {
        if (Objects.nonNull(serviceIdentifier)) {
            if (this.services.containsKey(serviceIdentifier)) {
                this.unregister(this.services.get(serviceIdentifier));
            }
        }
    }

    @Override
    public void unregister(Service service) {
        if (Objects.nonNull(service)) {
            if (this.services.values().contains(service)) {
                ServiceIdentifier identifier = this.services.entrySet().stream().filter(entry -> entry.getValue().equals(service)).findFirst().get().getKey();
                this.services.remove(identifier);
            }
        }
    }
    @Override
    public Collection<Service> list() {
        return Collections.unmodifiableCollection(this.services.values());
    }
    
}
