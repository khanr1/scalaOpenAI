# OpenAI Scala Library

## Overview

This Scala library provides a simple and effective way to interact with the OpenAI API, utilizing the latest features and best practices for asynchronous and resource-efficient programming. The library supports streaming responses and integrates seamlessly with the OpenAI API to deliver chat completions.

## Why Use `Resource`?

### Resource Management

The `Resource` type from the `cats-effect` library is used to manage resources in a safe and efficient manner. In the context of this library, `Resource` is employed to handle HTTP client connections and other resources that need to be cleaned up properly after use. This approach ensures that resources are acquired and released correctly, preventing resource leaks and ensuring that connections are reused effectively.

### Benefits of Using `Resource`

1. **Automatic Resource Management**: `Resource` handles the acquisition and release of resources automatically. This means that resources such as HTTP clients are opened, used, and closed without requiring explicit management by the user.

2. **Safety and Reliability**: By using `Resource`, we minimize the risk of resource leaks. The `Resource` type ensures that resources are released even in the case of exceptions or errors, promoting safe and reliable code.

3. **Simplified API**: Users of the library do not need to manually manage the lifecycle of resources. Instead, they work with a simplified API where resource management is handled internally. This leads to cleaner and more maintainable code.

4. **Integration with Cats Effect**: `Resource` integrates seamlessly with the `cats-effect` library, which is used for asynchronous and concurrent programming in Scala. This allows the library to provide a consistent and efficient approach to handling resources.

## Features

- **Chat Completions**: Interact with the OpenAI API to generate chat completions using different models.
- **Streaming Responses**: Handle streaming responses from the API to process data as it arrives.
- **Asynchronous Operations**: Use `cats-effect` to perform asynchronous operations efficiently.

