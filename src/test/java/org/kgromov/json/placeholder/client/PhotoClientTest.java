package org.kgromov.json.placeholder.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kgromov.json.placeholder.model.Photo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PhotoClientTest extends AbstractJsonPlaceholderClientTest {

    private PhotoClient photoClient;

    @BeforeEach
    void setUp() {
        photoClient = new PhotoClient(buildRestClient(), null);
    }

    @Test
    void getById_returnsPhoto() {
        expectGet("/photos/1", """
                {"id":1,"title":"Sunset","url":"https://example.com/1.jpg",
                 "thumbnailUrl":"https://example.com/thumb/1.jpg","albumId":1}
                """);

        Photo photo = photoClient.getById(1L);

        assertThat(photo.id()).isEqualTo(1L);
        assertThat(photo.title()).isEqualTo("Sunset");
        assertThat(photo.url()).isEqualTo("https://example.com/1.jpg");
        assertThat(photo.albumId()).isEqualTo(1L);
        mockServer.verify();
    }

    @Test
    void getAll_returnsPhotos() {
        expectGet("/photos", """
                [{"id":1,"title":"Photo 1","url":"https://example.com/1.jpg",
                  "thumbnailUrl":"https://example.com/thumb/1.jpg","albumId":1},
                 {"id":2,"title":"Photo 2","url":"https://example.com/2.jpg",
                  "thumbnailUrl":"https://example.com/thumb/2.jpg","albumId":2}]
                """);

        List<Photo> photos = photoClient.getAll();

        assertThat(photos).hasSize(2);
        assertThat(photos).extracting(Photo::title).containsExactly("Photo 1", "Photo 2");
        mockServer.verify();
    }

    @Test
    void getPhotosByAlbumId_returnsPhotosForAlbum() {
        expectGet("/albums/1/photos", """
                [{"id":1,"title":"Photo 1","url":"https://example.com/1.jpg",
                  "thumbnailUrl":"https://example.com/thumb/1.jpg","albumId":1},
                 {"id":2,"title":"Photo 2","url":"https://example.com/2.jpg",
                  "thumbnailUrl":"https://example.com/thumb/2.jpg","albumId":1}]
                """);

        List<Photo> photos = photoClient.getPhotosByAlbumId(1L);

        assertThat(photos).hasSize(2);
        assertThat(photos).extracting(Photo::albumId).containsOnly(1L);
        mockServer.verify();
    }

    @Test
    void create_returnsCreatedPhoto() {
        expectPost("/photos", """
                {"id":5001,"title":"New Photo","url":"https://example.com/new.jpg",
                 "thumbnailUrl":"https://example.com/thumb/new.jpg","albumId":1}
                """);

        var newPhoto = new Photo(0, "New Photo", "https://example.com/new.jpg",
                "https://example.com/thumb/new.jpg", 1L);
        Photo created = photoClient.create(newPhoto);

        assertThat(created.id()).isEqualTo(5001L);
        assertThat(created.title()).isEqualTo("New Photo");
        mockServer.verify();
    }

    @Test
    void delete_sendsDeleteRequest() {
        expectDelete("/photos/1");

        photoClient.delete(1L);

        mockServer.verify();
    }
}