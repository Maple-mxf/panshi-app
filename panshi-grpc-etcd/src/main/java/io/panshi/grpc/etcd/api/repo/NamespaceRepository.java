package io.panshi.grpc.etcd.api.repo;

import io.panshi.grpc.etcd.api.exception.PanshiException;
import io.panshi.grpc.etcd.api.model.Namespace;

import java.util.List;

public interface NamespaceRepository extends PersistenceRepository {

    List<Namespace> getNamespaceList();

    void putNamespace(Namespace namespace) throws PanshiException;

    boolean exist(String namespace) throws PanshiException ;
}
