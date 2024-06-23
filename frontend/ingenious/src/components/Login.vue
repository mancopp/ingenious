<template>
  <div>
    <h2>Login</h2>
    <span>{{ errorsString }}</span>
    <form @submit.prevent="login">
      <input type="text" v-model="email" placeholder="Email" required />
      <input
        type="password"
        v-model="password"
        placeholder="Password"
        required
      />
      <button type="submit">Login</button>
    </form>
    <router-link to="/register">Don't have an account? Register</router-link>
  </div>
</template>

<script>
import axios from "axios";
import { fetchUserRole } from "../utils/auth.js";

export default {
  data() {
    return {
      email: "",
      password: "",
      errorsString: "",
    };
  },
  methods: {
    async login() {
      try {
        const response = await axios.post("http://localhost:8080/auth/login", {
          email: this.email,
          password: this.password,
        });
        const { token, expiresIn } = response.data;
        localStorage.setItem("token", token);
        localStorage.setItem("expiresIn", Date.now() + expiresIn);
        axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
        fetchUserRole(token);
        this.$router.push("/songs");
        this.setLogoutTimer(expiresIn);
      } catch (error) {
        console.error("Error logging in:", error);
        this.errorsString = error.response.data.description;
      }
    },
    setLogoutTimer(duration) {
      setTimeout(() => {
        this.logout();
      }, duration);
    },
    logout() {
      localStorage.removeItem("token");
      localStorage.removeItem("expiresIn");
      localStorage.removeItem("role");
      delete axios.defaults.headers.common["Authorization"];
      this.$router.push("/login");
    },
  },
};
</script>
