FROM openjdk:8-jdk

RUN apt-get update && apt-get install -y \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libgl1-mesa-glx \
    libgl1-mesa-dri \
    && rm -rf /var/lib/apt/lists/*


RUN apt-get update && apt-get install -y maven

WORKDIR /app

COPY . /app/

RUN mvn clean package

CMD ["java", "-jar", "target/virtual-heritage-1.0-SNAPSHOT.jar"]
