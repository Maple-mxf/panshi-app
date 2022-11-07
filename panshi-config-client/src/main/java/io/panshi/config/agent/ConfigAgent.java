package io.panshi.config.agent;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableScheduledFuture;
import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import io.panshi.config.srv.CommonResp;
import io.panshi.config.srv.ConfigDto;
import io.panshi.config.srv.ConfigSrvGrpc;
import io.panshi.config.srv.ErrCode;
import io.panshi.config.srv.PullConfigListRequest;
import io.panshi.config.srv.PullConfigListResponse;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class ConfigAgent {

    private final ConfigSrvGrpc.ConfigSrvBlockingStub configSrvBlockingStub ;
    private final ListeningScheduledExecutorService executor = MoreExecutors.listeningDecorator(
            Executors.newScheduledThreadPool(2, new ThreadFactoryBuilder()
                            .setDaemon(true)
                            .setNameFormat("config-agent-%d")
                            .setPriority(1)
                    .build())
    );
    private final static String GROUP = "SERVICE_GROUP";
    private final static String SET = "SERVICE_SET";
//    private final static String

    private final String group;
    private final String set;

    private final ConcurrentHashMap<String, ConfigDto> configMap
            = new ConcurrentHashMap<>();

    private ConfigAgent(){
        group = System.getenv(GROUP);
        set = System.getenv(SET);
        if (group == null || group .isEmpty()){
            throw new IllegalStateException("'SERVICE_GROUP' env var absent ");
        }
        if (set == null || set .isEmpty()){
            throw new IllegalStateException("'SERVICE_SET' env var absent ");
        }
        Channel channel = ManagedChannelBuilder
                .forAddress("", 8090)
                .build();
         configSrvBlockingStub = ConfigSrvGrpc.newBlockingStub(channel);

        ListenableScheduledFuture<?> future = executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                PullConfigListRequest request = PullConfigListRequest
                        .newBuilder()
                        .setGroup(group)
                        .setSet(set)
                        .build();
                PullConfigListResponse response =
                        configSrvBlockingStub.pullConfigList(request);

                CommonResp cRsp = response.getCRsp();
                if (ErrCode.SUCCESS .equals(cRsp.getCode() )){
                    List<ConfigDto> configList = response.getConfigListList();
                    for (ConfigDto configDto : configList) {
                        configMap.put(configDto.getKey(), configDto);
                    }
                }
            }
        }, 0, 5, TimeUnit.SECONDS);

        Futures.addCallback(future, new FutureCallback<Object>() {
            @Override
            public void onSuccess(Object result) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        },executor);


        ListenableScheduledFuture<?> future1 = executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

            }
        }, 1, 8, TimeUnit.SECONDS);

    }


}
