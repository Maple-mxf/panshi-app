package io.panshi.discovery.core.api.repo;

import io.panshi.discovery.core.api.exception.PanshiException;
import io.panshi.discovery.core.api.model.Namespace;

import java.util.List;

public interface NamespaceRepository extends PersistenceRepository {

    List<Namespace> getNamespaceList();

    void putNamespace(Namespace namespace) throws PanshiException;

    boolean exist(String namespace) throws PanshiException ;
}
