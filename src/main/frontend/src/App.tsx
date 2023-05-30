import React, { useState } from "react";
import "./App.css";
import { NavLink, Navigate, Route, Routes } from "react-router-dom";
import LoginPage from "./components/loginpage/LoginPage";
import TaskPage from "./components/taskpage/TaskPage";
import { Navbar } from "reactstrap";
import RegisterPage from './components/registerpage/RegisterPage';
import PrivateRoute from "./components/privateroute/PrivateRoute";
import NavigationBar from "./components/NavigationBar";

function App() {
  return (
    <>
      <NavigationBar />
      <Routes>
        <Route path="/" element={<TaskPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />

        <Route path="/tasks" element={<PrivateRoute><TaskPage /></PrivateRoute>} />
        <Route path="*" element={<Navigate to={"/login"} />} />
      </Routes>
    </>
  );
}

export default App;
