<template>
  <v-app>
    <Header />
    <v-content>
      <router-view />
    </v-content>
  </v-app>
</template>

<script>
import Header from './components/Header'
import { mapGetters } from 'vuex';
export default {
  name: 'App',
  components: {
    Header
  },
}
</script>
