import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { loginUser } from "../../services/authService";
import "./LoginPage.css";

const LoginPage: React.FC = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!username || !password) {
      setErrorMessage("Введите логин и пароль");
      return;
    }

    setIsSubmitting(true);
    setErrorMessage("");

    try {
      const { token } = await loginUser(username, password);
      localStorage.setItem("token", token);
      navigate("/profile");
    } catch (err: any) {
      if (err.status === 401) {
        setErrorMessage("Неверный логин или пароль");
      } else if (err.message) {
        setErrorMessage(err.message);
      } else {
        setErrorMessage("Произошла ошибка при входе. Попробуйте позже.");
      }
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="login-page">
      <h2>Авторизация</h2>

      <form onSubmit={handleSubmit}>
        <label>
          Логин:
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </label>

        <label>
          Пароль:
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </label>

        <button className="login-btn" type="submit" disabled={isSubmitting}>
          {isSubmitting ? "Вход..." : "Войти"}
        </button>

        <button
          type="button"
          className="back-btn"
          onClick={() => navigate("/")}
        >
          Назад
        </button>

        {errorMessage && <p className="error">{errorMessage}</p>}
      </form>
    </div>
  );
};

export default LoginPage;
