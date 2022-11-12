

docker run -d --name etcd-server \
   -p 2379:2379 \
   -p 2380:2380 \
   --env ALLOW_NONE_AUTHENTICATION=yes \
   --env ETCD_ADVERTISE_CLIENT_URLS=http://etcd-server:2379 \
   bitnami/etcd:latest