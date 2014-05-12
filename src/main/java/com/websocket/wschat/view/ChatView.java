/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.websocket.wschat.view;

import com.google.gson.Gson;
import com.websocket.wschat.model.Message;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.context.RequestContext;

/**
 *
 * @author usuario
 */
@ManagedBean
@SessionScoped
public class ChatView implements Serializable {

    private String loginName;
    private String password;
    private InputText txtMessage;
    private Boolean logged;
    
    private List<Message> messages;
    
    private final Gson gsonParser;
    
    private static final String LOGOUT_MESSAGE = "se ha desconectado";
    
    /**
     * Creates a new instance of ChatView
     */
    public ChatView() {
        System.out.println("new ChatView");
        messages = new LinkedList<Message>();
        gsonParser = new Gson();
        logged = false;
        //messages.add(new Message("test","test ..."));
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public InputText getTxtMessage() {
        return txtMessage;
    }

    public void setTxtMessage(InputText txtMessage) {
        this.txtMessage = txtMessage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Boolean isLogged() {
        return logged;
    }

    public void setLogged(Boolean logged) {
        this.logged = logged;
    }
    
    
    
    
    public String login() {
        String message = "ha entrado al chat";
        Message msg = new Message(loginName, message);
        System.out.println(message);
        String jsonMessage = gsonParser.toJson(msg);
        RequestContext.getCurrentInstance().execute("init('"+jsonMessage+"');");
        messages.add(msg);
        logged = true;
        return "";
    }
    
    public void sendMessage() {
        String strMessage = txtMessage.getValue().toString();
        Message msg = new Message(loginName, strMessage);
        handleMessage(msg);
        messages.add(msg);
        txtMessage.setValue("");
    }
    
    public String setMessageData() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map map = ec.getRequestParameterMap();
        String msg = (String) map.get("msgdata");
        Message m = gsonParser.fromJson(msg, Message.class);
        messages.add(m);
        System.out.println("m-loginName: "+m.getUser().toString());
        return null;
    }
    
    public String logout() {
        Message msg = new Message(loginName, LOGOUT_MESSAGE);
        handleMessage(msg);
        logged = false;
        messages = new LinkedList<Message>();
        return "";
    }
    
    private void handleMessage(Message msg) {
        String jsonMessage = gsonParser.toJson(msg);
        System.out.println("jsonMessage = "+jsonMessage);
        RequestContext.getCurrentInstance().execute("sendMessage('"+jsonMessage+"');");
    }
}
