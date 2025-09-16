FROM nginx:alpine

# limpa html padrão
RUN rm -rf /usr/share/nginx/html/*

# copia arquivos do frontend
COPY ./public/ /usr/share/nginx/html/
COPY ./assets/ /usr/share/nginx/html/assets/

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
