package com.event4u.eventsservice.service;

/*import com.event4u.eventsservice.grpc.Event4U;
import com.event4u.eventsservice.grpc.actionGrpc;
import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class LogActionService {
    public static void logAction(String resName) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        actionGrpc.actionBlockingStub stub
                = actionGrpc.newBlockingStub(channel);

        Event4U.APIResponse response = stub.logAction(Event4U.Request.newBuilder()
                .setServiceName("Events-service")
                .setUserId(20)
                .setActionType(Event4U.Request.ActionType.GET)
                .setTimestamp(Timestamp.newBuilder().setNanos(100).setSeconds(20).build())
                .setResourceName(resName)
                .build());

        System.out.println(response.getResponseMessage());
        channel.shutdown();
    }
}*/
