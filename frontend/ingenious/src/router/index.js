import { createMemoryHistory, createRouter, RouterLink, RouterView } from 'vue-router';

import Login from '../components/Login.vue';
import Register from '../components/Register.vue';
import SongList from '../components/SongList.vue';
import SongDetails from '../components/SongDetails.vue';

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  { path: '/songs', component: SongList },
  { path: '/songs/:id', component: SongDetails }
]

const router = createRouter({
  history: createMemoryHistory(),
  routes,
})

export default router;
