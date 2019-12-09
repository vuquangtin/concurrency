package basic.app1;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panghu
 * @Title: TestController
 * @ProjectName cuncurrency_demo
 * @Description: TODO
 * @date 19-2-15 下午4:00
 */
@RestController
@Slf4j
public class TestController {

    @RequestMapping("/test")
    public String test(){
        return "test success";
    }

}
