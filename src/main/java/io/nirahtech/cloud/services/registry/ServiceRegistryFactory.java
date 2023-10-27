package io.nirahtech.cloud.services.registry;

public final class ServiceRegistryFactory {

    private static final ServiceRegistry SERVICE_REGISTRY = new LocalServiceRegistry();
    
    private ServiceRegistryFactory() { }

    public static final ServiceRegistry create() {
        return SERVICE_REGISTRY;
    }
}
