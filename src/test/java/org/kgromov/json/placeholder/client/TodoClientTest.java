package org.kgromov.json.placeholder.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kgromov.json.placeholder.model.Todo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TodoClientTest extends AbstractJsonPlaceholderClientTest {

    private TodoClient todoClient;

    @BeforeEach
    void setUp() {
        todoClient = new TodoClient(buildRestClient(), null);
    }

    @Test
    void getById_returnsTodo() {
        expectGet("/todos/1", """
                {"id":1,"title":"Buy milk","completed":false,"userId":1}
                """);

        Todo todo = todoClient.getById(1L);

        assertThat(todo.id()).isEqualTo(1L);
        assertThat(todo.title()).isEqualTo("Buy milk");
        assertThat(todo.completed()).isFalse();
        assertThat(todo.userId()).isEqualTo(1L);
        mockServer.verify();
    }

    @Test
    void getAll_returnsTodos() {
        expectGet("/todos", """
                [{"id":1,"title":"Task 1","completed":false,"userId":1},
                 {"id":2,"title":"Task 2","completed":true,"userId":1}]
                """);

        List<Todo> todos = todoClient.getAll();

        assertThat(todos).hasSize(2);
        assertThat(todos).extracting(Todo::completed).containsExactly(false, true);
        mockServer.verify();
    }

    @Test
    void getUserTodos_returnsTodosForUser() {
        expectGet("/users/1/todos", """
                [{"id":1,"title":"Task 1","completed":false,"userId":1},
                 {"id":2,"title":"Task 2","completed":true,"userId":1}]
                """);

        List<Todo> todos = todoClient.getUserTodos(1L);

        assertThat(todos).hasSize(2);
        assertThat(todos).extracting(Todo::userId).containsOnly(1L);
        mockServer.verify();
    }

    @Test
    void create_returnsCreatedTodo() {
        expectPost("/todos", """
                {"id":201,"title":"New Task","completed":false,"userId":1}
                """);

        Todo created = todoClient.create(new Todo(0, "New Task", false, 1L));

        assertThat(created.id()).isEqualTo(201L);
        assertThat(created.title()).isEqualTo("New Task");
        assertThat(created.completed()).isFalse();
        mockServer.verify();
    }

    @Test
    void update_returnsUpdatedTodo() {
        expectPut("/todos/1", """
                {"id":1,"title":"Buy milk","completed":true,"userId":1}
                """);

        Todo updated = todoClient.update(1L, new Todo(1, "Buy milk", true, 1L));

        assertThat(updated.completed()).isTrue();
        mockServer.verify();
    }

    @Test
    void delete_sendsDeleteRequest() {
        expectDelete("/todos/1");

        todoClient.delete(1L);

        mockServer.verify();
    }
}