# Stage 1: Build stage
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app

# Копіюємо pom.xml та завантажуємо залежності (кешуємо цей шар)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Копіюємо вихідний код та збираємо артефакт без запуску тестів
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage
FROM eclipse-temurin:21-alpine
WORKDIR /app

# Створюємо не-root користувача для безпеки (стандарт у комерційній розробці)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Копіюємо зібраний jar-файл з попереднього етапу
COPY --from=builder /app/target/*.jar app.jar

# Налаштування параметрів JVM для оптимізації пам'яті в контейнері
ENTRYPOINT ["java", "-XX:+UseG1GC", "-jar", "app.jar"]