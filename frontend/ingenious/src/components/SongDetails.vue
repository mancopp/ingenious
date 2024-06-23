<template>
  <div>
    <button v-if="isAdmin" @click="toggleEditing">
      {{ editing ? "Cancel" : "Edit" }}
    </button>
    <button v-if="isAdmin && editing" @click="deleteSong">Delete</button>
    <div>
      <form @submit.prevent="saveSong">
        <input
          :class="['title', 'one-liner', editing ? '' : 'read']"
          v-model="song.title"
          placeholder="Title"
          required
          :readonly="!editing"
        />
        <input
          :class="['artist', 'one-liner', editing ? '' : 'read']"
          v-model="song.artist"
          placeholder="Artist"
          required
          :readonly="!editing"
        />
        <textarea
          :class="['lyrics', editing ? '' : 'read']"
          v-model="song.lyrics"
          placeholder="Lyrics"
          required
          :readonly="!editing"
        ></textarea>
        <button v-if="isAdmin && editing" type="submit">Save</button>
      </form>
    </div>
    <button @click="logout">Logout</button>
  </div>
</template>

<script>
import axios from "axios";
import { fetchUserRole } from "../utils/auth";

export default {
  data() {
    return {
      song: {},
      editing: false,
      isAdmin: false,
    };
  },
  async created() {
    await this.fetchSong();
    this.isAdmin =
      (await fetchUserRole(localStorage.getItem("token"))) === "ROLE_ADMIN";
  },
  methods: {
    async fetchSong() {
      try {
        const response = await axios.get(
          `http://localhost:8080/songs/${this.$route.params.id}`
        );
        this.song = response.data;
      } catch (error) {
        console.error("Error fetching song:", error);
      }
    },
    toggleEditing() {
      this.editing = !this.editing;
    },
    async saveSong() {
      try {
        await axios.put(`http://localhost:8080/songs/edit`, this.song);
        this.editing = false;
        this.fetchSong(); // Refresh data
      } catch (error) {
        console.error("Error saving song:", error);
      }
    },
    async deleteSong() {
      try {
        await axios.delete(
          `http://localhost:8080/songs/delete/${this.$route.params.id}`
        );
        this.$router.push("/songs");
      } catch (error) {
        console.error("Error deleting song:", error);
      }
    },
    logout() {
      localStorage.removeItem("token");
      localStorage.removeItem("expiresIn");
      localStorage.removeItem("role");
      delete axios.defaults.headers.common["artistization"];
      this.$router.push("/login");
    },
  },
};
</script>

<style scoped>
form {
  width: 500px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: stretch;
  gap: 10px;
}

.one-liner {
  font-size: 1.5rem;
}

.lyrics {
  font-size: 1.1rem;
  height: 60vh;
  white-space: pre-wrap;
  resize: none;
}

.read {
  border: none;
}

.read:focus,
.read:focus-visible {
  border: none;
  outline: none;
}
</style>
