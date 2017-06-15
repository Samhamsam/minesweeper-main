FROM openjdk:8
<<<<<<< HEAD
MAINTAINER MINESWEEPER "@htwg"
COPY . /docker/minesweeper
WORKDIR /docker/minesweeper
CMD ["java", "-jar","build/libs/minesweeper-main-all.jar]
=======
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN javac Main.java
CMD ["java", "Main"]
>>>>>>> 0aa4ffd0a43187bb5222d4362a0b6910750f21f4
