package io.atomix.copycat.whl;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.examples.ValueStateMachine;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class AtomixThree {

    public static void main(String[] args){

        //设置server_1的地址和端口
        Address address = new Address("127.0.0.1", 8003);
        CopycatServer server = CopycatServer.builder(address)
                .withStateMachine(ValueStateMachine::new)
                .withTransport(NettyTransport.builder()
                        .withThreads(4)
                        .build())
                .withStorage(Storage.builder()
                        .withDirectory(new File("I:\\elecate_leader\\logs"))
                        .withStorageLevel(StorageLevel.DISK)
                        .build())
                .build();

        server.serializer().register(PutCommand.class);
        server.serializer().register(GetQuery.class);

        //启动服务器
        CompletableFuture<CopycatServer> future = server.bootstrap();
        future.join();

    }
}
