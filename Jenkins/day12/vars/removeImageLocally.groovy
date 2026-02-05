#!/usr/bin/env groovy

def call(String imageName) {
    stage('Remove Image Locally') {
        echo "Removing Docker image locally: ${imageName}"
        sh """
            docker rmi ${imageName} || true
            echo "Cleaning up dangling images..."
            docker image prune -f
        """
        echo "Local Docker image removed successfully"
    }
}
