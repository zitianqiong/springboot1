package pers.zitianqiong.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

/**
 * @author zitianqiong
 */

@Controller
@Slf4j
public class HelloController {

    /**
     * @return String
     **/
    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        return "hello spring boot";
    }

    /**
     * @param model .
     * @return String
     **/
    @GetMapping("/toLoginPage")
    public String toLoginPage(Model model) {
        model.addAttribute("currentYear", Calendar.getInstance().get(Calendar.YEAR));
        return "login";
    }
    
    @GetMapping("future")
    public void callable() throws ExecutionException, InterruptedException {
        ArrayList<Callable<Integer>> tasks = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            tasks.add(() -> {
                System.out.println("========================"+ finalI+"=========");
                Thread.sleep(1000);
                return 1;
            });
        }
        long start = System.currentTimeMillis();
        
        List<Future<Integer>> futures = ForkJoinPool.commonPool().invokeAll(tasks);
        long end = System.currentTimeMillis();
        log.info("耗时{}",end - start);
        int sum = 0;
        for (Future<Integer> future : futures) {
            sum += future.get();
        }
        System.out.println(sum);
    }

}
