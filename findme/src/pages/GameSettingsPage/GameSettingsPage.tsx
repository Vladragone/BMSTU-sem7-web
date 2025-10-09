import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { getLocationNames } from "../../services/locationService";
import "./GameSettingsPage.css";

const GameSettingsPage: React.FC = () => {
  const [locations, setLocations] = useState<string[]>([]);
  const [selectedLocation, setSelectedLocation] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(true);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchLocations = async () => {
      try {
        const data = await getLocationNames();
        setLocations(data);
      } catch (err) {
        console.error("Ошибка загрузки локаций:", err);
      } finally {
        setLoading(false);
      }
    };

    fetchLocations();
  }, []);

  const startGame = () => {
    if (!selectedLocation) {
      alert("Выберите локацию для начала игры");
      return;
    }

    navigate(`/game?name=${encodeURIComponent(selectedLocation)}`);
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
              value={selectedLocation}
              onChange={(e) => setSelectedLocation(e.target.value)}
            >
              <option value="">-- Выберите локацию --</option>
              {locations.map((loc) => (
                <option key={loc} value={loc}>
                  {loc}
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
