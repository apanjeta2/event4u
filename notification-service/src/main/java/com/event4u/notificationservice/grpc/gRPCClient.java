package com.event4u.notificationservice.grpc;

import com.event4u.notificationservice.ServiceInstanceRestController;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class gRPCClient {
    private static final Logger logger = Logger.getLogger(gRPCClient.class.getName());

    @Autowired
    private ServiceInstanceRestController serviceInstanceRestController;

    private actionGrpc.actionBlockingStub blockingStub;



    public void createLog(String ServiceName, Long userId, Request.ActionType actionType, String resourceName) {
        //String target = "localhost:6565";

        RestTemplate restTemplate = new RestTemplate();
        List<String> listOfUrls = serviceInstanceRestController.serviceInstancesByApplicationName("system-events-service");

        String url = listOfUrls.get(0);
        String fooResourceUrl = url;

        String p = "dns://";
        if (System.getProperty("os.name").toLowerCase().contains("windows")) p += "/";
        if ((url != null) && (url.length() > 0)) {
            url = p  + url.substring(7, url.length() - 4) + "6565";
        }
        ManagedChannel channel = ManagedChannelBuilder.forTarget(url)

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
