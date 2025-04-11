//package org.exchage.exchange;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.testcontainers.containers.GenericContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.utility.DockerImageName;
//
//@Testcontainers
//class ContainerTest {
//
//	@Container
//	private GenericContainer container = new GenericContainer<>(DockerImageName.parse("postgres:13.20"));
//
//
//	@BeforeEach
//	void setUp() {
//
//	}
//
//
//	@Test
//	void firstContainerTest() throws InterruptedException {
//
//		Assertions.assertTrue(this.container.isRunning());
//
//	}
//
//
//}
