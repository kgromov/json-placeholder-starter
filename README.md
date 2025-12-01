# JSON Placeholder Spring Boot Starter

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A Spring Boot Starter for easy integration with the [JSON Placeholder](https://jsonplaceholder.typicode.com/) API.

## Features

- Auto-configuration for JSON Placeholder REST client
- Support for all JSON Placeholder resources (Posts, Comments, Users, etc.)
- Easy-to-use client interfaces
- Support pagination and sorting
- Configurable base URL
- Built with Spring's `RestClient`

## Installation

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>org.kgromov</groupId>
    <artifactId>json-placeholder-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Configuration

Configure the base URL in your `application.yml` or `application.properties`:

```yaml
json-placeholder:
  base-url: https://jsonplaceholder.typicode.com
```

## Usage

### Autowire the Clients

```java
@Autowired
private PostClient postClient;

@Autowired
private UserClient userClient;

@Autowired
private CommentClient commentClient;
```

### Example: Get All Posts

```java
List<Post> posts = postClient.getAll();
```

### Example: Get Post by ID

```java
Post post = postClient.getById(1L);
```

### Example: Create Post

```java
import org.kgromov.json.placeholder.model.Post;

Post newPost = new Post(-1L, "New Post", "This is a new post", 1L);

Post createdPost = postClient.create(newPost);
```

## Available Clients

- `PostClient` - For managing posts
- `UserClient` - For managing users
- `CommentClient` - For managing comments

## Building from Source

```bash
mvn clean install
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Author

Konstantin Gromov - [@kgromov](https://github.com/kgromov)
