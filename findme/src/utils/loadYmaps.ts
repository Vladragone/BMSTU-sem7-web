export const loadYmapsScript = () => {
  if (document.getElementById("ymaps-script")) return;

  const apiKey = import.meta.env.VITE_YMAPS_KEY;

  if (!apiKey) {
    console.error("❌ Yandex Maps API key not found! Проверь .env файл.");
    return;
  }

  const script = document.createElement("script");
  script.id = "ymaps-script";
  script.src = `https://api-maps.yandex.ru/2.1/?apikey=${apiKey}&lang=ru_RU`;
  script.async = true;
  document.body.appendChild(script);
};
