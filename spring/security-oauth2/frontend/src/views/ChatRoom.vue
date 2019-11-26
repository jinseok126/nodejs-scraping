<template>
  <div class="inner-wrap" fluid fill-height inner-wrap>
    <Message-List :msgs="msgs"></Message-List>
    <Message-Form v-on:submitMessage="sendMessage"></Message-Form>
  </div>
</template>

<script>
import MessageList from '@/components/chat/MessageList.vue'
import MessageForm from '@/components/chat/MessageForm.vue'

export default {
  name: 'ChatRoom',
  components: {
    'Message-List': MessageList,
    'Message-Form': MessageForm
  },
  data: () => ({
    msgs: [{msg: 'zzzzzzzzzzzzzzzz', name: 'userName'}]
  }),
  methods: {
    sendMessage(msg) {
      const sendMsg = {}
      sendMsg.msg = msg
      sendMsg.name=this.$route.params.username
      this.msgs.push(sendMsg)

      // 메세지 쐈을 때 다른 클라이언트에게 메세지를 전송하는 부분
      this.$sendMessage({
        name: this.$route.params.username,
        msg: msg
      })
    },
    
  },
  created() {
    // 메세지 수신하는 부분
    this.$socket.on('chat', (data) => {
      console.log(data);
      this.msgs.push(data)
    })
  }
}
</script>
