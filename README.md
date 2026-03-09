# A79-QA-Automation-Framework

## Project Description
This repository contains an automated testing framework for the **Koel App** (v6). The goal of this project is to cover critical user paths including Login, Registration, Playlist management, and Profile security validation.

## Tech Stack
* **Java 21** (JDK 21)
* **Selenium WebDriver** (v4.28.1)
* **TestNG** (Testing Framework)
* **Gradle** (Build tool)
* **JDBC** (Database validation)

## Setup Instructions
1. Clone the repository.
2. Create a `local.properties` file in the root directory.
3. Use `local.properties.example` as a template and fill in your actual credentials.
4. Ensure you have Chrome/ChromeDriver installed.

## Running Tests
To run the full sprint suite (including parallel and sequential tests) use Sprint1.xml:
```bash
./gradlew clean test -DsuiteXmlFile=Sprint1.xml