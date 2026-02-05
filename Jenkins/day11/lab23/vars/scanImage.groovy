#!/usr/bin/env groovy

def call(String imageName) {
    stage('Scan Docker Image') {
        echo "Scanning Docker image for vulnerabilities: ${imageName}"
        sh """
            # Install Trivy if not present
            if ! command -v trivy &> /dev/null; then
                echo "Installing Trivy..."
                wget -qO - https://aquasecurity.github.io/trivy-repo/deb/public.key | sudo apt-key add -
                echo "deb https://aquasecurity.github.io/trivy-repo/deb \$(lsb_release -sc) main" | sudo tee -a /etc/apt/sources.list.d/trivy.list
                sudo apt-get update
                sudo apt-get install trivy -y
            fi
            
            # Scan the image
            trivy image --severity HIGH,CRITICAL --format table ${imageName}
            
            # Optional: fail if critical vulnerabilities found
            # trivy image --severity CRITICAL --exit-code 1 ${imageName}
        """
        echo "Image scan completed"
    }
}
