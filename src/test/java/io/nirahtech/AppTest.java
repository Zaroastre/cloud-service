package io.nirahtech;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import io.nirahtech.cloud.services.common.Service;
import io.nirahtech.cloud.services.common.ServiceIdentifier;
import io.nirahtech.cloud.services.registry.ServiceRegistry;
import io.nirahtech.cloud.services.registry.ServiceRegistryFactory;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        // ServiceMesh mesh;
        // ServiceDiscovery discovery;

        final ServiceRegistry registry = ServiceRegistryFactory.create();
        final ServiceIdentifier identifier = registry.register(new AuthService());
        Optional<Service> service = registry.find(identifier);
        assertTrue( service.isPresent() );
        Service auth = service.get();
        System.out.println(
            auth.isServiceAvailable()
        );
        registry.unregister(identifier);
        service = registry.find(identifier);
        assertTrue( service.isEmpty() );
    }
}
