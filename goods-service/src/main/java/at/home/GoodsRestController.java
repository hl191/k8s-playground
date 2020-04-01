package at.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.collection.IList;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.flakeidgen.FlakeIdGenerator;

import at.home.api.GoodListApi;
import at.home.api.model.Good;

@RestController
public class GoodsRestController implements GoodListApi {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Override
    public ResponseEntity<List<Good>> listGoods() {
        FlakeIdGenerator listIdGenerator = hazelcastInstance.getFlakeIdGenerator("goodIdGenerator");
        IList<Good> list = hazelcastInstance.getList("dataList");

        list.add(new Good().id(listIdGenerator.newId()).name("listGood"));

        return ResponseEntity.ok(list);
    }
}
