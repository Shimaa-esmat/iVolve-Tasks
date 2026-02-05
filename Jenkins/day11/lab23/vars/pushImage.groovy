#!/usr/bin/env groovy

def call(String imageName, String credentialsId = 'dockerhub-credentials') {
    stage('Push Docker Image') {
        echo "Pushing Docker image to registry: ${imageName}"
        
        withCredentials([usernamePassword(
            credentialsId: credentialsId,
            usernameVariable: 'DOCKER_USER',
            passwordVariable: 'DOCKER_PASS'
        )]) {
            sh """
                echo "\${DOCKER_PASS}" | docker login -u "\${DOCKER_USER}" --password-stdin
                docker push ${imageName}
                docker logout
            """
        }
        
        echo "Docker image pushed successfully: ${imageName}"
    }
}
