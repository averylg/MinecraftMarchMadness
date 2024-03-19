<template>
  <div class="row">
    <div class="col">
      <h2>Team A</h2>
      <p>Name: {{ teamA }}</p>
      <p>Health: {{ teamAHealth }}</p>
    </div>
    <div class="col">
      <h2>Team B</h2>
      <p>Name: {{ teamB }}</p>
      <p>Health: {{ teamBHealth }}</p>
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
      this.socket = new WebSocket("ws://localhost:8081");

      this.socket.onopen = () => {
        this.connectionStatus = "Connected";
      };

      this.socket.onmessage = (event) => {
        // const jsonData = JSON.parse(event.data);
        const blobReader = new FileReader();
        blobReader.onload = () => {
          // Convert the Blob to a string and update the state
          const jsonData = JSON.parse(blobReader.result);
          const firstTeam = jsonData["villager1"];
          const secondTeam = jsonData["villager2"];
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
  margin: auto;
}
.row {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
}
li {
  list-style-type: none;
}
* {
  align-items: center;
}
</style>
