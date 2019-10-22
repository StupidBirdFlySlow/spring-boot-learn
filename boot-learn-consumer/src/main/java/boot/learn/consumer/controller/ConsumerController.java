package boot.learn.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import boot.learn.consumer.service.ComputeClient;

/**
 * Ribbion ConsumerController
 * 
 * @author leim
 *
 */
@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ComputeClient computeClient;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "addMethodFallBack")
    public Integer add() {
        return restTemplate.getForEntity("http://COMPUTE-SERVICE/add?a=10&b=20", Integer.class).getBody();
    }

    public Integer addMethodFallBack() {
        return -1;
    }

    @RequestMapping(value = "/add2", method = RequestMethod.GET)
    public Integer add(Integer a, Integer b) {
        return computeClient.add(a, b);
    }
}
