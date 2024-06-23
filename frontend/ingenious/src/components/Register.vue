<template>
  <div>
    <h2>Register</h2>
    <form @submit.prevent="register">
      <input
        type="text"
        v-model="firstName"
        placeholder="First Name"
        required
      />
      <input type="text" v-model="email" placeholder="Email" required />
      <input
        type="password"
        v-model="password"
        placeholder="Password"
        required
      />
      <button type="submit">Register</button>
    </form>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      firstName: "",
      email: "",
      password: "",
    };
  },
  methods: {
    async register() {
      try {
        await axios.post("http://localhost:8080/auth/signup", {
          firstName: this.firstName,
          email: this.email,
          password: this.password,
        });
        this.$router.push("/login");
      } catch (error) {
        console.error("Error registering:", error);
      }
    },
  },
};
</script>
