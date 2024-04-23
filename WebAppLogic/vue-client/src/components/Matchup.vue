<template>
  <div class="wrapper">
    <div class="input">
        <label>Team 1: </label>
        <input type="text" v-model="teamA" />
    </div>
    <div>
        <label>Team 2: </label>
        <input type="text" v-model="teamB" />
    </div>
    <button @click="handlePostRequest">Spawn Villagers</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      teamA: "",
      teamB: "",
    };
  },
  methods: {
    async handlePostRequest() {
      try {
        const url = "http://localhost:8084/minecraft";
        const data = {
          teamA: this.teamA,
          teamB: this.teamB,
        };
        const response = await fetch(url, {
          method: "POST",
          mode: "cors", // Enable CORS
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(data),
        });
        if (response.ok) {
          const result = await response.text();
          console.log('Response from Spigot server:', result);
          this.teamA = ""
          this.teamB = ""
        } else {
          console.error('Failed to send data:', response.status, response.statusText);
        }
      } catch (error) {
        console.error("Error sending POST request:", error);
      }
    },
  },
};
</script>

<style scoped>
    .wrapper {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
    }
    input {
        color: black;
        padding: 5px;
        margin: 5px;
    }

    button {
        background-color: deepskyblue;
        margin: 10px;
        border: none;
        padding: 10px;
        cursor: pointer;
        border-radius: 5px;
    }
    * {
        align-items: center;
    }
</style>
