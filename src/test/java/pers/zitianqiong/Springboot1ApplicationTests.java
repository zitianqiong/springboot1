package pers.zitianqiong;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.transaction.annotation.Transactional;
import pers.zitianqiong.domain.Customer;
import pers.zitianqiong.handler.KafkaSendResultHandler;
import pers.zitianqiong.mapper.CustomerMapper;

@Slf4j
@SpringBootTest
class Springboot1ApplicationTests {

    @Autowired
    private CustomerMapper userMapper;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private KafkaSendResultHandler producerListener;

    @Test
    @Transactional
    public void testTransactionalAnnotation() {
        kafkaTemplate.send("topic.quick.tran", "test transactional annotation");
        throw new RuntimeException("fail");
    }


    @Test
    void contextLoads() {
        List<Customer> users = userMapper.selectList(null);
        users.forEach(System.out :: println);
    }

    @Test
    public void testPage() {
        Page<Customer> page = new Page<>(1, 2);
        userMapper.selectPage(page, null);
        page.getRecords().forEach(System.out :: println);
    }

    @Test
    void testleet(){
        System.out.println(Solution.isPalindrome(13231));
    }

    @Test
    public void testDelete() {
        userMapper.deleteById(1);
    }

    @Test
    @Transactional
    public void testDemo() throws InterruptedException, ExecutionException {
        kafkaTemplate.send("topic.quick.demo", "测试事务").get();
//        throw new RuntimeException("fail");
        //休眠5秒，为了使监听器有足够的时间监听到topic的数据
//        Thread.sleep(5000);
    }

    @Test
    public void testTemplateSend() {
        //发送带有时间戳的消息
        kafkaTemplate.send("topic.quick.demo", 0, System.currentTimeMillis(), 0, "send message with timestamp");

        //使用ProducerRecord发送消息
        ProducerRecord record = new ProducerRecord("topic.quick.demo", "use ProducerRecord to send message");
        kafkaTemplate.send(record);

        //使用Message发送消息
        Map map = new HashMap();
        map.put(KafkaHeaders.TOPIC, "topic.quick.demo");
        map.put(KafkaHeaders.PARTITION_ID, 0);
        map.put(KafkaHeaders.MESSAGE_KEY, 0);
        GenericMessage message = new GenericMessage("use Message to send message", new MessageHeaders(map));
        kafkaTemplate.send(message);
    }

    @Test
    @Transactional
    public void testProducerListen() {
        kafkaTemplate.setProducerListener(producerListener);
        try {
            kafkaTemplate.send("topic.quick.demo", "事务7").get(50000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
