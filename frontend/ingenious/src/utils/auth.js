import axios from 'axios';
import router from '../router/index';

export function initializeAuth() {
  const token = localStorage.getItem("token");
  const expiresIn = localStorage.getItem("expiresIn");
  const currentTime = Date.now();

  if (token && expiresIn && currentTime < expiresIn) {
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
    setLogoutTimer(expiresIn - currentTime);
    fetchUserRole(token);
    router.push("/songs");
  } else {
    localStorage.removeItem("token");
    localStorage.removeItem("expiresIn");
    delete axios.defaults.headers.common["Authorization"];
    router.push("/login");
  }
}

function setLogoutTimer(duration) {
  setTimeout(() => {
    localStorage.removeItem("token");
    localStorage.removeItem("expiresIn");
    delete axios.defaults.headers.common["Authorization"];
    router.push("/login");
  }, duration);
}

async function fetchUserRole(token) {
  try {
    const response = await axios.get('http://localhost:8080/auth/role', {
      headers: { Authorization: `Bearer ${token}` }
    });
    const role = response.data;
    console.log("User role:", role);
    localStorage.setItem("role", role);
  } catch (error) {
    console.error("Error fetching user role:", error);
  }
}