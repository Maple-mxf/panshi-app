package io.panshi.grpc.etcd.api.repo;

import io.panshi.grpc.etcd.api.model.Service;

import java.util.List;

public interface ServiceRepository {

    List<Service> getServiceList();
}
