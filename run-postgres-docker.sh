docker rm -f postgres
docker run --name postgres -e POSTGRES_PASSWORD=abc123 -e POSTGRES_DB=artikel -p 5432:5432 postgres:14.1-bullseye