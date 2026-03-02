import React from "react";
import "./App.css";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Routes, Route, useLocation } from "react-router-dom";
import Navbar from "./Components/Navbar";
import Footer from "./Components/Footer";
import Home from "./Components/Home.jsx";
import Home2 from "./Components/Actual.jsx";
import Login from "./Components/Login.jsx";
import Assignments from "./Components/Assignments.jsx";
import Quizzes from "./Components/Quizzes.jsx";
import Profile from "./Components/Profile.jsx";
import Course from "./Components/Course.jsx";

function App() {
  const location = useLocation();

const hideLayout =
  location.pathname === "/" ||
  location.pathname === "/login";

return (
  <div className="App">

    {!hideLayout && <Navbar />}

    <div className="page-content">
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/Home2" element={<Home2 />} />
        <Route path="/Assignments" element={<Assignments />} />
        <Route path="/Quizzes" element={<Quizzes />} />
        <Route path="/Profile" element={<Profile />} />
        <Route path="/Course" element={<Course />} />
      </Routes>
    </div>

    {!hideLayout && <Footer />}

    <ToastContainer position="top-right" autoClose={3000} />
  </div>
);
}

export default App;