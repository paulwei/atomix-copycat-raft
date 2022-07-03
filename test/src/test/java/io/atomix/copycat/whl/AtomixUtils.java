package io.atomix.copycat.whl;



public class AtomixUtils {


//    private void atomix(){
//        //引导集群
//        Atomix atomix = Atomix.builder()
//                .withMemberId("member1")
//                .withAddress("10.192.19.181:5679")
//                .withMulticastEnabled()
//                .build();
//        atomix.start().join();
//
//        //订阅消息
//        atomix.getCommunicationService().subscribe("test", message -> {
//            return CompletableFuture.completedFuture(message);
//        });
//
//        //发送消息
//        atomix.getCommunicationService().send("test", "Hello world!", MemberId.from("foo")).thenAccept(response -> {
//            System.out.println("Received " + response);
//        });

//    }
}
