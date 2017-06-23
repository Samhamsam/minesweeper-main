FROM openjdk:8
MAINTAINER MINESWEEPER "@htwg"
COPY . /docker/minesweeper
WORKDIR /docker/minesweeper
CMD ["java", "-jar","build/libs/minesweeper-main-all.jar"]
