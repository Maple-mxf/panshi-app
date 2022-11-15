package io.panshi.discovery.core.api.repo;

import io.panshi.discovery.core.api.event.WatchServiceListener;
import io.panshi.discovery.core.api.exception.PanshiException;
import io.panshi.discovery.core.api.model.Instance;

import java.util.List;


public interface InstanceRepository extends TempRepository {

    void watchInstanceChangeEvent(WatchServiceListener listener  );

    /**
     * 新增服务实例
     * @param instance 服务实例信息
     * @return success
     */
    void putInstanceInfo(Instance instance) throws PanshiException;

    List<Instance> getInstanceList(String namespace,String set, String service);
    List<Instance> getInstanceList(  );

    void deleteInstance(Instance instance) throws PanshiException;
}
