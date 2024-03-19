<script setup>
  import Matchup from "./components/Matchup.vue"
  import MatchMonitor from "./components/MatchMonitor.vue"
</script>

<template>
  <header>
    
  </header>

  <main>
    <h1>Enter your team names</h1>
    <Matchup />
    <MatchMonitor />
  </main>
</template>

<style scoped>
header {
  line-height: 1.5;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

* {
  
  color: white;
}
</style>
