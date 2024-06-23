import { createApp } from 'vue';
import router from './router/index.js';
import './style.css';
import App from './App.vue';
import { initializeAuth } from './utils/auth.js';

createApp(App).use(router).mount('#app');

initializeAuth();