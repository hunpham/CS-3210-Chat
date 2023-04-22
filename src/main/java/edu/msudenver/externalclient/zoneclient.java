package edu.msudenver.externalclient;

import edu.msudenver.externalclient.model.ObjectResponse;
import edu.msudenver.externalclient.model.PlayerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class zoneclient {
    @Value("${zone.service.host}")
    private String zoneServiceHost;

    @Autowired
    protected RestTemplate restTemplate;

    public ObjectResponse[] getPlayerList(Long zoneId, Long tileId, Long radius, String objectType)
    {
        System.out.println(zoneServiceHost + "/zones/1/tiles/1/objects?radius=20&type=\"Player\"");

        //ResponseEntity<ObjectResponse[]> responseEntity = restTemplate.getForEntity(zoneServiceHost + "/zones/{1}/tiles/{2}/objects?radius={3}&type={4}", ObjectResponse[].class, zoneId, tileId, radius, objectType);
        //ObjectResponse[] objects = responseEntity.getBody();

        //return objects;
        return restTemplate.getForObject(zoneServiceHost + "/zones/{1}/tiles/{2}/objects?radius={3}&type={4}", ObjectResponse[].class, zoneId, tileId, radius, objectType);
    }
}
