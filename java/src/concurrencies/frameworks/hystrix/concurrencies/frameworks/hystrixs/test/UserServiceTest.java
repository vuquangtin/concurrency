package concurrencies.frameworks.hystrixs.test;

import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import concurrencies.frameworks.hystrixs.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

	@Autowired
	private UserService userService;

	/**
	 * 测试同步
	 */
	@Test
	public void testGetUserId() {
		System.out.println("=================" + userService.getUserId("lisi"));
	}

	/**
	 * 测试异步
	 */
	@Test
	public void testGetUserName() throws ExecutionException,
			InterruptedException {
		System.out.println("================="
				+ userService.getUserName(30L).get());
	}
}