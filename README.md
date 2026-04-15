# Koel App Automation Framework (Java/Selenium/TestNG)

## Project Description
This repository contains a robust automated testing framework designed for the **Koel Music Streaming App (v5.1.20)**. The project focuses on validating critical user journeys, data integrity via database checks, and backend API consistency.
Detected and documented around 30 functional bugs in the current Koel version via automated regression suite

## Tech Stack
* **Java 21** (JDK 21)
* **TestNG** (Parallel & Sequential execution)
* **Selenium WebDriver** (v4.41.0)
* **REST-Assured** (v5.4.0)
* **Gradle** (Build tool)
* **JDBC** (Database validation)
* **Design Pattern** Page Object Model (POM)

## Setup Instructions
1. Clone the repository.
2. Create a `local.properties` file in the root directory.
3. Use `local.properties.example` as a template and fill in your actual credentials.

## Requirements:
1. Ensure you have the latest Chrome browser installed (v145+).
2. Java 21 installed and configured

## Running tests
To run the full sprint suite (including parallel and sequential tests) use Sprint1.xml:
```bash
.\gradlew clean test "-DsuiteXmlFile=testNG.xml"
or
.\gradlew clean test "-DsuiteXmlFile=smoke.xml"
or
.\gradlew clean test "-DsuiteXmlFile=regression.xml"
```
