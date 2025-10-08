import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { registerUser } from "../../services/registrationService";
import "./RegisterPage.css";

const RegisterPage: React.FC = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!username || !email || !password) {
      setErrorMessage("Заполните все поля");
      return;
    }

    setIsSubmitting(true);
    setErrorMessage("");

    try {
      await registerUser(username, email, password);
      alert("Успешная регистрация! Переход к авторизации...");
      navigate("/login");
    } catch (err: any) {
      setErrorMessage(err?.message || "Ошибка регистрации");
      console.error("Ошибка регистрации:", err);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="register-container">
      <h2>Регистрация</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="username">Имя пользователя</label>
          <input
            id="username"
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>

        <div>
          <label htmlFor="email">Email</label>
          <input
            id="email"
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>

        <div>
          <label htmlFor="password">Пароль</label>
          <input
            id="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>

        <button type="submit" disabled={isSubmitting}>
          {isSubmitting ? "Регистрация..." : "Зарегистрироваться"}
        </button>

        {errorMessage && <div className="error">{errorMessage}</div>}

        <button
          type="button"
          className="back-button"
          onClick={() => navigate("/")}
        >
          Назад
        </button>
      </form>
    </div>
  );
};

export default RegisterPage;
