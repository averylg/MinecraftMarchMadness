<template>
  <div class="wrapper">
    <input type="text" v-model="teamA" />
    <input type="text" v-model="teamB" />
    <button @click="handlePostRequest">Sleep is for the weak</button>
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
        const url = "http://localhost:8084/minecwaft";
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
        padding: auto;
    }
    input {
        color: black;
    }

    button {
        background-color: aqua;

    }
</style>
