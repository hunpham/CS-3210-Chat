package edu.msudenver.chat.areaMessage;

import edu.msudenver.chat.message.Message;
import edu.msudenver.chat.message.MessageService;
import edu.msudenver.chat.privateMessage.PrivateMessage;
import edu.msudenver.chat.privateMessage.PrivateMessageService;
import edu.msudenver.externalclient.model.ObjectResponse;

import edu.msudenver.externalclient.zoneclient;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@RestController
@RequestMapping(path = "/chat/area")
public class AreaMessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private PrivateMessageService privateMessageService;

    @Autowired
    private zoneclient zoneClient;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Message>> getAreaMessages() {
        return ResponseEntity.ok(messageService.getMessages("AREA"));
        //return ResponseEntity.ok(areaMessageService.getAreaMessages());
    }

    @GetMapping(path = "/{playerId}", produces = "application/json")
    public ResponseEntity<List<PrivateMessage>> getAreaMessages(@PathVariable Long playerId,@RequestParam(required = false) Long epoch) {

        List<PrivateMessage> areaMessage;

        LocalDateTime ldt;
        if(epoch == null) {
            ldt = LocalDateTime.now();
            ldt = ldt.minusSeconds(60);
        }
        else {
            ldt = Instant.ofEpochMilli(epoch)
                    .atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        System.out.println(ldt);
        areaMessage = privateMessageService.getLatestAreaMessages(playerId, ldt);

        return new ResponseEntity<>(areaMessage, areaMessage == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @PostMapping(path = "/{zoneId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<PrivateMessage>> createZoneMessage(@PathVariable Long zoneId, @RequestBody Message areaMessage) {
        try {
            //TODO change this list to an API call that gets player IDs
            List<Long> playerIds = new ArrayList<Long>();
            playerIds.add(1L);
            playerIds.add(2L);
            playerIds.add(3L);

            try {
                List<ObjectResponse> playerResponses = Arrays.asList(zoneClient.getPlayerList(zoneId, 1L, 20L, "Player"));

                if(playerResponses != null && playerResponses.size() > 0)
                {
                    for (ObjectResponse playerResponse : playerResponses) {
                        playerIds.add((long) playerResponse.playerId);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                //I don't expect this to return much
            }
            //create and save the message itself
            LocalDateTime lt = LocalDateTime.now();

            areaMessage.setDateTime(lt);
            areaMessage.setMessageType("AREA");
//            group.setGroupCreationDate(lt);

            Message updatedMessage = messageService.saveMessage(areaMessage);

            //send message to all players
            List<PrivateMessage> returnMessages = new ArrayList<>();
            for(Long playerId : playerIds)
            {
                PrivateMessage privateMessage = new PrivateMessage();

                privateMessage.setMessageId(updatedMessage.getMessageId());
                privateMessage.setReceiver_id(playerId);

                returnMessages.add(privateMessageService.savePrivateMessage(privateMessage));
            }

//            LocalDateTime lt = LocalDateTime.now();
//            areaMessage.setAreaMessageCreationDate(lt);
            return new ResponseEntity<>(returnMessages, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/{zoneId}/{tileId}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<PrivateMessage>> createAreaMessage(@PathVariable Long zoneId, @PathVariable Long tileId, @RequestParam Integer radius, @RequestBody Message areaMessage) {
        try {
            //TODO change this list to an API call that gets player IDs
            List<Long> playerIds = new ArrayList<Long>();
            playerIds.add(4L);
            playerIds.add(5L);

            try {
                List<ObjectResponse> playerResponses = Arrays.asList(zoneClient.getPlayerList(zoneId, tileId, radius.longValue(), "Player"));

                if(playerResponses != null && playerResponses.size() > 0)
                {
                    System.out.println("Player count: " + playerResponses.size());

                    for (ObjectResponse playerResponse : playerResponses) {
                        System.out.println("Object ID: " + playerResponse.objectId);
                        System.out.println("Type ID: " + playerResponse.type);
                        System.out.println("Player ID: " + playerResponse.playerId);
                        Long val = new Long(playerResponse.playerId);
                        playerIds.add(val);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                //I don't expect this to return much
            }

            //create and save the message itself
            LocalDateTime lt = LocalDateTime.now();

            areaMessage.setDateTime(lt);
            areaMessage.setMessageType("AREA");
//            group.setGroupCreationDate(lt);

            Message updatedMessage = messageService.saveMessage(areaMessage);

            //send the message to all the players
            List<PrivateMessage> returnMessages = new ArrayList<>();
            for(Long playerId : playerIds)
            {
                PrivateMessage privateMessage = new PrivateMessage();
                privateMessage.setMessageId(updatedMessage.getMessageId());
                privateMessage.setReceiver_id(playerId);

                returnMessages.add(privateMessageService.savePrivateMessage(privateMessage));
            }

//            LocalDateTime lt = LocalDateTime.now();
//            areaMessage.setAreaMessageCreationDate(lt);
            return new ResponseEntity<>(returnMessages, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(ExceptionUtils.getStackTrace(e), HttpStatus.BAD_REQUEST);
        }
    }

}
