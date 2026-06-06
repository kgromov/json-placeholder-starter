package org.kgromov.json.placeholder.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kgromov.json.placeholder.model.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PostClientTest extends AbstractJsonPlaceholderClientTest {

    private PostClient postClient;

    @BeforeEach
    void setUp() {
        postClient = new PostClient(buildRestClient(), null);
    }

    @Test
    void getById_returnsPost() {
        expectGet("/posts/1", """
                {"id":1,"title":"title","body":"body","userId":1}
                """);

        Post post = postClient.getById(1L);

        assertThat(post).isNotNull();
        assertThat(post.id()).isEqualTo(1L);
        assertThat(post.title()).isEqualTo("title");
        assertThat(post.body()).isEqualTo("body");
        assertThat(post.userId()).isEqualTo(1L);
        mockServer.verify();
    }

    @Test
    void getAll_returnsPosts() {
        expectGet("/posts", """
                [{"id":1,"title":"t1","body":"b1","userId":1},
                 {"id":2,"title":"t2","body":"b2","userId":2}]
                """);

        List<Post> posts = postClient.getAll();

        assertThat(posts).hasSize(2);
        assertThat(posts).extracting(Post::id).containsExactly(1L, 2L);
        mockServer.verify();
    }

    @Test
    void getUserPosts_returnsPostsForUser() {
        expectGet("/users/1/posts", """
                [{"id":1,"title":"t1","body":"b1","userId":1}]
                """);

        List<Post> posts = postClient.getUserPosts(1L);

        assertThat(posts).hasSize(1);
        assertThat(posts.getFirst().userId()).isEqualTo(1L);
        mockServer.verify();
    }

    @Test
    void create_returnsCreatedPost() {
        expectPost("/posts", """
                {"id":101,"title":"new","body":"content","userId":1}
                """);

        Post created = postClient.create(new Post(0, "new", "content", 1L));

        assertThat(created.id()).isEqualTo(101L);
        assertThat(created.title()).isEqualTo("new");
        mockServer.verify();
    }

    @Test
    void update_returnsUpdatedPost() {
        expectPut("/posts/1", """
                {"id":1,"title":"updated","body":"content","userId":1}
                """);

        Post updated = postClient.update(1L, new Post(1, "updated", "content", 1L));

        assertThat(updated.title()).isEqualTo("updated");
        mockServer.verify();
    }

    @Test
    void patch_returnsPartiallyUpdatedPost() {
        expectPatch("/posts/1", """
                {"id":1,"title":"patched","body":"body","userId":1}
                """);

        Post patched = postClient.patch(1L, new Post(1, "patched", null, null));

        assertThat(patched.title()).isEqualTo("patched");
        mockServer.verify();
    }

    @Test
    void delete_sendsDeleteRequest() {
        expectDelete("/posts/1");

        postClient.delete(1L);

        mockServer.verify();
    }

    @Test
    void getAllByQueryParams_singleValue_filtersCorrectly() {
        expectGet("/posts?userId=1", """
                [{"id":1,"title":"t1","body":"b1","userId":1}]
                """);

        List<Post> posts = postClient.getAllByQueryParams(Map.of("userId", "1"));

        assertThat(posts).hasSize(1);
        mockServer.verify();
    }

    @Test
    void getAllByQueryParams_multiValue_filtersCorrectly() {
        expectGet("/posts?id=1&id=2", """
                [{"id":1,"title":"t1","body":"b1","userId":1},
                 {"id":2,"title":"t2","body":"b2","userId":1}]
                """);

        var params = new LinkedMultiValueMap<String, String>();
        params.add("id", "1");
        params.add("id", "2");
        List<Post> posts = postClient.getAllByQueryParams(params);

        assertThat(posts).hasSize(2);
        mockServer.verify();
    }

    @Test
    void getPage_withSort_buildsPaginatedUri() {
        expectGet("/posts?_start=0&_page=0&_limit=5&_sort=title&_order=ASC", """
                [{"id":1,"title":"aaa","body":"b","userId":1}]
                """);

        var pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "title"));
        List<Post> page = postClient.getPage(pageable);

        assertThat(page).hasSize(1);
        assertThat(page).extracting(Post::title).containsExactly("aaa");
        assertThat(page).extracting(Post::body).containsExactly("b");
        assertThat(page).extracting(Post::userId).containsExactly(1L);
        mockServer.verify();
    }
}