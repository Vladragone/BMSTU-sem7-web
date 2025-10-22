function updateTime() {
  document.getElementById('time').innerText = new Date().toLocaleTimeString();
}
updateTime();
setInterval(updateTime, 1000);

function updateStatus() {
  fetch('/status_raw')
    .then(r => r.text())
    .then(t => {
      const box = document.getElementById('statusText');
      box.innerHTML = `<pre>${t}</pre>`;
      box.style.color = '#00ffea';

      // PostgreSQL подпрыгивает
      document.querySelector('.pg-logo').animate(
        [{ transform: 'translateY(0)' }, { transform: 'translateY(-40px)' }, { transform: 'translateY(0)' }],
        { duration: 600, easing: 'ease-out' }
      );

      // NGINX крутится
      document.querySelector('.nginx-logo').animate(
        [{ transform: 'rotate(0deg)' }, { transform: 'rotate(360deg)' }],
        { duration: 1200, iterations: 1 }
      );
    })
    .catch(() => {
      const box = document.getElementById('statusText');
      box.innerText = '💥 Ошибка связи с сервером 💥';
      box.style.color = '#ff0044';
    });
}
updateStatus();
setInterval(updateStatus, 5000);
