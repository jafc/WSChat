/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.websocket.wschat.logic;

import com.websocket.wschat.model.Message;
import java.io.IOException;
import java.io.Reader;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.spi.JsonProvider;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author usuario
 */
public class MessageDecoder implements Decoder.TextStream<Message> {

    @Override
    public Message decode(Reader reader) throws DecodeException, IOException {
        JsonProvider provider = JsonProvider.provider();
        JsonReader jsonReader = provider.createReader(reader);
        JsonObject jsonMessage = jsonReader.readObject();
        Message message = new Message();
        message.setUser(jsonMessage.get("user"));
        message.setMessage(jsonMessage.get("message"));
        return message;
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
    
}
