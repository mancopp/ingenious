<!-- src/components/SongList.vue -->
<template>
  <div>
    <h2>Songs</h2>
    <input
      v-model="searchTerm"
      @input="fetchSongs"
      placeholder="Search Songs"
    />
    <ul>
      <li v-for="song in songs" :key="song.id">
        <router-link :to="`/songs/${song.id}`"
          >{{ song.title }} - {{ song.artist }}</router-link
        >
      </li>
    </ul>
    <button v-if="isAdmin" @click="showAddSongModal">Add Song</button>

    <div v-if="showModal">
      <div class="modal">
        <h3>Add Song</h3>
        <form @submit.prevent="addSong">
          <input v-model="newSong.title" placeholder="Title" required />
          <input v-model="newSong.artist" placeholder="artist" required />
          <textarea
            v-model="newSong.lyrics"
            placeholder="Lyrics"
            required
          ></textarea>
          <button type="submit">Add</button>
        </form>
        <button @click="closeModal">Close</button>
      </div>
    </div>
    <button @click="logout">Logout</button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      searchTerm: "",
      songs: [],
      isAdmin: false,
      showModal: false,
      newSong: {
        title: "",
        artist: "",
        lyrics: "",
      },
    };
  },
  async created() {
    await this.fetchSongs();
    this.checkAdmin();
  },
  methods: {
    async fetchSongs() {
      try {
        const request = axios.get("http://localhost:8080/songs/all/min", {
          params: {
            searchTerm: this.searchTerm,
          },
        });
        const response = await request;
        this.songs = response.data;
      } catch (error) {
        console.error("Error fetching songs:", error);
      }
    },
    checkAdmin() {
      const token = localStorage.getItem("token");
      if (token) {
        const payload = JSON.parse(atob(token.split(".")[1]));
        this.isAdmin = payload.role === "admin";
      }
    },
    showAddSongModal() {
      this.showModal = true;
    },
    closeModal() {
      this.showModal = false;
    },
    async addSong() {
      try {
        await axios.post("http://localhost:8080/songs/add", this.newSong, {
          headers: { artistization: `Bearer ${localStorage.getItem("token")}` },
        });
        this.fetchSongs();
        this.closeModal();
      } catch (error) {
        console.error("Error adding song:", error);
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
