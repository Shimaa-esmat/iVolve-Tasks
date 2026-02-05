#!/usr/bin/env groovy

def call(Map config = [:]) {
    stage('Build Docker Image') {
        def imageName = config.imageName ?: 'myapp'
        def imageTag = config.imageTag ?: 'latest'
        def dockerRegistry = config.dockerRegistry ?: ''
        
        def fullImageName = dockerRegistry ? "${dockerRegistry}/${imageName}:${imageTag}" : "${imageName}:${imageTag}"
        
        echo "Building Docker image: ${fullImageName}"
        sh """
            docker build -t ${fullImageName} .
        """
        echo "Docker image built successfully: ${fullImageName}"
        
        return fullImageName
    }
}
