<!-- src/components/SongDetails.vue -->
<template>
  <div>
    <h2>{{ song.title }} by {{ song.artist }}</h2>
    <p>{{ song.lyrics }}</p>
    <button v-if="isAdmin" @click="editSong">Edit</button>
    <button v-if="isAdmin && editing" @click="deleteSong">Delete</button>
    <div v-if="editing">
      <form @submit.prevent="saveSong">
        <input v-model="song.title" placeholder="Title" required />
        <input v-model="song.artist" placeholder="artist" required />
        <textarea
          v-model="song.lyrics"
          placeholder="Lyrics"
          required
        ></textarea>
        <button type="submit">Save</button>
      </form>
    </div>
    <button @click="logout">Logout</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      song: {},
      isAdmin: false,
      editing: false,
    };
  },
  async created() {
    await this.fetchSong();
    this.checkAdmin();
  },
  methods: {
    async fetchSong() {
      try {
        const response = await axios.get(
          `http://localhost:8080/songs/${this.$route.params.id}`
        );
        console.log(response.data);
        this.song = response.data;
      } catch (error) {
        console.error("Error fetching song:", error);
      }
    },
    checkAdmin() {
      const token = localStorage.getItem("token");
      if (token) {
        const payload = JSON.parse(atob(token.split(".")[1]));
        this.isAdmin = payload.role === "admin";
      }
    },
    editSong() {
      this.editing = true;
    },
    async saveSong() {
      try {
        await axios.put(
          `http://localhost:8080/songs/${this.$route.params.id}`,
          this.song,
          {
            headers: {
              artistization: `Bearer ${localStorage.getItem("token")}`,
            },
          }
        );
        this.editing = false;
        this.fetchSong();
      } catch (error) {
        console.error("Error saving song:", error);
      }
    },
    async deleteSong() {
      try {
        await axios.delete(
          `http://localhost:8080/songs/${this.$route.params.id}`,
          {
            headers: {
              artistization: `Bearer ${localStorage.getItem("token")}`,
            },
          }
        );
        this.$router.push("/songs");
      } catch (error) {
        console.error("Error deleting song:", error);
      }
    },
    logout() {
      localStorage.removeItem("token");
      localStorage.removeItem("expiresIn");
      delete axios.defaults.headers.common["artistization"];
      this.$router.push("/login");
    },
  },
};
</script>
