import React from "react";
import { useNavigate } from "react-router-dom";
import "./StartPage.css";

const StartPage: React.FC = () => {
  const navigate = useNavigate();

  const onLogin = () => navigate("/login");
  const onRegister = () => navigate("/register");
  const onStartGame = () => navigate("/game-settings");
  const onAbout = () => navigate("/faq");

  return (
    <div className="start-page">
      <div className="content">
        <h1 className="title">FindMe</h1>

        <button className="start-game-btn" onClick={onStartGame}>
          Начать игру
        </button>

        <div className="auth-buttons">
          <button className="auth-btn" onClick={onLogin}>
            Вход
          </button>
          <button className="auth-btn" onClick={onRegister}>
            Регистрация
          </button>
        </div>

        <button className="about-btn" onClick={onAbout}>
          О проекте
        </button>
      </div>
    </div>
  );
};

export default StartPage;
