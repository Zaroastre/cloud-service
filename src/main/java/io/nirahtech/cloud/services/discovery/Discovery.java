package io.nirahtech.cloud.services.discovery;

import java.util.Collection;

import io.nirahtech.cloud.services.common.Service;

interface Discovery<T extends Service> {
    Collection<T> discover();
    
}
