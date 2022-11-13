package io.panshi.grpc.etcd.imp.repo;

import io.panshi.grpc.etcd.api.config.Config;
import io.panshi.grpc.etcd.api.exception.PanshiException;
import io.panshi.grpc.etcd.api.model.Namespace;
import io.panshi.grpc.etcd.api.repo.NamespaceRepository;

import java.util.ArrayList;
import java.util.List;

public class NamespaceRepositoryImp extends AbstractRepository implements NamespaceRepository {

    protected NamespaceRepositoryImp(Config config) {
        super(config);
    }

    @Override
    public List<Namespace> getNamespaceList() {
        return new ArrayList<>();
    }

    @Override
    public void putNamespace(Namespace namespace) throws PanshiException {

    }

    @Override
    public void deleteNamespace(Namespace namespace) throws PanshiException {

    }
}
