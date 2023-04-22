package edu.msudenver.chat.trade;

import edu.msudenver.chat.message.Message;
import edu.msudenver.chat.message.MessageService;
import edu.msudenver.chat.privateMessage.PrivateMessage;
import edu.msudenver.chat.privateMessage.PrivateMessageService;
import edu.msudenver.externalclient.playerclient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = TradeController.class)
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    @MockBean
    private PrivateMessageService privateMessageService;

    @MockBean
    private playerclient playerClient;

    //Not quite sure why this was needed, but taking it out threw errors requesting it to be added
    @MockBean
    private RestTemplateBuilder restTemplateBuilder;

    @MockBean
    private TradeService tradeService;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testGetTrades() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/chat/trades/")
                .contentType(MediaType.APPLICATION_JSON);

        Trade trade = new Trade();
        trade.setTradeId(1111L);
        trade.setTradeStatus((short)2222);
        trade.setPlayer1Approval(true);
        trade.setItem2Quantity((short)3333);
        trade.setItem2Id(4444L);
        trade.setPlayer2Approval(true);
        trade.setItem1Quantity((short)5555);
        trade.setItem1Id(6666L);
        trade.setPlayer1Id(7777L);
        trade.setPlayer2Id(8888L);

        Mockito.when(tradeService.getTrades()).thenReturn(Arrays.asList(trade));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("1111"));
        assertTrue(response.getContentAsString().contains("2222"));
        assertTrue(response.getContentAsString().contains("3333"));
        assertTrue(response.getContentAsString().contains("4444"));
        assertTrue(response.getContentAsString().contains("5555"));
        assertTrue(response.getContentAsString().contains("6666"));
        assertTrue(response.getContentAsString().contains("7777"));
        assertTrue(response.getContentAsString().contains("8888"));
    }

    @Test
    public void testGetTrade() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/chat/trades/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Trade trade = new Trade();
        trade.setTradeId(1111L);
        trade.setTradeStatus((short)2222);
        trade.setPlayer1Approval(true);
        trade.setItem2Quantity((short)3333);
        trade.setItem2Id(4444L);
        trade.setPlayer2Approval(true);
        trade.setItem1Quantity((short)5555);
        trade.setItem1Id(6666L);
        trade.setPlayer1Id(7777L);
        trade.setPlayer2Id(8888L);

        Mockito.when(tradeService.getTrade(Mockito.anyLong())).thenReturn(trade);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("1111"));
        assertTrue(response.getContentAsString().contains("2222"));
        assertTrue(response.getContentAsString().contains("3333"));
        assertTrue(response.getContentAsString().contains("4444"));
        assertTrue(response.getContentAsString().contains("5555"));
        assertTrue(response.getContentAsString().contains("6666"));
        assertTrue(response.getContentAsString().contains("7777"));
        assertTrue(response.getContentAsString().contains("8888"));
    }

    @Test
    public void testGetTradeNotFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/chat/trades/99")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(tradeService.getTrade(Mockito.anyLong())).thenReturn(null);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());
    }

    @Test
    public void testCreateTrade() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/chat/trades")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"item1Quantity\": 1, \"item1Id\": 6666, \"player1Id\": 7777, \"player2Id\": 8888}")
                .contentType(MediaType.APPLICATION_JSON);

        Trade trade = new Trade();
        trade.setTradeId(1111L);
        trade.setTradeStatus((short)2222);
        trade.setPlayer1Approval(true);
        trade.setItem2Quantity((short)1);
        trade.setItem2Id(4444L);
        trade.setPlayer2Approval(true);
        trade.setItem1Quantity((short)1);
        trade.setItem1Id(6666L);
        trade.setPlayer1Id(7777L);
        trade.setPlayer2Id(8888L);

        Message message = new Message();
        message.setMessageBody("Hi");
        message.setDateTime(LocalDateTime.now());
        message.setSenderId(1L);
        message.setSenderType("PLAYER");
        message.setMessageType("TRADE");

        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setMessageId(1L);
        privateMessage.setReceiver_id(2L);

        Mockito.when(messageService.saveMessage(Mockito.any())).thenReturn(message);
        Mockito.when(tradeService.saveTrade(Mockito.any())).thenReturn(trade);
        Mockito.when(privateMessageService.savePrivateMessage(Mockito.any())).thenReturn(privateMessage);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());

    }

    @Test
    public void testCreateTradeBadRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/chat/trades")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"tradeId\":1111, \"tradeStatus\": 2222}")
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(tradeService.saveTrade(Mockito.any())).thenThrow(IllegalArgumentException.class);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Exception"));
    }

    @Test
    public void testUpdateTrade() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/chat/trades/9")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"item2Quantity\": 1, \"item2Id\": 3333}")
                .contentType(MediaType.APPLICATION_JSON);

        Trade trade = new Trade();
        trade.setTradeId(1111L);
        trade.setTradeStatus((short)2222);
        trade.setPlayer1Approval(true);
        trade.setItem2Quantity((short)1);
        trade.setItem2Id(4444L);
        trade.setPlayer2Approval(true);
        trade.setItem1Quantity((short)1);
        trade.setItem1Id(6666L);
        trade.setPlayer1Id(7777L);
        trade.setPlayer2Id(8888L);

        Message message = new Message();
        message.setMessageBody("Hi");
        message.setDateTime(LocalDateTime.now());
        message.setSenderId(1L);
        message.setSenderType("PLAYER");
        message.setMessageType("TRADE");

        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setMessageId(1L);
        privateMessage.setReceiver_id(2L);

        Mockito.when(tradeService.getTrade(Mockito.any())).thenReturn(trade);
        Mockito.when(messageService.saveMessage(Mockito.any())).thenReturn(message);
        Mockito.when(tradeService.saveTrade(Mockito.any())).thenReturn(trade);
        Mockito.when(privateMessageService.savePrivateMessage(Mockito.any())).thenReturn(privateMessage);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void testUpdateTradeBadRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/chat/trades/1")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"tradeId\":1111, \"tradeStatus\": 2222}")
                .contentType(MediaType.APPLICATION_JSON);

        Trade trade = new Trade();
        trade.setTradeId(1111L);
        trade.setTradeStatus((short)2222);
        trade.setPlayer1Approval(true);
        trade.setItem2Quantity((short)1);
        trade.setItem2Id(4444L);
        trade.setPlayer2Approval(true);
        trade.setItem1Quantity((short)1);
        trade.setItem1Id(6666L);
        trade.setPlayer1Id(7777L);
        trade.setPlayer2Id(8888L);

        Mockito.when(tradeService.getTrade(Mockito.any())).thenReturn(trade);
        Mockito.when(tradeService.saveTrade(Mockito.any())).thenThrow(IllegalArgumentException.class);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Exception"));
    }

    @Test
    public void testUpdateTradeNotFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/chat/trades/1")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"tradeId\":1111, \"tradeStatus\": 2222}")
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(tradeService.getTrade(Mockito.any())).thenReturn(null);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());

    }

    @Test
    public void testApproveTrade() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/chat/trades/9/1?approval=true")
                .contentType(MediaType.APPLICATION_JSON);

        Trade trade = new Trade();
        trade.setTradeId(9L);
        trade.setTradeStatus((short)0);
        trade.setPlayer1Approval(true);
        trade.setItem2Quantity((short)1);
        trade.setItem2Id(4444L);
        trade.setPlayer2Approval(true);
        trade.setItem1Quantity((short)1);
        trade.setItem1Id(6666L);
        trade.setPlayer1Id(1L);
        trade.setPlayer2Id(2L);

        Message message = new Message();
        message.setMessageBody("Hi");
        message.setDateTime(LocalDateTime.now());
        message.setSenderId(1L);
        message.setSenderType("PLAYER");
        message.setMessageType("TRADE");

        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setMessageId(1L);
        privateMessage.setReceiver_id(2L);

        Mockito.when(tradeService.getTrade(Mockito.any())).thenReturn(trade);
        Mockito.when(messageService.saveMessage(Mockito.any())).thenReturn(message);
        Mockito.when(tradeService.saveTrade(Mockito.any())).thenReturn(trade);
        Mockito.when(privateMessageService.savePrivateMessage(Mockito.any())).thenReturn(privateMessage);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    public void testApproveTradeBadRequestMissingApproval() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/chat/trades/9/1")
                .contentType(MediaType.APPLICATION_JSON);

        Trade trade = new Trade();
        trade.setTradeId(1111L);
        trade.setTradeStatus((short)0);
        trade.setPlayer1Approval(true);
        trade.setItem2Quantity((short)1);
        trade.setItem2Id(4444L);
        trade.setPlayer2Approval(true);
        trade.setItem1Quantity((short)1);
        trade.setItem1Id(6666L);
        trade.setPlayer1Id(7777L);
        trade.setPlayer2Id(8888L);

        Message message = new Message();
        message.setMessageBody("Hi");
        message.setDateTime(LocalDateTime.now());
        message.setSenderId(1L);
        message.setSenderType("PLAYER");
        message.setMessageType("TRADE");

        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setMessageId(1L);
        privateMessage.setReceiver_id(2L);

        Mockito.when(tradeService.getTrade(Mockito.any())).thenReturn(trade);
        Mockito.when(messageService.saveMessage(Mockito.any())).thenReturn(message);
        Mockito.when(tradeService.saveTrade(Mockito.any())).thenReturn(trade);
        Mockito.when(privateMessageService.savePrivateMessage(Mockito.any())).thenReturn(privateMessage);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());

    }

    @Test
    public void testApproveTradeBadRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/chat/trades/1/1?approval=true")
                .contentType(MediaType.APPLICATION_JSON);

        Trade trade = new Trade();
        trade.setTradeId(1111L);
        trade.setTradeStatus((short)0);
        trade.setPlayer1Approval(true);
        trade.setItem2Quantity((short)1);
        trade.setItem2Id(4444L);
        trade.setPlayer2Approval(true);
        trade.setItem1Quantity((short)1);
        trade.setItem1Id(6666L);
        trade.setPlayer1Id(7777L);
        trade.setPlayer2Id(8888L);

        Message message = new Message();
        message.setMessageBody("Hi");
        message.setDateTime(LocalDateTime.now());
        message.setSenderId(1L);
        message.setSenderType("PLAYER");
        message.setMessageType("TRADE");

        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setMessageId(1L);
        privateMessage.setReceiver_id(2L);


        Mockito.when(messageService.saveMessage(Mockito.any())).thenReturn(message);
        Mockito.when(privateMessageService.savePrivateMessage(Mockito.any())).thenReturn(privateMessage);


        Mockito.when(tradeService.getTrade(Mockito.any())).thenReturn(trade);
        Mockito.when(tradeService.saveTrade(Mockito.any())).thenThrow(IllegalArgumentException.class);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void testApproveTradeNotAcceptable() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/chat/trades/1/1?approval=true")
                .contentType(MediaType.APPLICATION_JSON);

        Trade trade = new Trade();
        trade.setTradeId(1L);
        trade.setTradeStatus((short)2);
        trade.setPlayer1Approval(true);
        trade.setItem2Quantity((short)1);
        trade.setItem2Id(4444L);
        trade.setPlayer2Approval(true);
        trade.setItem1Quantity((short)1);
        trade.setItem1Id(6666L);
        trade.setPlayer1Id(7777L);
        trade.setPlayer2Id(8888L);

        Message message = new Message();
        message.setMessageBody("Hi");
        message.setDateTime(LocalDateTime.now());
        message.setSenderId(1L);
        message.setSenderType("PLAYER");
        message.setMessageType("TRADE");

        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setMessageId(1L);
        privateMessage.setReceiver_id(2L);


        Mockito.when(messageService.saveMessage(Mockito.any())).thenReturn(message);
        Mockito.when(privateMessageService.savePrivateMessage(Mockito.any())).thenReturn(privateMessage);

        Mockito.when(tradeService.getTrade(Mockito.any())).thenReturn(trade);
        Mockito.when(tradeService.saveTrade(Mockito.any())).thenThrow(IllegalArgumentException.class);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_ACCEPTABLE.value(), response.getStatus());
    }

    @Test
    public void testApprovalTradeNotFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/chat/trades/1/1?approval=true")
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(tradeService.getTrade(Mockito.any())).thenReturn(null);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

}
