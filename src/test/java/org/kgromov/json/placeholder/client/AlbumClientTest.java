package org.kgromov.json.placeholder.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kgromov.json.placeholder.model.Album;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AlbumClientTest extends AbstractJsonPlaceholderClientTest {

    private AlbumClient albumClient;

    @BeforeEach
    void setUp() {
        albumClient = new AlbumClient(buildRestClient(), null);
    }

    @Test
    void getById_returnsAlbum() {
        expectGet("/albums/1", """
                {"id":1,"title":"My Album","userId":1}
                """);

        Album album = albumClient.getById(1L);

        assertThat(album.id()).isEqualTo(1L);
        assertThat(album.title()).isEqualTo("My Album");
        assertThat(album.userId()).isEqualTo(1L);
        mockServer.verify();
    }

    @Test
    void getAll_returnsAlbums() {
        expectGet("/albums", """
                [{"id":1,"title":"Album 1","userId":1},
                 {"id":2,"title":"Album 2","userId":2}]
                """);

        List<Album> albums = albumClient.getAll();

        assertThat(albums).hasSize(2);
        assertThat(albums).extracting(Album::title).containsExactly("Album 1", "Album 2");
        mockServer.verify();
    }

    @Test
    void getUserAlbums_returnsAlbumsForUser() {
        expectGet("/users/1/albums", """
                [{"id":1,"title":"My Album","userId":1},
                 {"id":2,"title":"Another Album","userId":1}]
                """);

        List<Album> albums = albumClient.getUserAlbums(1L);

        assertThat(albums).hasSize(2);
        assertThat(albums).extracting(Album::userId).containsOnly(1L);
        mockServer.verify();
    }

    @Test
    void create_returnsCreatedAlbum() {
        expectPost("/albums", """
                {"id":101,"title":"New Album","userId":1}
                """);

        Album created = albumClient.create(new Album(0, "New Album", 1L));

        assertThat(created.id()).isEqualTo(101L);
        assertThat(created.title()).isEqualTo("New Album");
        mockServer.verify();
    }

    @Test
    void delete_sendsDeleteRequest() {
        expectDelete("/albums/1");

        albumClient.delete(1L);

        mockServer.verify();
    }
}