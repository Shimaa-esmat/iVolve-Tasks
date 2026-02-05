def call() {
    echo "Building Application..."
    sh "mvn -B clean package -DskipTests"
}

