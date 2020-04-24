package com.event4u.systemevents;

import com.event4u.systemevents.grpc.Event4U.APIResponse;
import com.event4u.systemevents.grpc.Event4U.Request;
import com.event4u.systemevents.grpc.actionGrpc.actionImplBase;
import io.grpc.stub.StreamObserver;

public class SystemEventsService extends actionImplBase{

    @Override
    public void logAction(Request request, StreamObserver<APIResponse> responseObserver) {

        String serviceName = request.getServiceName();
        String resourceName = request.getResourceName();

        System.out.println("Service name: " + serviceName + ", resource name: " + resourceName);

        APIResponse.Builder response = APIResponse.newBuilder();
        response.setResponseMessage("Action saved");
        response.setResponseType(APIResponse.ResponseType.SUCCESS);

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }

}

