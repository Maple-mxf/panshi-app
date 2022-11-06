
protoc --proto_path=./ --java_out=../java ConfigSrv.proto

protoc --plugin=protoc-gen-grpc-java --grpc-java_out=../java --java_out=../java   ConfigSrv.proto


/Users/maxuefeng/.gradle/caches/modules-2/files-2.1/com.google.protobuf/protoc/3.6.1/3dbdffcfd05e90a3daec982301c0ac73f026deb2/protoc-3.6.1-osx-x86_64.exe, -I/Users/maxuefeng/panshi-app/panshi-proto/src/main/proto, -I/Users/maxuefeng/panshi-app/panshi-proto/src/main/proto/configsrv, -I/Users/maxuefeng/panshi-app/panshi-proto/build/extracted-protos/main, -I/Users/maxuefeng/panshi-app/panshi-proto/build/extracted-include-protos/main, --java_out=/Users/maxuefeng/panshi-app/panshi-proto/src/main/java, --plugin=protoc-gen-grpc=/Users/maxuefeng/.gradle/caches/modules-2/files-2.1/io.grpc/protoc-gen-grpc-java/1.20.0/73e37e9c44d6b8ac020c2d3f4c5bb73f97ce2882/protoc-gen-grpc-java-1.20.0-osx-x86_64.exe, --grpc_out=/Users/maxuefeng/panshi-app/panshi-proto/src/main/grpc, /Users/maxuefeng/panshi-app/panshi-proto/src/main/proto/configsrv/ConfigSrv.proto, /Users/maxuefeng/panshi-app/panshi-proto/src/main/proto/configsrv/Req.proto, /Users/maxuefeng/panshi-app/panshi-proto/src/main/proto/configsrv/Resp.proto

/Users/maxuefeng/.gradle/caches/modules-2/files-2.1/io.grpc/protoc-gen-grpc-java/1.20.0/73e37e9c44d6b8ac020c2d3f4c5bb73f97ce2882/protoc-gen-grpc-java-1.20.0-osx-x86_64.exe

docker run -d --name etcd-server \
    -p 2379:2379 \
    -p 2380:2380 \
    --env ALLOW_NONE_AUTHENTICATION=yes \
    --env ETCD_ADVERTISE_CLIENT_URLS=http://etcd-server:2379 \
    bitnami/etcd:latest
