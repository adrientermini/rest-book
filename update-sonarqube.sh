#!/bin/bash

# Fonction pour capturer les erreurs et afficher un message
trap 'echo "❌ Error occurred. Press any key to exit..."; read -n 1 -s' ERR

# Configuration
SONAR_HOST="http://localhost:9090"
SONAR_USER="admin"
SONAR_PASSWORD="admin"
TOKEN_NAME="auto-generated-token"
TOKEN_FILE=".sonar_token"  # File to store the token
COVERAGE_JACOCO_XML_REPORT_PATHS="target/jacoco-report/jacoco.xml"

# Run SonarQube analysis with Maven
echo "🧪 Running Tests with Maven..."
./mvnw clean test

# Check if SonarQube is already running
if [ ! "$(docker ps -q -f name=sonarqube)" ]; then
    echo "🚀 SonarQube is not running. Starting it..."
    docker-compose up -d sonarqube
else
    echo "✅ SonarQube is already running."
fi

# Check if SonarQube is fully ready by querying the system status
echo "⏳ Waiting for SonarQube API to be fully available..."
until curl -s "$SONAR_HOST/api/system/status" | grep -q '"status":"UP"'; do
    sleep 3
done
echo "✅ SonarQube is ready."

# Check if a token already exists
if [ -f "$TOKEN_FILE" ]; then
    SONAR_TOKEN=$(cat "$TOKEN_FILE")
    echo "🔑 Using existing SonarQube token."
else
    echo "🔑 Generating a new SonarQube API token..."

    # Revoke the previous token (if exists) to prevent duplicates
    curl -u $SONAR_USER:$SONAR_PASSWORD -X POST "$SONAR_HOST/api/user_tokens/revoke" \
         -d "name=$TOKEN_NAME" >/dev/null 2>&1

    # Try generating the token, retrying if necessary
    ATTEMPT=0
    MAX_ATTEMPTS=5
    while [ $ATTEMPT -lt $MAX_ATTEMPTS ]; do
        # Generate a new token and capture the full response
        RESPONSE=$(curl -u $SONAR_USER:$SONAR_PASSWORD -X POST "$SONAR_HOST/api/user_tokens/generate" \
                      -d "name=$TOKEN_NAME")

        # Display the full response for debugging
        echo "Full API response: $RESPONSE"

        # Extract the token from the response using grep and sed
        SONAR_TOKEN=$(echo "$RESPONSE" | grep -o '"token":"[^"]*' | sed 's/"token":"//')

        if [[ -n "$SONAR_TOKEN" && "$SONAR_TOKEN" != "null" ]]; then
            echo "✅ Token generated and stored."
            echo "$SONAR_TOKEN" > "$TOKEN_FILE"
            break
        else
            ATTEMPT=$((ATTEMPT+1))
            echo "❌ Failed to generate token. Retrying... ($ATTEMPT/$MAX_ATTEMPTS)"
            sleep 5
        fi
    done

    if [[ -z "$SONAR_TOKEN" || "$SONAR_TOKEN" == "null" ]]; then
        echo "❌ Failed to generate token after $MAX_ATTEMPTS attempts."
        exit 1
    fi
fi

# Run SonarQube analysis with Maven
echo "🚀 Running SonarQube analysis with Maven..."
./mvnw sonar:sonar -Dsonar.host.url=$SONAR_HOST -Dsonar.login=$SONAR_TOKEN -Dsonar.coverage.jacoco.xmlReportPaths=$COVERAGE_JACOCO_XML_REPORT_PATHS

echo "🎯 SonarQube analysis completed successfully!"

# Wait for user input to close the script
echo "Press any key to exit..."
read -n 1 -s # Wait for a key press
echo "Goodbye!"
