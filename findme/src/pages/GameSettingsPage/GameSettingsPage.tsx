import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { jwtDecode } from "jwt-decode";
import { getLocationGroups } from "../../services/locationGroupsService";
import { createGameSession } from "../../services/gameSessionsService";
import "./GameSettingsPage.css";

interface LocationGroup {
  id: number;
  name: string;
}

interface DecodedToken {
  user_id: number;
  role: string;
  sub: string;
  exp: number;
}

const GameSettingsPage: React.FC = () => {
  const [locationGroups, setLocationGroups] = useState<LocationGroup[]>([]);
  const [selectedGroup, setSelectedGroup] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(true);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchGroups = async () => {
      try {
        const groups = await getLocationGroups();
        setLocationGroups(groups);
      } catch (err) {
        console.error("Ошибка при загрузке групп локаций:", err);
        alert("Не удалось загрузить список локаций");
      } finally {
        setLoading(false);
      }
    };

    fetchGroups();
  }, []);

  const startGame = async () => {
    if (!selectedGroup) {
      alert("Выберите локацию для начала игры");
      return;
    }

    const token = localStorage.getItem("token");
    if (!token) {
      alert("Вы не авторизованы");
      navigate("/login");
      return;
    }

    let userId: number;
    try {
      const decoded: DecodedToken = jwtDecode(token);
      userId = decoded.user_id;
    } catch (err) {
      console.error("Ошибка при декодировании токена:", err);
      alert("Ошибка авторизации. Войдите заново.");
      navigate("/login");
      return;
    }

    const group = locationGroups.find((g) => g.name === selectedGroup);
    if (!group) {
      alert("Ошибка: выбранная группа не найдена");
      return;
    }

    try {
      const session = await createGameSession({
        userId,
        locationGroupId: group.id,
      });
      navigate(`/game?sessionId=${session.id}`);
    } catch (err: any) {
      console.error("Ошибка при создании игровой сессии:", err);
      if (err.response?.status === 401) {
        alert("Авторизация истекла. Войдите заново.");
        navigate("/login");
      } else {
        alert("Не удалось создать игру. Попробуйте позже.");
      }
    }
  };

  const goBack = () => {
    if (window.history.length > 1) navigate(-1);
    else navigate("/profile");
  };

  return (
    <div className="overlay">
      <div className="location-selection-box">
        <div className="settings-box">
          <h1 className="title">Выберите игровую локацию</h1>

          {loading ? (
            <p>Загрузка локаций...</p>
          ) : (
            <select
              className="select-box"
              value={selectedGroup}
              onChange={(e) => setSelectedGroup(e.target.value)}
            >
              <option value="">-- Выберите группу локаций --</option>
              {locationGroups.map((group) => (
                <option key={group.id} value={group.name}>
                  {group.name}
                </option>
              ))}
            </select>
          )}

          <div className="button-group">
            <button className="start-button" onClick={startGame}>
              Начать игру
            </button>
            <button className="back-button" onClick={goBack}>
              Назад
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default GameSettingsPage;
