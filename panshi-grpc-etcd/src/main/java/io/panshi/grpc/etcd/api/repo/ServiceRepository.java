package io.panshi.grpc.etcd.api.repo;

import io.panshi.grpc.etcd.api.exception.PanshiException;
import io.panshi.grpc.etcd.api.model.Service;

import java.util.List;

public interface ServiceRepository extends PersistenceRepository {

    void putService(Service service) throws PanshiException;

    List<Service> getServiceList();

    boolean exist(Service service);
}
