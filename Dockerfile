FROM postgres
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD 123
ENV POSTGRES_DB file-manager
COPY file-manager-schema.sql /docker-entrypoint-initdb.d/