/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.websocket.wschat.model;

import java.io.Serializable;

/**
 *
 * @author usuario
 */
public class Message implements Serializable {
    
    private Object user;
    private Object message;

    public Message() {
    }

    public Message(Object user, Object message) {
        this.user = user;
        this.message = message;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
    
    public String getText() {
        return message.toString();
    }
    
    
}
