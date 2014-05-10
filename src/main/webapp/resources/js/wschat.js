/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var wsocket = null;

function init() {
    wsocket = new WebSocket("ws://localhost:8080/WSChat/wschat");
    wsocket.onopen = function() {
       wsocket.send("websocket connection established");
      console.log("websocket connection established");
    };
    wsocket.onmessage = onSocketMessage;    
}

function onSocketMessage(event) {
    console.log("onSocket message: "+event.data);
    console.log("onSocket message: "+event.toString());
    if(event.data) {
        var data = event.data.toString();
        console.log(data);
        setMessageData([{name: 'msgdata', value: data}]);
    }
}

function sendMessage(message) {
    console.log("Message2.. : "+message);
//    console.log(wsocket);
    wsocket.send(message);
    console.log("Message dispached: "+message);
}
