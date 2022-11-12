package io.panshi.grpc.etcd.api.repo;

import io.panshi.grpc.etcd.api.model.Namespace;

import java.util.List;

public interface NamespaceRepository {

    List<Namespace> getNamespaceList();
}
