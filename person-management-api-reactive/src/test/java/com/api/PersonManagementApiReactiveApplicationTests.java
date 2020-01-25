package com.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@Slf4j
@ContextConfiguration(initializers = PersonManagementApiReactiveApplicationTests.MongoDbInitializer.class)
@ActiveProfiles(value = "integration")
class PersonManagementApiReactiveApplicationTests {

	@Test
	void contextLoads() {
	}

	public static class MongoDbInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@Override
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			log.info("Overriding Spring Properties for mongodb !!!!!!!!!");

			TestPropertyValues values = TestPropertyValues.of(
					"spring.data.mongodb.host=" + "localhost"

			);
			values.applyTo(configurableApplicationContext);
		}
	}
}
