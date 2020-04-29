package com.event4u.notificationservice.grpc;

import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class gRPCClient {
    private static final Logger logger = Logger.getLogger(gRPCClient.class.getName());

    private actionGrpc.actionBlockingStub blockingStub;

    public void createLog(String ServiceName, Long userId, Request.ActionType actionType, String resourceName) {
        String target = "localhost:6565";

        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                .usePlaintext()
                .build();

        blockingStub = actionGrpc.newBlockingStub(channel);

        Request req= Request.newBuilder()
                .setActionType(actionType)
                .setServiceName(ServiceName)
                .setUserId(userId)
                .setResourceName(resourceName)
                .build();

        APIResponse response;
        try {
            logger.info(String.valueOf(req.getUserId()));
            logger.info(String.valueOf(req.getServiceName()));
            response = blockingStub.logAction(req);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
        logger.info("Greeting: " + response.getResponseMessage());
    }


}
