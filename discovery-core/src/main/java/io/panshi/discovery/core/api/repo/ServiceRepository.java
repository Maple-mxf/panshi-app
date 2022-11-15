package io.panshi.discovery.core.api.repo;

import io.panshi.discovery.core.api.exception.PanshiException;
import io.panshi.discovery.core.api.model.Service;

import java.util.List;

public interface ServiceRepository extends PersistenceRepository {

    void putService(Service service) throws PanshiException;

    List<Service> getServiceList();

    boolean exist(Service service);
}
