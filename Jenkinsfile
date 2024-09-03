pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building the project...'
                bat 'mvn clean package'
            }
        }
        stage('Test') {
            steps {
                echo 'Running tests...'
                bat 'mvn test'
            }
        }
        stage('Package') {
            steps {
                echo 'Packaging the project...'
                bat 'mvn package'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying the application...'
                script {
                    if (isUnix()) {
                        sh 'echo Deploying on Linux/Unix...'
                        // Add your Linux/Unix-specific deployment steps here
                    } else {
                        bat 'echo Deploying on Windows...'
                        // Add your Windows-specific deployment steps here
                    }
                }
            }
        }
    }
    post {
        failure {
            echo 'Build failed!'
        }
    }
}
