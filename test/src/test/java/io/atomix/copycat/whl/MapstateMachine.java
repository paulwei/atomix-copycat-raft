package io.atomix.copycat.whl;

import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.Snapshottable;
import io.atomix.copycat.server.StateMachine;
import io.atomix.copycat.server.storage.snapshot.SnapshotReader;
import io.atomix.copycat.server.storage.snapshot.SnapshotWriter;

import java.util.HashMap;
import java.util.Map;

//自定了MapstateMachine，它继承框架提供的StateMachine类，MapstateMachine主要处理来自客户端的操作，如示例建的这个类，用于处理两个操作,put和get.put用于向map中写入键值，get用于获取值
public class MapstateMachine extends StateMachine implements Snapshottable {
    //此为copycat-server需要维护的一致性数据结构,本例使用的是MAP
    private Map<Object, Object> map = new HashMap<>();

    //定义对map的put操作
    public Object put(Commit<PutCommand> commit) {
        try {
            map.put(commit.operation().key(), commit.operation().value());
        } finally {
            commit.close();
        }
        return null;
    }
    //定义对map的get操作
    public Object get(Commit<GetQuery> commit) {
        try {
            return map.get(commit.operation().key());
        } finally {
            commit.close();
        }
    }

    //以下两个方法来自于实现Snapshottable的接口，实现这个接口是用于copycat-server能够对本地状态日志进行压缩，并形成snapshot(快照),当copycat-server重启后，可以从快照恢复状态，如果有其它的server加入进来，可以将快照复制到其它server上.
    @Override
    public void snapshot(SnapshotWriter writer) {
        writer.writeObject(map);
    }

    @Override
    public void install(SnapshotReader reader) {
        map = reader.readObject();
    }
}

