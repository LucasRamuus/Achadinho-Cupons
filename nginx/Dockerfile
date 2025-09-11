FROM nginx:1.15.0

RUN rm /etc/nginx/conf.d/default.conf

COPY nginx/nginx.conf /etc/nginx/conf.d/

# Copia apenas os arquivos necessários
COPY public /var/www/html
COPY assets /var/www/html/assets

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
