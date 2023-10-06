// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
import { getStorage } from "firebase/storage";

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyB05n0A59hAl1M9iNHSAc5Z-fUSz_KsE4I",
  authDomain: "bunnies-aad60.firebaseapp.com",
  projectId: "bunnies-aad60",
  storageBucket: "bunnies-aad60.appspot.com",
  messagingSenderId: "927333266951",
  appId: "1:927333266951:web:6279d372be6af35ee31ca2",
  measurementId: "G-NN30VQ6VXE"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
// const analytics = getAnalytics(app);

export const storage = getStorage(app);