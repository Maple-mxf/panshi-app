package io.panshi.discovery.core.api.router;

import io.panshi.discovery.core.api.model.Instance;

import java.util.List;

public interface Router {

    /**
     * 选取符合条件的实例列表
     * @param instanceList
     * @return
     */
    List<Instance> selectInstanceList(List<Instance> instanceList);
}
