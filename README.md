## Название
# Task Management System

## Описание

Task Management System — это RESTful API для управления задачами. Позволяет создавать, обновлять, удалять и просматривать задачи.

## Требования

- Java 17 или выше
- Maven 3.6 или выше
- PostgreSQL 14 или выше
- Git
- Docker

## Установка и запуск

1. **Склонируйте репозиторий:**

   ```bash
   git clone https://github.com/Pavcore/task-manager-system.git

2. Зайти через консоль в директорию, в которой лежит файл docker-compose
3. Для запуска использовать команду docker-compose up -d

## Использование

Для проверки работоспособности приложения в базе данных лежат два пользователя:
1. email: pavcore@example.com | password: 12345678 с ролью ADMIN
2. email: worker@gmail.com | password: 12345678 с ролью USER

## Тестирование

Запуск тестов: mvn test

## Документация API

Полная документация API доступна через Swagger UI по адресу http://localhost:8080/swagger-ui.html

## Авторы

Корчагин Павел - разработчик bloodcocker@yandex.kz
