FROM openjdk:8
COPY /build/libs/ /docker/minesweeper
WORKDIR /docker/minesweeper
EXPOSE 8080
CMD ["java", "-jar","/docker/minesweeper/minesweeper-main-all.jar"]
