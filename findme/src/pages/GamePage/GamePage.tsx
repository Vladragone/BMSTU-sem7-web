import React, { useEffect, useRef, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { getGameSessionById } from "../../services/gameSessionsService";
import { getLocationsByGroup } from "../../services/gameLocationsService";
import { createGameRound } from "../../services/gameRoundsService";
import "./GamePage.css";

declare const ymaps: any;

interface GameLocation {
  id: number;
  lat: number;
  lng: number;
}

const GamePage: React.FC = () => {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const [locations, setLocations] = useState<GameLocation[]>([]);
  const [currentRound] = useState(0);
  const [userGuess, setUserGuess] = useState<[number, number] | null>(null);
  const [loading, setLoading] = useState(true);
  const sessionId = Number(searchParams.get("sessionId"));

  const panoramaRef = useRef<HTMLDivElement | null>(null);
  const mapRef = useRef<HTMLDivElement | null>(null);
  const mapInstanceRef = useRef<any>(null);
  const placemarkRef = useRef<any>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const session = await getGameSessionById(sessionId);
        const groupId = session.locationGroupId;
        const locationsData = await getLocationsByGroup(groupId);
        setLocations(locationsData);
      } catch (err) {
        console.error("Ошибка загрузки данных игры:", err);
        alert("Не удалось загрузить данные игры");
        navigate("/profile");
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [sessionId, navigate]);

  useEffect(() => {
    const initMap = () => {
      if (!mapRef.current || mapInstanceRef.current) return;
      const container = mapRef.current;
      container.innerHTML = "";
      const map = new ymaps.Map(container, {
        center: [55.75, 37.62],
        zoom: 3,
        controls: ["zoomControl"],
      });
      map.events.add("click", (e: any) => {
        const coords = e.get("coords");
        setUserGuess(coords);
        if (placemarkRef.current) {
          placemarkRef.current.geometry.setCoordinates(coords);
        } else {
          const mark = new ymaps.Placemark(coords, {}, { preset: "islands#redIcon" });
          map.geoObjects.add(mark);
          placemarkRef.current = mark;
        }
      });
      mapInstanceRef.current = map;
      setTimeout(() => {
        try {
          map.container.fitToViewport();
        } catch (e) {
          /* noop */
        }
      }, 300);
    };

    const waitForYMaps = () => {
      if (typeof ymaps === "undefined") {
        setTimeout(waitForYMaps, 100);
      } else {
        ymaps.ready(initMap);
      }
    };

    if (!loading) waitForYMaps();
    return () => {
      if (mapInstanceRef.current && mapRef.current) {
        try {
          mapInstanceRef.current.destroy();
        } catch (e) {
          mapRef.current.innerHTML = "";
        }
        mapInstanceRef.current = null;
        placemarkRef.current = null;
      }
    };
  }, [loading]);

  useEffect(() => {
    if (!locations.length) return;
    const loc = locations[currentRound];
    if (!loc || !panoramaRef.current) return;
    panoramaRef.current.innerHTML = "";

    const initPanorama = () => {
      ymaps.panorama.locate([loc.lat, loc.lng]).done(
        (panoramas: any[]) => {
          if (panoramas.length > 0) {
            try {
              new ymaps.panorama.Player(panoramaRef.current, panoramas[0], {
                direction: [0, 0],
                span: [90, 45],
              });
            } catch (e) {
              panoramaRef.current!.innerHTML =
                "<p style='color:white;text-align:center;margin-top:40px;'>Ошибка отображения панорамы</p>";
            }
          } else {
            panoramaRef.current!.innerHTML =
              "<p style='color:white;text-align:center;margin-top:40px;'>Панорама недоступна для этой точки</p>";
          }
        },
        (err: any) => {
          console.error("Ошибка загрузки панорамы:", err);
          panoramaRef.current!.innerHTML =
            "<p style='color:white;text-align:center;margin-top:40px;'>Ошибка загрузки панорамы</p>";
        }
      );
    };

    const waitForYMaps = () => {
      if (typeof ymaps === "undefined") {
        setTimeout(waitForYMaps, 100);
      } else {
        ymaps.ready(initPanorama);
      }
    };

    waitForYMaps();
  }, [locations, currentRound]);

  const calculateScore = (trueLat: number, trueLng: number, guessLat: number, guessLng: number) => {
    const R = 6371;
    const dLat = ((guessLat - trueLat) * Math.PI) / 180;
    const dLng = ((guessLng - trueLng) * Math.PI) / 180;
    const a =
      Math.sin(dLat / 2) ** 2 +
      Math.cos((trueLat * Math.PI) / 180) *
        Math.cos((guessLat * Math.PI) / 180) *
        Math.sin(dLng / 2) ** 2;
    const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    const distance = R * c;
    return Math.max(0, Math.round(5000 - distance * 10));
  };

  const handleFind = async () => {
    if (!userGuess) {
      alert("Выберите место на карте!");
      return;
    }
    const loc = locations[currentRound];
    if (!loc) return;
    const score = calculateScore(loc.lat, loc.lng, userGuess[0], userGuess[1]);
    try {
      await createGameRound({
        sessionId,
        locationId: loc.id,
        guessLat: userGuess[0],
        guessLng: userGuess[1],
        score,
        roundNumber: currentRound + 1,
      });
      navigate(`/round-result?sessionId=${sessionId}&round=${currentRound + 1}`);
    } catch (err) {
      console.error("Ошибка при сохранении раунда:", err);
      alert("Не удалось сохранить результат.");
    }
  };

  if (loading) return <p className="loading">Загрузка...</p>;

  return (
    <div className="game-container">
      <div ref={panoramaRef} id="panorama" className="panorama-container"></div>
      <div className="map-container">
        <div ref={mapRef} id="guess-map" className="guess-map"></div>
        <button className="find-button" onClick={handleFind}>
          FindMe!
        </button>
      </div>
    </div>
  );
};

export default GamePage;
