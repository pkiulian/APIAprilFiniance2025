package org.exchage.exchange.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@SpringBootTest
@Testcontainers
public class AccountServiceStressTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("exchange_db")
            .withUsername("test")
            .withPassword("test")
            .waitingFor(Wait.forListeningPort())  // Ensure PostgreSQL is ready
            .waitingFor(Wait.forLogMessage(".*database system is ready to accept connections.*\\n", 1));  // Log message for readiness

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        // This will inject the dynamically assigned JDBC URL from the container
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", () -> postgres.getUsername());
        registry.add("spring.datasource.password", () -> postgres.getPassword());
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }

    @Test
    public void testConnection() {
        // Add a simple assertion to verify that Spring Boot is connecting to the database
        Assertions.assertNotNull(postgres.getJdbcUrl(), "PostgreSQL URL is not set correctly");
    }



//	@DynamicPropertySource
//	static void configureProperties(DynamicPropertyRegistry registry) {
//
//		System.out.println(">>>>>>>>>>>>>> PostgreSQL URL: " + postgres.getJdbcUrl());
//
//		registry.add("spring.datasource.url", postgres::getJdbcUrl);
//		registry.add("spring.datasource.username", postgres::getUsername);
//		registry.add("spring.datasource.password", postgres::getPassword);
//		registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
//		registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
//	}

	// @Autowired
	// private AccountService accountService;
	// @Autowired
	// private AccountRepository accountRepository;

	// private Long debitAccountId;
	// private Long creditAccountId;

	// @BeforeEach
	// public void setUp() {
	// 	// Initialize two test accounts
	// 	Account debitAccount = new Account();
	// 	debitAccount.setOwnerId(1L);
	// 	debitAccount.setCurrency("EUR");
	// 	debitAccount.setBalance(1000.0);
	// 	debitAccount = this.accountRepository.save(debitAccount);

	// 	Account creditAccount = new Account();
	// 	creditAccount.setOwnerId(2L);
	// 	creditAccount.setCurrency("EUR");
	// 	creditAccount.setBalance(0.0);
	// 	creditAccount = this.accountRepository.save(creditAccount);

	// 	this.debitAccountId = debitAccount.getAccountId();
	// 	this.creditAccountId = creditAccount.getAccountId();
	// }

	// @Test
	// @Disabled
	// public void testOptimisticLockingConcurrentTransfers() throws InterruptedException, ExecutionException {

	// 	int successCount = 0;
	// 	int failureCount = 0;
	// 	int numberOfThreads = 10;
	// 	ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

	// 	List<Callable<Boolean>> tasks = new ArrayList<>();
	// 	for (int i = 0; i < numberOfThreads; i++) {
	// 		tasks.add(() -> {
	// 			try {
	// 				return this.accountService.transferFunds(this.debitAccountId, this.creditAccountId, 10.0, "EUR",
	// 						"EUR");
	// 			} catch (Exception e) {
	// 				return false;
	// 			}
	// 		});
	// 	}

	// 	// Execute all tasks concurrently
	// 	List<Future<Boolean>> results = executorService.invokeAll(tasks);

	// 	for (Future<Boolean> result : results) {
	// 		if (result.get()) {
	// 			successCount++;
	// 		} else {
	// 			failureCount++;
	// 		}
	// 	}

	// 	Assertions.assertEquals(1, successCount);
	// 	Assertions.assertEquals(numberOfThreads - 1, failureCount);

	// 	executorService.shutdown();
	// }

	// @Test
	// public void testRetryLogicConcurrentTransfers() throws InterruptedException, ExecutionException {

	// 	int numberOfThreads = 10;
	// 	ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

	// 	List<Callable<Boolean>> tasks = new ArrayList<>();
	// 	for (int i = 0; i < numberOfThreads; i++) {
	// 		tasks.add(() -> {
	// 			try {
	// 				return this.accountService.safeTransfer(this.debitAccountId, this.creditAccountId, 10.0, "EUR",
	// 						"EUR");
	// 			} catch (Exception e) {
	// 				e.printStackTrace();
	// 				return false;
	// 			}
	// 		});
	// 	}

	// 	// Execute all tasks concurrently
	// 	List<Future<Boolean>> results = executorService.invokeAll(tasks);

	// 	boolean allPass = true;
	// 	for (Future<Boolean> result : results) {
	// 		if (!result.get()) {
	// 			allPass = false;
	// 			break;
	// 		}
	// 	}

	// 	Assertions.assertTrue(allPass);

	// 	Account creditAccount = this.accountRepository.findById(this.creditAccountId).orElseThrow();
	// 	Account debitAccount = this.accountRepository.findById(this.debitAccountId).orElseThrow();

	// 	// The debit account balance should be reduced by 10 * numberOfThreads
	// 	double expectedDebitBalance = 1000.0 - (10.0 * numberOfThreads);
	// 	double expectedCreditBalance = 10.0 * numberOfThreads;

	// 	Assertions.assertEquals(expectedDebitBalance, debitAccount.getBalance());
	// 	Assertions.assertEquals(expectedCreditBalance, creditAccount.getBalance());

	// 	executorService.shutdown();
	// }

}
