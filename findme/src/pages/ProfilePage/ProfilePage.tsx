import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getProfile } from "../../services/profileService";
import type { Profile } from "../../services/profileService";
import "./ProfilePage.css";

const ProfilePage: React.FC = () => {
  const [profile, setProfile] = useState<Profile | null>(null);
  const [error, setError] = useState<string | null>(null);

  const navigate = useNavigate();

  useEffect(() => {
    const loadProfile = async () => {
      try {
        const data = await getProfile();
        setProfile(data);
      } catch (err) {
        console.error(err);
        setError("Ошибка загрузки профиля");
      }
    };
    loadProfile();
  }, []);

  const startGame = () => navigate("/game-settings");
  const goToRating = () => navigate("/rating");
  const goToFaq = () => navigate("/faq");

  const logout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  return (
    <div className="profile-page">
      {profile ? (
        <div className="profile-info">
          <ul>
            <li>
              <strong>Дата регистрации:</strong>{" "}
              {new Date(profile.regDate).toLocaleString()}
            </li>
            <li>
              <strong>Количество игр:</strong> {profile.gameNum}
            </li>
            <li>
              <strong>Счёт:</strong> {profile.score}
            </li>
          </ul>
        </div>
      ) : (
        !error && <p>Загрузка...</p>
      )}

      {error && <p className="error-message">{error}</p>}

      <div className="profile-buttons">
        <button className="start-game-btn" onClick={startGame}>
          Начать игру
        </button>
        <button className="rating-btn" onClick={goToRating}>
          Рейтинг
        </button>
        <button className="rating-btn" onClick={goToFaq}>
          О проекте
        </button>
        <button className="logout-btn" onClick={logout}>
          Выйти
        </button>
      </div>
    </div>
  );
};

export default ProfilePage;
