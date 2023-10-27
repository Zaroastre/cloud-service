package io.nirahtech.cloud.services.registry;

import java.util.Collection;
import java.util.Optional;

import io.nirahtech.cloud.services.common.Service;
import io.nirahtech.cloud.services.common.ServiceIdentifier;

interface Registry<T extends Service> {
    ServiceIdentifier register(T service);
    void unregister(T service);
    void unregister(ServiceIdentifier serviceIdentifier);
    Optional<T> find(ServiceIdentifier identifier);
    Collection<T> list();
}
