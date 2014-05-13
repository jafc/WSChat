/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var wsocket = null;
var host = null;

function setHost() {
    host = document.getElementById('formChat:iptHost').value;
    console.log("host: "+host);
}

function init(msgLogin) {
    wsocket = new WebSocket("ws://"+host+":8080/WSChat/wschat2");
    wsocket.onopen = function() {
        console.log(msgLogin);
        wsocket.send(msgLogin);
        console.log("websocket connection established");
    };
    wsocket.onmessage = onSocketMessage;
    wsocket.onclose = onSocketClose;
}

function onSocketMessage(event) {
    console.log("onSocket message: " + event.data);
    console.log("onSocket message: " + event.toString());
    if (event.data) {
        var data = event.data.toString();
        console.log("data = " + data);
        setMessageData([{name: 'msgdata', value: data}]);
    }
}

function sendMessage(message) {
    console.log("Message2.. : " + message);
//    console.log(wsocket);
    wsocket.send(message);
    console.log("Message dispached: " + message);
}

function onSocketClose() {
    wsocket.close();
    console.log("websocket connection terminated.");
}

function bajarScroll() {
    console.log("bajarScroll");
    var scrollPanel = document.getElementById('formChat:spScroll');
    if (scrollPanel !== null) {
        scrollPanel.scrollTop = scrollPanel.scrollHeight;
    }
}

function loginMessage(xhr, status, args) {
    console.log("Login Message: " + args.jsonLogin);
    console.log("Login Message: " + args.jsonMessage);
}
