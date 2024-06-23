<template>
  <div>
    <button v-if="isAdmin" @click="toggleEditing">
      {{ editing ? "Cancel" : isNewSong ? "Cancel" : "Edit" }}
    </button>
    <button v-if="isAdmin && editing && !isNewSong" @click="deleteSong">
      Delete
    </button>
    <div>
      <form @submit.prevent="saveSong">
        <input
          :class="['title', 'one-liner', editing || isNewSong ? '' : 'read']"
          v-model="song.title"
          placeholder="Title"
          required
          :readonly="!(editing || isNewSong)"
        />
        <input
          :class="['artist', 'one-liner', editing || isNewSong ? '' : 'read']"
          v-model="song.artist"
          placeholder="Artist"
          required
          :readonly="!(editing || isNewSong)"
        />
        <textarea
          :class="['lyrics', editing || isNewSong ? '' : 'read']"
          v-model="song.lyrics"
          placeholder="Lyrics"
          required
          :readonly="!(editing || isNewSong)"
        ></textarea>
        <button type="submit">{{ isNewSong ? "Add" : "Save" }}</button>
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
      isAdmin: localStorage.getItem("role") === "ROLE_ADMIN",
      editing: false,
      isNewSong: false, // Indicates if we are adding a new song
    };
  },
  async created() {
    if (this.$route.params.id === "new") {
      // Adding a new song
      this.isNewSong = true;
      this.song = {
        title: "",
        artist: "",
        lyrics: "",
      };
      this.editing = true; // Start in editing mode for adding a new song
    } else {
      // Editing an existing song
      await this.fetchSong();
    }
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
      if (this.isNewSong) {
        this.isNewSong = false; // Cancel adding a new song
      }
    },
    async saveSong() {
      try {
        if (this.isNewSong) {
          // Adding a new song
          await axios.post("http://localhost:8080/songs/add", this.song);
        } else {
          // Editing an existing song
          await axios.put(`http://localhost:8080/songs/edit`, this.song);
        }
        this.editing = false;
        this.isNewSong = false;
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
