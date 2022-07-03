package io.atomix.copycat.whl;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class AtomixClient {
    public static void main(String[] args){

        CopycatClient.Builder builder = CopycatClient.builder();

        builder.withTransport(NettyTransport.builder()
                .withThreads(2)
                .build());

        CopycatClient client = builder.build();

        //客户端注册命令
        client.serializer().register(PutCommand.class);
        client.serializer().register(GetQuery.class);


        //集群的ip以及端口
        Collection<Address> cluster = Arrays.asList(
                new Address("127.0.0.1", 8001),
                new Address("127.0.0.1", 8002),
                new Address("127.0.0.1", 8003)
        );

        CompletableFuture<CopycatClient> future = client.connect(cluster);
        future.join();

        //使用PutCommand提交三个键值对
        CompletableFuture[] futures = new CompletableFuture[3];
        futures[0] = client.submit(new PutCommand("one", "Hello world!"));
        futures[1] = client.submit(new PutCommand("two", "Hello world!"));
        futures[2] = client.submit(new PutCommand("three", "Hello world!"));

        //等待集群完成一致性的复制后，打印完成的结果
        CompletableFuture.allOf(futures).thenRun(() -> System.out.println("Commands completed!"));

        //客户端提交查询
        client.submit(new GetQuery("one")).thenAccept(result -> {
            System.out.println("one is: " + result);
        });
    }
}