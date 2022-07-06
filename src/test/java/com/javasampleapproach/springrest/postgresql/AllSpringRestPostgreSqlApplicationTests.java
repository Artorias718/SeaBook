package com.javasampleapproach.springrest.postgresql;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		CustomerRepositoryIntegrationTest.class,
		CustomerControllerIntegrationTest.class,
		SpotRepositoryIntegrationTest.class,
		SpotControllerIntegrationTest.class,
		StabilimentoRepositoryIntegrationTest.class,
		StabilimentoControllerIntegrationTest.class
})
public class AllSpringRestPostgreSqlApplicationTests {

	@Test
	public void contextLoads() {
	}

}
