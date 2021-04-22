package tub.ods.pch.channel.node;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication(scanBasePackageClasses = ChannelNodeApplication.class)
public class ChannelNodeApplication {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(ChannelNodeApplication.class, args);
        System.out.println("Context Server" +context);
        context.start();
        NodeServer nodeServer = context.getBean(NodeServer.class);
        System.out.println("Node Server" +nodeServer);
        nodeServer.awaitTermination();
    }
}
