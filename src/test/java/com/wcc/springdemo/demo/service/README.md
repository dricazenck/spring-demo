# Component Testing Without Spring Container

This directory contains examples of component tests that demonstrate how to test Spring components without using the Spring container.

## What is Component Testing?

Component testing is a form of testing that focuses on testing a component in isolation from its dependencies. In a Spring application, components are typically beans managed by the Spring container, such as services, repositories, and controllers.

## Why Test Without Spring Container?

While Spring provides excellent testing support with annotations like `@SpringBootTest`, there are several benefits to testing components without the Spring container:

1. **Faster execution**: Tests run faster because they don't need to load the Spring context.
2. **Focused testing**: Tests focus only on the component under test and its immediate dependencies.
3. **Better isolation**: Dependencies are explicitly mocked, providing better control over test conditions.
4. **Less fragility**: Tests are less likely to break due to changes in other parts of the application.
5. **Clearer intent**: The test clearly shows what dependencies the component has and how they are used.

## Example: ProductServiceComponentTest

The `ProductServiceComponentTest` class demonstrates how to test the `ProductService` component without using the Spring container:

- It uses Mockito's `@ExtendWith(MockitoExtension.class)` instead of Spring's `@SpringBootTest`.
- It manually creates the `ProductService` instance with a mocked `ProductRepository`.
- It explicitly defines the behavior of the mocked repository for each test case.
- It verifies both the behavior of the service and its interactions with the repository.

## Key Techniques

1. **Manual instantiation**: Create the component under test manually in the `setUp` method.
   ```java
   productService = new ProductService(productRepository);
   ```

2. **Dependency mocking**: Use Mockito to mock dependencies.
   ```java
   @Mock
   private ProductRepository productRepository;
   ```

3. **Behavior definition**: Define the behavior of mocked dependencies for each test.
   ```java
   when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(testProduct));
   ```

4. **Interaction verification**: Verify that the component interacts correctly with its dependencies.
   ```java
   verify(productRepository).findById(PRODUCT_ID);
   ```

## When to Use This Approach

This approach is particularly useful for:

- Unit testing service classes
- Testing components with complex dependencies
- Scenarios where you need precise control over dependency behavior
- Performance-critical test suites

For integration tests that need to verify the interaction between multiple real components, Spring's testing support with `@SpringBootTest` may still be more appropriate.