def call(String fullImageName) {
    echo "Removing local Docker image: ${fullImageName}"
    sh """
      docker rmi -f ${fullImageName} || true
    """
}

