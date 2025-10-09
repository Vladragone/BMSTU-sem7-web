import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getRating } from "../../services/ratingService";
import type { RatingResponse } from "../../services/ratingService";
import "./RatingPage.css";

const RatingPage: React.FC = () => {
  const [ratingData, setRatingData] = useState<RatingResponse | null>(null);
  const [sortBy, setSortBy] = useState<"points" | "games">("points");
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();

  const fetchRating = async () => {
    try {
      setLoading(true);
      const data = await getRating(sortBy);
      setRatingData(data);
    } catch (err) {
      console.error("Ошибка при загрузке рейтинга", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchRating();
  }, [sortBy]);

  const toggleSort = () => {
    setSortBy(sortBy === "points" ? "games" : "points");
  };

  const startGame = () => navigate("/game-settings");
  const goToProfile = () => navigate("/profile");

  const logout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  const changeImg = () => {
    const body = document.body;
    const current = body.style.backgroundImage;
    if (current.includes("world.png")) {
      body.style.backgroundImage = "url('/assets/saxur.png')";
    } else {
      body.style.backgroundImage = "url('/assets/world.png')";
    }
  };

  if (loading) return <div className="loading">Загрузка рейтинга...</div>;

  return (
    <div className="rating-page">
      {ratingData ? (
        <>
          <h2>
            Топ 3 игроков (сортировка:{" "}
            {ratingData.sortBy === "points" ? "по очкам" : "по числу игр"})
          </h2>

          <table>
            <thead>
              <tr>
                <th>Позиция</th>
                <th>Пользователь</th>
                <th>Очки</th>
                <th>Игры</th>
                <th>Дата регистрации</th>
              </tr>
            </thead>
            <tbody>
              {ratingData.top.map((profile, i) => (
                <tr key={profile.user?.username || i}>
                  <td>{i + 1}</td>
                  <td>{profile.user?.username}</td>
                  <td>{profile.score}</td>
                  <td>{profile.gameNum}</td>
                  <td>{new Date(profile.regDate).toLocaleDateString()}</td>
                </tr>
              ))}
            </tbody>
          </table>

          <p className="your-rank">Ваш ранг: {ratingData.yourRank}</p>

          <div className="sorting">
            <label>Сортировать:</label>
            <div className="switch-container" onClick={toggleSort}>
              <div className={`switch ${sortBy === "points" ? "active" : ""}`}>
                <div className="switch-button"></div>
              </div>
            </div>
            <span className="sort-label">
              {sortBy === "points" ? "По очкам" : "По играм"}
            </span>
          </div>

          <div className="profile-buttons">
            <button className="start-game-btn" onClick={startGame}>
              Начать игру
            </button>
            <button className="rating-btn" onClick={goToProfile}>
              Профиль
            </button>
            <button className="logout-btn" onClick={logout}>
              Выйти
            </button>
          </div>
        </>
      ) : (
        <p>Нет данных для отображения.</p>
      )}

      <button className="help-button" onClick={changeImg}>
        ?
      </button>
    </div>
  );
};

export default RatingPage;
