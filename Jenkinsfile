pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from the master branch of the GitHub repository
                git branch: 'master',
                    url: 'https://github.com/motirambandale/taskmanagement.git'
            }
        }

        stage('Build') {
            steps {
                // Building the project
                echo 'Building the project...'
                // Replace this with your actual build command, e.g., `mvn clean install` for a Maven project
                sh './mvnw clean install'
            }
        }

        stage('Test') {
            steps {
                // Running tests
                echo 'Running tests...'
                // Replace this with your actual test command
                sh './mvnw test'
            }
        }

        stage('Package') {
            steps {
                // Packaging the application
                echo 'Packaging the application...'
                // Replace this with your actual packaging command, e.g., `mvn package`
                sh './mvnw package'
            }
        }

        stage('Deploy') {
            steps {
                // Deploying the application
                echo 'Deploying the application...'
                // Add your deployment steps here, e.g., copying the artifact to a server
            }
        }
    }

    post {
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
