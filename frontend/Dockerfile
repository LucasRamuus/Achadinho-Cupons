# Usa versão leve do Nginx
ARG NGINX_VERSION=1.25.2
FROM nginx:${NGINX_VERSION}-alpine

# Copia configuração customizada
COPY nginx/nginx.conf /etc/nginx/nginx.conf

# Cria usuário não-root
RUN adduser -D -H -u 1000 -s /bin/bash www-data -G www-data

# Cria diretório raiz do site
RUN mkdir -p /var/www/html && \
    chown -R www-data:www-data /var/www/html && \
    chmod -R 755 /var/www/html

# Copia frontend (somente public + assets)
COPY public /var/www/html
COPY assets /var/www/html/assets

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
