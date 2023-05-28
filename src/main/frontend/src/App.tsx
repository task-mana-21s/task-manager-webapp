import React from 'react';
import './App.css';
import { Route, Routes } from 'react-router-dom';
import HomePage from './components/homepage/HomePage';
import LoginPage from './components/loginpage/LoginPage';

function App() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/login" element={<LoginPage />} />
    </Routes>
  );
}

export default App;
