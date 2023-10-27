package io.nirahtech.cloud.services.discovery;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import io.nirahtech.cloud.services.common.Host;
import io.nirahtech.cloud.services.common.Service;
import io.nirahtech.cloud.services.common.ServiceIdentifier;
import io.nirahtech.cloud.services.registry.ServiceRegistry;

public final class LocalServiceDiscovery implements ServiceDiscovery {

    private final Set<ServiceRegistry> serviceRegistries = new HashSet<>();
    
    private final ServiceIdentifier identifier;
    private final String name;
    private final Host host;
    private final int port;
    private final Map<ServiceIdentifier, Service> services = new HashMap<>();
    private final String protocol;
    private final String namespace;

    public LocalServiceDiscovery(final ServiceRegistry serviceRegistry) {
        this.serviceRegistries.add(serviceRegistry);
        
        this.identifier = new ServiceIdentifier(UUID.randomUUID());
        this.name = this.getClass().getSimpleName();
        InetAddress localHost;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        this.host = new Host(localHost.getHostAddress(), localHost.getHostName());
        this.protocol = null;
        this.port = 0;
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
    public Collection<Service> discover() {
        return this.serviceRegistries.stream().flatMap(registry -> registry.list().stream()).toList();
    }
    
}
