def call(String fullImageName) {
    echo "Scanning image with Trivy: ${fullImageName}"

    sh """
      if ! command -v trivy >/dev/null 2>&1; then
        echo "Trivy not installed! Please install it on agent."
        exit 1
      fi

      trivy image --exit-code 0 --severity LOW,MEDIUM ${fullImageName}
      trivy image --exit-code 1 --severity HIGH,CRITICAL ${fullImageName}
    """
}

