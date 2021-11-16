package pers.zitianqiong;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pers.zitianqiong.controller.HelloController;
import pers.zitianqiong.pojo.User;
import pers.zitianqiong.mapper.UserMapper;

import java.util.List;

@SpringBootTest
class Springboot1ApplicationTests {


	@Autowired
	private UserMapper userMapper;

	@Autowired
	private HelloController helloController;

	@Test
	void contextLoads() {
		List<User> users = userMapper.selectList(null);
		users.forEach(System.out :: println);
	}

	@Test
	public void helloControllerTest(){

		String hello=helloController.hello();
		System.out.println(hello);
	}

	@Test
	public void insertTest() {
		User user = new User();
		user.setName("关欣宇");
		user.setAge(22);
		int result = userMapper.insert(user);
		System.out.println(result);
	}

	@Test
	public void updateTest() {
		User user = new User();
//		user.setName("丛吉钰");
//		user.setAge(22);
//		int result = userMapper.insert(user);
//		System.out.println("result:"+result);

		user.setId(2);
		user.setName("关欣宇");
		user.setAge(21);
		int result = userMapper.updateById(user);
		System.out.println("result:"+result);
	}

	@Test
	public void testOptimisticLocker() {
		User user = userMapper.selectById(3);
		user.setName("丛吉钰");
		user.setAge(22);
		userMapper.updateById(user);
	}

	@Test
	public void  testPage(){
		Page<User> page = new Page<>(1,2);
		userMapper.selectPage(page,null);
		page.getRecords().forEach(System.out :: println);
	}

	@Test
	public void testDelete(){
		userMapper.deleteById(1);
	}
}
