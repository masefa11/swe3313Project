# Coding Style Guide
## Coding Style
1. Break long lines after 100 characters. Turn on your IDE's "visual guide" to see where the 100 character limit is.
2. Include whitespace in your methods, properties, etc. to increase readability

## Automated Testing
1. Use FluentAssertions instead of XUnit assertions. FluentAssertions are more descriptive.
2. Follow the AAA pattern (Arrange, Act, Assert) when writing tests.
3. Name your tests with the following pattern: MethodName_StateUnderTest ExpectedBehavior. For example, GetPrice_WhenCalled_ReturnsPrice

## Integration Testing 
1. When testing large numbers of database records for a condition, capture the results in a list rather than asserting on each record. When asserting on each record, the scope of the problem can be masked. Often when one record is wrong, many are wrong.
2. Always return appropriate database context to help the developer locate the problem rapidly
