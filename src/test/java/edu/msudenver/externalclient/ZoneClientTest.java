package edu.msudenver.externalclient;

        import edu.msudenver.externalclient.model.ObjectResponse;
        import edu.msudenver.externalclient.model.PlayerResponse;
        import org.junit.jupiter.api.BeforeEach;
        import org.junit.jupiter.api.Test;
        import org.junit.jupiter.api.extension.ExtendWith;
        import org.mockito.Mock;
        import org.mockito.Mockito;
        import org.mockito.junit.jupiter.MockitoExtension;
        import org.springframework.web.client.RestTemplate;

        import java.time.LocalDateTime;
        import java.util.Optional;

        import static org.junit.Assert.assertThrows;
        import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ZoneClientTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private zoneclient zoneClient;

    @BeforeEach
    public void setup() {
        zoneClient = new zoneclient();
        zoneClient.restTemplate = restTemplate;

    }

    @Test
    public void testGetPlayerList() throws Exception {

        LocalDateTime dt = LocalDateTime.now();

        ObjectResponse objectResponse = new ObjectResponse();
        objectResponse.playerId = 1;
        objectResponse.objectId = 1;

        ObjectResponse[] objectResponses = new ObjectResponse[]{objectResponse};

       // public ObjectResponse[] getPlayerList(Long zoneId, Long tileId, Long radius, String objectType)
        //{
        //    return restTemplate.getForObject(zoneServiceHost + "/zones/{1}/tiles/{2}/objects?radius={3}&type={4}", ObjectResponse[].class, zoneId, tileId, radius, objectType);
       // }
//return restTemplate.getForObject(playerServiceHost + "/players/{1}", PlayerResponse.class, playerId);
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(ObjectResponse[].class),Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong(),Mockito.anyString())).thenReturn(objectResponses);

        ObjectResponse[] actualObjectResponses = zoneClient.getPlayerList(1L,1L,1L,"Player");

        assertEquals(objectResponses,actualObjectResponses);
    }

    @Test
    public void testGetPlayerListNullException() throws Exception {
        Mockito.when(restTemplate.getForObject(Mockito.anyString(),
                Mockito.eq(ObjectResponse[].class),Mockito.anyLong(),Mockito.anyLong(),Mockito.anyLong(),Mockito.anyString())).thenReturn(null);

        assertEquals(null, zoneClient.getPlayerList(1L,1L,1L,"Player"));
    }
}
