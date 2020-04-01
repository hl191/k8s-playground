package at.home;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import at.home.api.GoodListApi;
import at.home.api.model.Good;

@RestController
public class GoodsRestController implements GoodListApi {

    @Override
    public ResponseEntity<List<Good>> listGoods() {
        return ResponseEntity.ok(Arrays.asList(new Good().id(1L).name("Wasser"), new Good().id(2L).name("Brot")));
    }
}
