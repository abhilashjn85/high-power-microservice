package com.practice.highpower;

import com.practice.highpower.entity.TweetEntity;
import com.practice.highpower.entity.common.SequenceEntity;
import com.practice.highpower.repository.SequenceRepository;
import com.practice.highpower.repository.TweetRepository;
import com.practice.highpower.service.SequenceGenerator;
import com.practice.highpower.service.TweetService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HighPowerMicroserviceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private SequenceRepository sequenceRepository;

    @Mock
    private SequenceGenerator sequenceGenerator;

    @Test
    public void create() {
        TweetEntity tweetEntity = new TweetEntity("Nothing beats hard work. It demands consistency", "quotes");
        Mockito.when(sequenceGenerator.getNextSequenceId(Mockito.anyString())).thenReturn((long) Math.random());

        webTestClient.post().uri("/")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(tweetEntity), TweetEntity.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.text").isEqualTo("Nothing beats hard work. It demands consistency")
                .jsonPath("$.hash_tag").isEqualTo("quotes");
    }

    @Test
    public void get() {

        Mockito.when(sequenceGenerator.getNextSequenceId(Mockito.anyString())).thenReturn((long) Math.random());

        TweetEntity tweetEntity = tweetRepository.save(new TweetEntity("Sharing knowledge helps us to evolve together")).block();
        webTestClient.get()
                .uri("/tweets/{id}", Collections.singletonMap("id", tweetEntity.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotNull());
    }

    @Test
    public void update() {

        Mockito.when(sequenceGenerator.getNextSequenceId(Mockito.anyString())).thenReturn((long) Math.random());

        TweetEntity tweetEntity = tweetRepository.save(new TweetEntity("Hello world")).block();
        TweetEntity newTweetEntityData = new TweetEntity("Hello Universe");
        webTestClient.put()
                .uri("/tweets/{id}", Collections.singletonMap("id", tweetEntity.getId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(newTweetEntityData), TweetEntity.class)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.text").isEqualTo("Hello Universe");
    }

    @Test
    public void delete() {

        Mockito.when(sequenceGenerator.getNextSequenceId(Mockito.anyString())).thenReturn((long) Math.random());
        TweetEntity tweetEntity = tweetRepository.save(new TweetEntity("Delete, Hello World")).block();
        webTestClient.delete()
                .uri("/tweets/{id}", Collections.singletonMap("id",  tweetEntity.getId()))
                .exchange()
                .expectStatus().isOk();
    }
}
