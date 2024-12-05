docker build - t api-springboot .
docker run -e DB_URL=jdbc:mysql://host.docker.internal:3306/mock_project -e DB_USERNAME=root -e DB_PASSWORD=Nhatnam00@ -p 81:8080 api-springboot