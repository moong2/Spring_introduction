package hello.spring_core_principlesbasic.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {

	static class TestConfig {

		@Bean
		public StatefulService statefulService() {
			return new StatefulService();
		}
	}

	@Test
	void statefulServiceSingleton() {
		AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(
			TestConfig.class);

		StatefulService statefulService1 = ac.getBean(StatefulService.class);
		StatefulService statefulService2 = ac.getBean(StatefulService.class);

		//ThreadA : A사용자 10000원 주문
		int userAPrice = statefulService1.order("userA", 10000);
		//ThreadB : B사용자 20000원 주문
		statefulService2.order("userA", 20000);

		// ThreadA: A사용자 주문 금액 조회
		// int price = statefulService1.getPrice();
		// System.out.println("price1 = " + price);
		System.out.println("userAPrice = " + userAPrice);

		Assertions.assertThat(userAPrice).isEqualTo(10000);
	}
}