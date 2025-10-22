FROM dpage/pgadmin4:8.10

USER root

# UID и GID по умолчанию у pgAdmin = 5050
RUN chown -R 5050:5050 /pgadmin4 && chmod -R 775 /pgadmin4

# Создаём скрипт для добавления APPLICATION_ROOT
RUN printf '%s\n' \
"import os" \
"SCRIPT_NAME = os.environ.get('SCRIPT_NAME', '')" \
"if SCRIPT_NAME:" \
"    c = '/pgadmin4/config_local.py'" \
"    with open(c, 'a') as f:" \
"        f.write(f'APPLICATION_ROOT = \"{SCRIPT_NAME}\"\n')" \
> /entrypoint-extra.py

# Возвращаемся к пользователю с UID 5050
USER 5050

ENTRYPOINT ["/bin/sh", "-c", "python3 /entrypoint-extra.py && /entrypoint.sh"]
