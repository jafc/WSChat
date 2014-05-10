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
    
    private List<Message> messages;
    
    private final Gson gsonParser;
    
    /**
     * Creates a new instance of ChatView
     */
    public ChatView() {
        System.out.println("new ChatView");
        messages = new LinkedList<Message>();
        gsonParser = new Gson();
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
    
    
    
    public String login() {
        String message = loginName + " ha entrado al chat";
        Message msg = new Message(loginName, message);
        messages.add(msg);
        System.out.println(message);
        RequestContext.getCurrentInstance().execute("init();");
        String jsonMessage = gsonParser.toJson(msg);
        //RequestContext.getCurrentInstance().execute("sendMessage('"+jsonMessage+"');");
        return "chat";
    }
    
    public void sendMessage() {
        String strMessage = txtMessage.getValue().toString();
        Message msg = new Message(loginName, strMessage);
        messages.add(msg);
        String jsonMessage = gsonParser.toJson(msg);
        System.out.println("jsonMessage = "+jsonMessage);
        RequestContext.getCurrentInstance().execute("sendMessage('"+jsonMessage+"');");
    }
    
    public String setMessageData() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map map = ec.getRequestParameterMap();
        String msg = (String) map.get("msgdata");
        Message m = gsonParser.fromJson(msg, Message.class);
        System.out.println("m-loginName: "+m.getUser().toString());
        return null;
    }
}
