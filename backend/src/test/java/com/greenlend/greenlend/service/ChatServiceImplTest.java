package com.greenlend.greenlend.service;

import com.greenlend.greenlend.GreenlendApplicationTests;
import com.greenlend.greenlend.model.Chat;
import com.greenlend.greenlend.model.ChatMessage;
import com.greenlend.greenlend.model.GLUser;
import com.greenlend.greenlend.model.LendOffer;
import com.greenlend.greenlend.repository.ChatRepository;
import com.greenlend.greenlend.repository.GLUserRepository;
import com.greenlend.greenlend.repository.MessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;


public class ChatServiceImplTest extends GreenlendApplicationTests {

    @Mock
    private ChatRepository dao;
    @Mock
    private GLUserRepository userDao;
    @Mock
    private MessageRepository messageDao;

    @InjectMocks
    private ChatServiceImpl chatService;

    private long loggedInUserId = 0L;
    private String loggedInUserUsername = "Karel";
    private long basicId = 1L;
    private String basicName = "Pepa";
    private int offsetMessageSize = 16;
    private long anotherId = 4L;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        GLUser loggedInUser = new GLUser();
        GLUser basicUser = new GLUser();
        GLUser someoneElse = new GLUser();
        LendOffer offer = new LendOffer();
        LendOffer someoneElsesOffer = new LendOffer();
        Chat chat = new Chat();
        Chat someoneElsesChat = new Chat();

        // user
        loggedInUser.setUserId(loggedInUserId);
        loggedInUser.setUsername(loggedInUserUsername);
        basicUser.setUserId(basicId + 1L);
        basicUser.setUsername(basicName);
        someoneElse.setUserId(basicId + 2L);
        someoneElse.setUsername("Someone Else");

        when(userDao.findById(loggedInUserId)).thenReturn(java.util.Optional.of(loggedInUser));
        when(userDao.findByUsername(loggedInUserUsername)).thenReturn(loggedInUser);

        // offer
        offer.setOwner(loggedInUser);
        offer.setLendOfferId(basicId);
        someoneElsesOffer.setOwner(someoneElse);
        someoneElsesOffer.setLendOfferId(basicId + 1L);

        // chat
        chat.setOffer(offer);
        chat.setInterested(basicUser);
        chat.setChatId(basicId);
        List<ChatMessage> messages = new ArrayList<>();
        for (int i = 0; i < 35; i++) {
            ChatMessage msg = new ChatMessage();
            msg.setMessageId(i);
            msg.setChat(chat);
            msg.setContent("hello this is message numero " + i);
            if (i % 2 == 0) {
                msg.setFromOwner(loggedInUser.equals(chat.getOffer().getOwner()));
            } else {
                msg.setFromOwner(basicUser.equals(chat.getOffer().getOwner()));
            }
            messages.add(msg);
        }
        chat.setMessages(messages);

        someoneElsesChat.setOffer(someoneElsesOffer);
        someoneElsesChat.setInterested(basicUser);
        someoneElsesChat.setChatId(anotherId);

        when(messageDao.getChatMessageByChatOrderBySentTimeDesc(chat)).thenReturn(chat.getMessages());
        when(messageDao.save(any(ChatMessage.class))).then(returnsFirstArg());
        when(dao.findById(basicId)).thenReturn(java.util.Optional.of(chat));
        when(dao.findById(anotherId)).thenReturn(java.util.Optional.of(someoneElsesChat));

        // security
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(loggedInUser);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(loggedInUserUsername);
    }

    @Test
    public void getCurrentUser_userReturned() {
        GLUser expectedUser = userDao.findById(loggedInUserId).get();

        GLUser actualUser = chatService.getCurrentUser();

        verify(userDao, times(1)).findByUsername(loggedInUserUsername);
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void getMessages_offsetZero_first16messagesReturned() {
        Chat chat = dao.findById(basicId).get();
        List<ChatMessage> expectedMessages = messageDao.getChatMessageByChatOrderBySentTimeDesc(chat).subList(0 * offsetMessageSize, 1 * offsetMessageSize);

        List<ChatMessage> actualMessages = chatService.getMessages(chat, 0L);

        verify(messageDao, times(1 + 1)).getChatMessageByChatOrderBySentTimeDesc(chat);
        assertEquals(expectedMessages.size(), actualMessages.size());
        assertEquals(expectedMessages, actualMessages);
    }

    @Test
    public void getMessages_offsetOne_next16messagesReturned() {
        Chat chat = dao.findById(basicId).get();
        List<ChatMessage> expectedMessages = messageDao.getChatMessageByChatOrderBySentTimeDesc(chat).subList(1 * offsetMessageSize, 2 * offsetMessageSize);

        List<ChatMessage> actualMessages = chatService.getMessages(chat, 1L);

        verify(messageDao, times(1 + 1)).getChatMessageByChatOrderBySentTimeDesc(chat);
        assertEquals(expectedMessages.size(), actualMessages.size());
        assertEquals(expectedMessages, actualMessages);
    }

    @Test
    public void getMessages_offsetTwo_lastThreeMessagesReturned() {
        Chat chat = dao.findById(basicId).get();
        List<ChatMessage> expectedMessages = messageDao.getChatMessageByChatOrderBySentTimeDesc(chat);
        expectedMessages = expectedMessages.subList(2 * offsetMessageSize, expectedMessages.size());

        List<ChatMessage> actualMessages = chatService.getMessages(chat, 2L);

        verify(messageDao, times(1 + 1)).getChatMessageByChatOrderBySentTimeDesc(chat);
        assertEquals(expectedMessages.size(), actualMessages.size());
        assertEquals(expectedMessages, actualMessages);
    }

    @Test
    public void sendMessage_correctChat_messageSaved() {
        String expectedResponse = "200 – OK";

        String response = chatService.sendMessage("ahoj Pepo", basicId);

        verify(dao, times(1)).findById(basicId);
        verify(messageDao, times(1)).save(any(ChatMessage.class));
        assertEquals(expectedResponse, response);
    }

    @Test
    public void sendMessage_wrongChat_messageNotSaved() {
        String expectedResponse = "403 – Forbidden";

        String response = chatService.sendMessage("ahoj Pepo", anotherId);

        verify(dao, times(1)).findById(anotherId);
        verify(messageDao, times(0)).save(any(ChatMessage.class));
        assertEquals(expectedResponse, response);
    }

    @Test
    public void getMembersInChat_twoMembersReturned() {
        GLUser expectedUser1 = new GLUser();
        GLUser expectedUser2 = new GLUser();
        expectedUser1.setUsername(basicName);
        expectedUser2.setUsername(loggedInUserUsername);

        List<GLUser> members = chatService.getMembersInChat(basicId);

        verify(dao, times(1)).findById(basicId);
        assertTrue(members.contains(expectedUser1));
        assertTrue(members.contains(expectedUser2));
    }
}
