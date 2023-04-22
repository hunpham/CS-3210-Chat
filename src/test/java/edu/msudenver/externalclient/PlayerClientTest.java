package edu.msudenver.externalclient;

import edu.msudenver.externalclient.model.PlayerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PlayerClientTest {


    @Mock
    private RestTemplate restTemplate;

    @Mock
    private playerclient playerClient;

    @BeforeEach
    public void setup() {
        playerClient = new playerclient();
        playerClient.restTemplate = restTemplate;

    }

    @Test
    public void testGetPlayer() throws Exception {

        LocalDateTime dt = LocalDateTime.now();

        PlayerResponse playerResponse = new PlayerResponse();
        playerResponse.playerId = 1L;
        playerResponse.Name = "Hi";
        playerResponse.isOnline = true;

//return restTemplate.getForObject(playerServiceHost + "/players/{1}", PlayerResponse.class, playerId);
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(PlayerResponse.class),Mockito.anyLong())).thenReturn(playerResponse);

        PlayerResponse actualPlayerResponse = playerClient.getPlayer(1L);

        assertEquals(playerResponse,actualPlayerResponse);
    }

    @Test
    public void testGetPlayerNullException() throws Exception {
        Mockito.when(restTemplate.getForObject(Mockito.anyString(),
                Mockito.eq(PlayerResponse.class),Mockito.anyLong())).thenReturn(null);

        assertEquals(null, playerClient.getPlayer(99L));
    }
}
