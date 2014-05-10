/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.websocket.wschat.logic;

import com.websocket.wschat.model.Message;
import java.io.IOException;
import java.io.Writer;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.spi.JsonProvider;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author usuario
 */
public class MessageEncoder implements Encoder.TextStream<Message> {

    @Override
    public void encode(Message message, Writer writer) throws EncodeException, IOException {
        System.out.println("Encoding : "+message.getMessage());
        JsonProvider provider = JsonProvider.provider();
        JsonObject jsonMessage = provider.createObjectBuilder()
                .add("user", message.getUser().toString())
                .add("message", message.getMessage().toString())
                .build();
        JsonWriter jsonWriter = provider.createWriter(writer);
        jsonWriter.writeObject(jsonMessage);
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }
    
}
