def call(Map config = [:]) {
    def imageName = config.imageName ?: error("buildImage: imageName is required")
    def imageTag  = config.imageTag ?: "latest"
    def dockerRegistry = config.dockerRegistry ?: ""

    def fullName = dockerRegistry ? "${dockerRegistry}/${imageName}:${imageTag}" : "${imageName}:${imageTag}"

    echo "Building Docker image: ${fullName}"
    sh "docker build -t ${fullName} ."
}

