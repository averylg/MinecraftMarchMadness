<template>
    <div>
        <h2>Winner:</h2>
        <p>{{ winnerText }}</p>
    </div>
</template>

<script>
export default {
  data() {
    return {
      connectionStatus: "Not connected",
      socket: null,
      winnerText: "",
    };
  },
  methods: {
    connectWebSocket() {
      this.socket = new WebSocket("ws://localhost:8081");

      this.socket.onopen = () => {
        this.connectionStatus = "Connected";
      };

      this.socket.onmessage = (event) => {
        const blobReader = new FileReader();
        blobReader.onload = () => {
          // Convert the Blob to a string and update the state
          const jsonData = JSON.parse(blobReader.result);
          const winnerText = jsonData["winner"]
          this.winnerText = winnerText
        };
        blobReader.readAsText(event.data);
      };
    },
    sendMessage() {
      if (this.socket && this.socket.readyState === WebSocket.OPEN) {
        this.socket.send("Hello from Vue.js!");
      }
    },
  },
  mounted() {
    this.connectWebSocket();
  }
};
</script>

<style scoped>
* {
    display: flex;
    flex-direction: column;
    align-items: center;
}
</style>
