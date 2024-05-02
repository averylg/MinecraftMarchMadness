<script setup>
  import Matchup from "./components/Matchup.vue"
  import MatchMonitor from "./components/MatchMonitor.vue"
  import Winner from "./components/Winner.vue"
</script>

<template>
  <header>
    
  </header>

  <main>
    <h1>Enter your team names</h1>
    <Matchup />
    <MatchMonitor />
    <Winner />
  </main>
</template>

<style scoped>
header {
  line-height: 1.5;
}

* {
  color: white;
  align-items: center;
  align-self: center;
}

h1 {
  justify-content: center;
  display: flex;
}

.match {
  display: flex;
  flex-direction: row;
}
</style>
