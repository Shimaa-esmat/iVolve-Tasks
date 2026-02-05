def call(String fullImageName, String dockerCredId) {
    echo "Pushing Docker image: ${fullImageName}"

    withCredentials([usernamePassword(credentialsId: dockerCredId, usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
        sh """
          echo "\$DOCKER_PASS" | docker login -u "\$DOCKER_USER" --password-stdin
          docker push ${fullImageName}
          docker logout
        """
    }
}

