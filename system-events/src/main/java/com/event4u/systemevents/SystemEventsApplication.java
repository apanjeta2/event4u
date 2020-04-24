package com.event4u.systemevents;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class SystemEventsApplication {

	public static void main(String[] args) throws IOException, InterruptedException {

		System.out.println("Starting GRPC Server");
		Server server = ServerBuilder.forPort(9090).addService(

				new SystemEventsService()).build();

		server.start();
		System.out.println("server started at "+ server.getPort());
		server.awaitTermination();
	}

}
