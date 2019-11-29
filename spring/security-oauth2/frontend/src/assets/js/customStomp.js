import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

import store from '@/store/index'

let socket = new SockJS("https://localhost:3000/spring-websocket");
let stompClient = Stomp.over(socket);

const connection = function(msgs) {
    
  socket = new SockJS("https://localhost:3000/spring-websocket");
  stompClient = Stomp.over(socket);

  stompClient.connect({}, frame => {
    console.log(msgs)
    stompClient.subscribe("/topic/greetings", tick => {
      const result = JSON.parse(tick.body)
      msgs.push(result);
      return msgs
    });

  },error => {
    console.log(error);
    this.connected = false;
  });
}

const sendMessage = function(msg) {
  if (stompClient && stompClient.connected) {
    const sendMsg = { msg: msg, name: store.state.username };
    stompClient.send("/app/chat", JSON.stringify(sendMsg), {});
  }
}

const disConnection = function() {
  if (stompClient) {
    stompClient.disconnect();
  }
  stompClient.connected=false
}

export default {
  connection,
  sendMessage,
  disConnection
};