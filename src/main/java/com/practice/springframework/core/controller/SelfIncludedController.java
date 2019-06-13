package com.practice.springframework.core.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * inject self
 *
 * @author Luo Bao Ding
 * @since 2018/11/21
 */
@RestController
@EnableCaching
public class SelfIncludedController {
    private final SelfIncludedController controller;


    public SelfIncludedController(@Lazy SelfIncludedController controller) {
        this.controller = controller;
    }


    @RequestMapping("/hello")
    public String hello() {
        return controller.greet();
    }

    @Cacheable("/greet")
    public String greet() {
        return "hello";
    }


}
