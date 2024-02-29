# a) Identify a couple of examples that use AssertJ expressive methods chaining.

Examples:

```
    assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());
```

```
    assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");
```

# b) Identify an example in which you mock the behavior of the repository (and avoid involving a database).

Setup Mock repo

```
    @Mock( lenient = true)
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp() {

        //these expectations provide an alternative to the use of the repository
        Employee john = new Employee("john", "john@deti.com");
        john.setId(111L);

        Employee bob = new Employee("bob", "bob@deti.com");
        Employee alex = new Employee("alex", "alex@deti.com");

        List<Employee> allEmployees = Arrays.asList(john, bob, alex);

        Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
        Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
        Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
        Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
        Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
        Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());
    }
```

Example of test using mock repo:

```
    @Test
     void whenSearchValidName_thenEmployeeShouldBeFound() {
        String name = "alex";
        Employee found = employeeService.getEmployeeByName(name);

        assertThat(found.getName()).isEqualTo(name);
    }
```

# c) What is the difference between standard @Mock and @MockBean?

@Mock is for simple unit tests and @MockBean is for tests that require Spring context (like integration tests).

# d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?

The file is a configuration file specifically used for integrations tests in a Spring boot application. It defines properties and configurations that are specific to the testing environment. It will only be used when Integration tests are ran.

# e) The sample project demonstrates three test strategies to assess an API (C, D and E) developed with SpringBoot. Which are the main/key differences?

C: This is a unit test that uses @WebMvcTest to load only the web layer. It mocks the EmployeeService using @MockBean, so no actual database interaction occurs. This test checks if the EmployeeRestController behaves as expected when it calls methods from EmployeeService.

D: This is an integration test that uses @SpringBootTest to load the full application context. It uses @AutoConfigureMockMvc to inject a MockMvc instance for sending HTTP requests and @AutoConfigureTestDatabase to replace the real database with an in-memory database. This test checks if the EmployeeRestController and EmployeeRepository work correctly together.

E: This is also an integration test, but it uses TestRestTemplate for sending HTTP requests. It starts the server on a random port (@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)) and interacts with it as a client, making it a more realistic test. It also uses @AutoConfigureTestDatabase to replace the real database with an in-memory database. This test checks if the EmployeeRestController and EmployeeRepository work correctly together in a more realistic environment.

In summary, the first test is a unit test that mocks the service layer, while the other two are integration tests that involve the database. The last test is more realistic because it acts as a client to the server.
