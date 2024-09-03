package pers.zitianqiong.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppListener implements CommandLineRunner, DisposableBean {

    @Override
    public void run(String... args) throws Exception {
        log.info("应用启动成功，预加载相关数据");
    }

    @Override
    public void destroy() throws Exception {
        log.info("应用正在关闭，清理相关数据");
    }

}