#!/usr/bin/env groovy

def call() {
    stage('Run Unit Tests') {
        echo "Running Unit Tests..."
        sh '''
            echo "Executing Maven unit tests"
            mvn clean test
        '''
        echo "Unit tests completed successfully"
    }
}
