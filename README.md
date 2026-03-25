# A79-QA-Automation-Framework

## Project Description
This repository contains an automated testing framework for the **Koel App** (v5.1.20). 
The goal of this project is to cover user paths including Email update, Search field and Favorites management.

## Tech Stack
* **Java 21** (JDK 21)
* **Selenium WebDriver** (v4.41.0)
* **TestNG** (Testing Framework)
* **Gradle** (Build tool)
* **JDBC** (Database validation)

## Setup Instructions
1. Clone the repository.
2. Create a `local.properties` file in the root directory.
3. Use `local.properties.example` as a template and fill in your actual credentials.
4. Ensure you have the latest Chrome browser installed (v145+).

## Running tests
To run the full sprint suite (including parallel and sequential tests) use Sprint1.xml:
```bash
./gradlew clean test -DsuiteXmlFile=Sprint2.xml 
```
To run the validated regression suite (including network and DB checks):
```bash
./gradlew clean test -DsuiteXmlFile=regression.xml
```
