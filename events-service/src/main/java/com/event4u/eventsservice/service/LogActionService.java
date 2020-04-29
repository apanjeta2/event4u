package com.event4u.eventsservice.service;

import com.event4u.eventsservice.grpc.Event4U;
import com.event4u.eventsservice.grpc.actionGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogActionService {
    @Autowired
    DiscoveryService discoveryService;

    public void logAction(Long userId, Event4U.Request.ActionType actionType, String resName) {
        String url = discoveryService.getSystemEventsInstance();
        String p = "dns://";
        if (System.getProperty("os.name").toLowerCase().contains("windows")) p += "/";
        if ((url != null) && (url.length() > 0)) {
            url = p  + url.substring(7, url.length() - 5) + "6565";
        }

        ManagedChannel channel = ManagedChannelBuilder.forTarget(url)
                .usePlaintext()
                .build();

        actionGrpc.actionBlockingStub stub
                = actionGrpc.newBlockingStub(channel);

        Event4U.APIResponse response = stub.logAction(Event4U.Request.newBuilder()
                .setServiceName("Events-service")
                .setUserId(userId)
                .setActionType(actionType)
                .setResourceName(resName)
                .build());
        //System.out.println(response.getResponseMessage());
        channel.shutdown();
    }
}
