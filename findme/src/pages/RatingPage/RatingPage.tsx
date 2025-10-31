import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getRating } from "../../services/ratingService";
import type { RatingResponse } from "../../services/ratingService";
import "./RatingPage.css";

const RatingPage: React.FC = () => {
  const [ratingData, setRatingData] = useState<RatingResponse | null>(null);
  const [sortBy, setSortBy] = useState<"score" | "games">("score");
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();

  const fetchRating = async () => {
    try {
      setLoading(true);
      const data = await getRating(sortBy);
      setRatingData(data);
    } catch (err) {
      console.error("Ошибка при загрузке рейтинга", err);
      setRatingData({ topUsers: [], currentUserRank: 0, sortBy });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchRating();
  }, [sortBy]);

  const toggleSort = () => {
    setSortBy(sortBy === "score" ? "games" : "score");
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
      {ratingData && Array.isArray(ratingData.topUsers) ? (
        <>
          <h2>
            Топ 3 игроков (сортировка:{" "}
            {ratingData.sortBy === "score" ? "по очкам" : "по числу игр"})
          </h2>

          <table>
            <thead>
              <tr>
                <th>Позиция</th>
                <th>Пользователь</th>
                <th>Очки</th>
                <th>Игры</th>
              </tr>
            </thead>
            <tbody>
              {ratingData.topUsers.length > 0 ? (
                ratingData.topUsers.map((user, i) => (
                  <tr key={user.username || i}>
                    <td>{user.rank || i + 1}</td>
                    <td>{user.username}</td>
                    <td>{user.score}</td>
                    <td>{user.gameNum}</td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan={4}>Нет данных для отображения</td>
                </tr>
              )}
            </tbody>
          </table>

          <p className="your-rank">Ваш ранг: {ratingData.currentUserRank}</p>

          <div className="sorting">
            <label>Сортировать:</label>
            <div className="switch-container" onClick={toggleSort}>
              <div className={`switch ${sortBy === "score" ? "active" : ""}`}>
                <div className="switch-button"></div>
              </div>
            </div>
            <span className="sort-label">
              {sortBy === "score" ? "По очкам" : "По играм"}
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
