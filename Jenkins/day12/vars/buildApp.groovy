#!/usr/bin/env groovy

def call() {
    stage('Build Application') {
        echo "Building the application with Maven..."
        sh '''
            echo "Packaging the application"
            mvn clean package -DskipTests
        '''
        echo "Application built successfully"
    }
}
