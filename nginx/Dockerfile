ARG NGINX_VERSION=1.25.2
FROM nginx:${NGINX_VERSION}-alpine

# Copia configs
COPY nginx.conf /etc/nginx/
COPY sites-available/app.conf /etc/nginx/sites-available/

# Remove default e garante diretórios
RUN rm -rf /etc/nginx/conf.d/default.conf && \
    mkdir -p /etc/nginx/conf.d && \
    mkdir -p /etc/nginx/sites-available && \
    adduser -D -H -u 1000 -s /bin/bash www-data -G www-data

# Define diretório de trabalho
WORKDIR /etc/nginx

# Inicia nginx
CMD ["nginx", "-g", "daemon off;"]