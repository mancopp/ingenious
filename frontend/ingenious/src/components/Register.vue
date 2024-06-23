<template>
  <div>
    <h2>Register</h2>
    <span>{{ errorsString }}</span>
    <form @submit.prevent="register">
      <input type="text" v-model="fullName" placeholder="First Name" required />
      <input type="text" v-model="email" placeholder="Email" required />
      <input
        type="password"
        v-model="password"
        placeholder="Password"
        required
      />
      <button type="submit">Register</button>
    </form>
    <router-link to="/login">Already have an account? Login</router-link>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      fullName: "",
      email: "",
      password: "",
      errorsString: "",
    };
  },
  methods: {
    async register() {
      try {
        console.log(this.fullName, this.email, this.password);
        await axios.post("http://localhost:8080/auth/signup", {
          fullName: this.fullName,
          email: this.email,
          password: this.password,
        });
        this.$router.push("/login");
      } catch (error) {
        console.error("Error registering:", error);
        this.errorsString = error.response.data.detail;
      }
    },
  },
};
</script>
