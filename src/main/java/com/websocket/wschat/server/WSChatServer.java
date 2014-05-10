/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.websocket.wschat.server;

import com.websocket.wschat.logic.MessageDecoder;
import com.websocket.wschat.logic.MessageEncoder;
import com.websocket.wschat.model.Message;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Alexander Filigrana
 */
@ServerEndpoint(
        value = "/wschat",
        encoders = {MessageEncoder.class},
        decoders = {MessageDecoder.class})
public class WSChatServer {
    
    private static final List<Message> messages = 
            Collections.synchronizedList(new LinkedList<Message>());
    
    private static final Set<Session> sessions = 
            Collections.synchronizedSet(new HashSet<Session>());
    
    private static final Logger logger = Logger.getLogger(WSChatServer.class.getName());
    
    @OnMessage
    public void onMessage(Message message, Session session) {
        logger.log(Level.INFO, "Server - onMessage = "+message.getText());
        messages.add(message);
        for(Session openSession : sessions) {
            try {
                openSession.getBasicRemote().sendObject(message);
            } catch (IOException ex) {
                sessions.remove(openSession);
            } catch (EncodeException ex) {
                sessions.remove(openSession);
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
    
    @OnOpen
    public void onOpen(Session session) {
        logger.log(Level.INFO, "onOpen - session id = "+session.getId());
        sessions.add(session);
    }
    
    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }
    
}
