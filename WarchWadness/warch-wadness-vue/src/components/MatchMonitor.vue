<template>
  <div class="row">
    <div class="col">
      <p>Team A</p>
      <ul>
        <li>Name: {{ teamA }}</li>
        <li>Health: {{ teamAHealth }}</li>
      </ul>
    </div>
    <div class="col">
      <p>Team B</p>
      <ul>
        <li>Name: {{ teamB }}</li>
        <li>Health: {{ teamBHealth }}</li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      connectionStatus: "Not connected",
      socket: null,
      teamA: "",
      teamAHealth: 0,
      teamB: "",
      teamBHealth: 0,
    };
  },
  methods: {
    connectWebSocket() {
      this.socket = new WebSocket("ws://localhost:8081"); // Replace with your server's WebSocket URL

      this.socket.onopen = () => {
        this.connectionStatus = "Connected";
      };

      this.socket.onmessage = (event) => {
        // const jsonData = JSON.parse(event.data);
        const blobReader = new FileReader();
        blobReader.onload = () => {
          // Convert the Blob to a string and update the state
          const jsonData = JSON.parse(blobReader.result);
          const firstTeam = jsonData["villagers"][0];
          const secondTeam = jsonData["villagers"][1];
          this.teamA = firstTeam["name"];
          this.teamAHealth = firstTeam["health"];
          this.teamB = secondTeam["name"];
          this.teamBHealth = secondTeam["health"];
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
  },
};
</script>

<style scoped>
.col {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.row {
  display: flex;
  flex-direction: row;
}
</style>
