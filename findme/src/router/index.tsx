import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import StartPage from "../pages/StartPage/StartPage";
import FaqPage from "../pages/FaqPage/FaqPage";
import RegisterPage from "../pages/RegisterPage/RegisterPage";
import LoginPage from "../pages/LoginPage/LoginPage";
import ProfilePage from "../pages/ProfilePage/ProfilePage";
import RatingPage from "../pages/RatingPage/RatingPage";
import GameSettingsPage from "../pages/GameSettingsPage/GameSettingsPage";

const AppRouter: React.FC = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<StartPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/game-settings" element={<GameSettingsPage />} />
        <Route path="/faq" element={<FaqPage />} />
        <Route path="/profile" element={<ProfilePage />} />
        <Route path="/rating" element={<RatingPage />} />
      </Routes>
    </BrowserRouter>
  );
};

export default AppRouter;
