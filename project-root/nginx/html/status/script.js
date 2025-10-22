function updateTime() {
  document.getElementById('time').innerText = new Date().toLocaleTimeString();
}
updateTime();
setInterval(updateTime, 0);

function updateStatus() {
  fetch('/status_raw')
    .then(r => r.text())
    .then(t => {
      const box = document.getElementById('statusText');
      box.innerHTML = `<pre>${t}</pre>`;
      box.style.color = '#00ffea';
    })
    .catch(() => {
      const box = document.getElementById('statusText');
      box.innerText = 'Ошибка связи с сервером';
      box.style.color = '#ff0044';
    });
}

updateStatus();
setInterval(updateStatus, 1500);
