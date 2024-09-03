pipeline {
    agent any

    environment {
        // Ensure this matches the SonarQube server name configured in Jenkins
        SONARQUBE_ENV = 'SonarQube'
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo 'Checking out code from GitHub...'
                checkout scm
            }
        }
        stage('SonarQube Analysis') {
            steps {
                echo 'Running SonarQube Analysis...'
                script {
                    withSonarQubeEnv("${SONARQUBE_ENV}") {
                        bat "${mvnHome}/bin/mvn clean verify sonar:sonar -Dsonar.projectKey=taskmanagement_dev"
                    }
                }
            }
        }
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
                    bat 'echo Deploying on Windows...'
                    // Add your Windows-specific deployment steps here
                }
            }
        }
        stage('SonarQube Quality Gate') {
            steps {
                echo 'Checking SonarQube Quality Gate...'
                script {
                    waitForQualityGate abortPipeline: true
                }
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
        always {
            echo 'Always runs after the pipeline stages'
        }
    }
}
