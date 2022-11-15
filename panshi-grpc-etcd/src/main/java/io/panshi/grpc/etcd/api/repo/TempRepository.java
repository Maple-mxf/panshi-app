package io.panshi.grpc.etcd.api.repo;

public interface TempRepository extends Repository {

    /**
     * 临时存储都需要一个租约ID
     * @return lease id
     */
    long getLeaseId();

    /**
     * 无限续约
     */
    void keepAlive();

    /**
     * @return time to alive 每次续约的间隔时间
     */
    long getTTL();
}
