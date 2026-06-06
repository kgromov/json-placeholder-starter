package org.kgromov.json.placeholder.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kgromov.json.placeholder.model.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CommentClientTest extends AbstractJsonPlaceholderClientTest {

    private CommentClient commentClient;

    @BeforeEach
    void setUp() {
        commentClient = new CommentClient(buildRestClient(), null);
    }

    @Test
    void getById_returnsComment() {
        expectGet("/comments/1", """
                {"id":1,"postId":1,"name":"Alice","email":"alice@example.com","body":"Great post!"}
                """);

        Comment comment = commentClient.getById(1L);

        assertThat(comment.id()).isEqualTo(1L);
        assertThat(comment.postId()).isEqualTo(1L);
        assertThat(comment.name()).isEqualTo("Alice");
        mockServer.verify();
    }

    @Test
    void getAll_returnsComments() {
        expectGet("/comments", """
                [{"id":1,"postId":1,"name":"Alice","email":"alice@example.com","body":"Nice!"},
                 {"id":2,"postId":2,"name":"Bob","email":"bob@example.com","body":"Agreed!"}]
                """);

        List<Comment> comments = commentClient.getAll();

        assertThat(comments).hasSize(2);
        assertThat(comments).extracting(Comment::name).containsExactly("Alice", "Bob");
        mockServer.verify();
    }

    @Test
    void getPostComments_returnsCommentsForPost() {
        expectGet("/posts/1/comments", """
                [{"id":1,"postId":1,"name":"Alice","email":"alice@example.com","body":"Nice!"},
                 {"id":2,"postId":1,"name":"Bob","email":"bob@example.com","body":"Agreed!"}]
                """);

        List<Comment> comments = commentClient.getPostComments(1L);

        assertThat(comments).hasSize(2);
        assertThat(comments).extracting(Comment::postId).containsOnly(1L);
        mockServer.verify();
    }

    @Test
    void create_returnsCreatedComment() {
        expectPost("/comments", """
                {"id":501,"postId":1,"name":"Dave","email":"dave@example.com","body":"Interesting!"}
                """);

        var newComment = new Comment(0, 1L, "Dave", "dave@example.com", "Interesting!");
        Comment created = commentClient.create(newComment);

        assertThat(created.id()).isEqualTo(501L);
        assertThat(created.name()).isEqualTo("Dave");
        mockServer.verify();
    }

    @Test
    void delete_sendsDeleteRequest() {
        expectDelete("/comments/1");

        commentClient.delete(1L);

        mockServer.verify();
    }
}