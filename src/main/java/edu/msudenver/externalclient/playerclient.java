package edu.msudenver.externalclient;

import edu.msudenver.externalclient.model.InventoryItemRequest;
import edu.msudenver.externalclient.model.InventoryItemResponse;
import edu.msudenver.externalclient.model.PlayerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class playerclient {
    //i.e https://cityservice.heroku.com
    @Value("${player.service.host}")
    private String playerServiceHost;

    @Autowired
    protected RestTemplate restTemplate;

    public PlayerResponse getPlayer(Long playerId)
    {
        return restTemplate.getForObject(playerServiceHost + "/players/{1}", PlayerResponse.class, playerId);
    }

    public InventoryItemResponse getInventoryItem(Long playerId, Long itemId)
    {
        return restTemplate.getForObject(playerServiceHost + "/players/{1}/inventory/{2}", InventoryItemResponse.class, playerId,itemId);
    }

    //public Long[] getAllPlayerIDs(int radius)
    //{
    //    return restTemplate.getForObject(privateChatServiceHost + "/players?radius={1}", Long[].class, radius);
    //}

    public InventoryItemResponse postInventoryItem(Long accountId, String characterName, InventoryItemRequest inventoryItemRequest)
    {
        return restTemplate.postForObject(playerServiceHost + "/accounts/" + accountId + "/characters/" + characterName + "/inventory",
                inventoryItemRequest,
                InventoryItemResponse.class);
    }

    public InventoryItemResponse patchInventoryItemAdd(Long accountId, String characterName, InventoryItemRequest inventoryItemRequest)
    {
        return restTemplate.patchForObject(playerServiceHost + "/accounts/" + accountId + "/characters/" + characterName + "/inventory/inventory/" + inventoryItemRequest.inventoryId + "/quantity/add",
                inventoryItemRequest,
                InventoryItemResponse.class);
    }

    public InventoryItemResponse patchInventoryItemRemove(Long accountId, String characterName, InventoryItemRequest inventoryItemRequest)
    {
        return restTemplate.patchForObject(playerServiceHost + "/accounts/" + accountId + "/characters/" + characterName + "/inventory/inventory/" + inventoryItemRequest.inventoryId + "/quantity/remove",
                inventoryItemRequest,
                InventoryItemResponse.class);
    }
    public InventoryItemResponse[] getInventoryItems(Long accountId, String characterName)
    {
        return restTemplate.getForObject(playerServiceHost + "/accounts/" + accountId + "/characters/" + characterName + "/inventory",
                InventoryItemResponse[].class);
    }

    //to remove an item:
    //get all the inventory items (get accounts/id/characters/name/inventory)
    //go through each one looking for a matching catalog id and a necessary quantity
    //(im going to be lazy and if the right amount of items aren't in a single inventory slot I'm going to fail)
    //if fail, done
    //if succeed, remove x items from that list (patch accounts/id/characters/name/inventory/inventory/inventoryid/quantity/remove)
    //Does the remove take a parameter or a json object or just remove 1 per call?

    //to add an item:
    //post inventory item (lazy method--post accounts/id/characters/name/inventory with item)
    //alternatively, do the same as removing an item but patch using the add patch, and if nothing found then do post)

    //public Long postPlayerID(PlayerRequest playerRequest)
    //{
    //    return restTemplate.postForObject(privateChatServiceHost + "/players/", playerRequest,Long.class);
    //}

}

//added private.chat.service.host to the application-local.properties file
//Take the request and response json objects and codify them from the internet (convert json to java)
//put these in a models.response and models.request package

//autowire this class into or controller