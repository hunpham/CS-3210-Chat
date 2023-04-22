/*
package edu.msudenver.chat.Area;

import edu.msudenver.chat.groupMessage.GroupMessage;
import edu.msudenver.chat.message.Message;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PrivateMessage.class)
public class AreaMessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrivateMessageRepository privateMessageRepository;

    @MockBean
    private EntityManagerFactory entityManagerFactory;

    @MockBean
    private EntityManager entityManager;

    @MockBean
    private PrivateMessageService privateMessageService;

    @Test
    void getAreaMessages() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/chat/area/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        AreaMessage testAreaMessage = new AreaMessage();


        testAreaMessage.setMessageId(Long.valueOf("135"));
        testAreaMessage.setReceiver_id(Long.valueOf("465"));

        Mockito.when(privateMessageService.getAreaMessages()).thenReturn(Arrays.asList(testAreaMessage));

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("135"));
        assertTrue(response.getContentAsString().contains("465"));
    }

    @Test
    public void testGetAreaMessageNotFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/chat/are/notfound")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(privateMessageService.getAreaMessage(Mockito.anyLong())).thenReturn(null);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertTrue(response.getContentAsString().isEmpty());
    }

    @Test
    void testGetAreaMessages() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/chat/area/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        AreaMessage testAreaMessage = new AreaMessage();
        testAreaMessage.setReceiver_id(Long.valueOf("135"));
        testAreaMessage.setMessageId(Long.valueOf("465"));



        Mockito.when(privateMessageService.getAreaMessage(Mockito.anyLong())).
                thenReturn(Arrays.asList(testAreaMessage));
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("135"));
        assertTrue(response.getContentAsString().contains("465"));


    }

    @Test
    void createAreaMessage() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/chat/area/1/")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"messageBody\":\"senderId\", \"senderType\": \"messageType\"}")
                .contentType(MediaType.APPLICATION_JSON);

        AreaMessage testAreaMessage = new AreaMessage();
        Message message = new Message("Hello");
        testAreaMessage.setReceiver_id(Long.valueOf("1"));
        testAreaMessage.setMessage(message);
        Mockito.when(privateMessageService.saveAreaMessage(Mockito.any())).thenReturn(null);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Hello"));
    }

    @Test
    public void testCreateAreaMessageBadRequest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/chat/area/1/")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"messageBody\":\"senderId\", \"senderType\": \"messageType\"}")
                .contentType(MediaType.APPLICATION_JSON);

        Mockito.when(privateMessageService.saveAreaMessage(Mockito.any())).thenThrow(IllegalArgumentException.class);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertTrue(response.getContentAsString().contains("Exception"));
    }
}
*/
