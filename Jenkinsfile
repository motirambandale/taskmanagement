pipeline {
    agent any

    tools {
        maven 'Maven 3.9.3'  // Use the local Maven version
    }

    environment {
        // Ensure this matches the SonarQube server name configured in Jenkins
        SONARQUBE_ENV = 'SonarServer'
        mvnHome = 'C:\\Ram Desktop\\Softwares\\apache-maven-3.9.3'  // Update with your actual local Maven path (escaped backslashes)
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
                        bat "${mvnHome}\\bin\\mvn clean verify sonar:sonar -Dsonar.projectKey=taskmanagement_dev"
                    }
                }
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                bat "${mvnHome}\\bin\\mvn clean package"
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                bat "${mvnHome}\\bin\\mvn test"
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging the project...'
                bat "${mvnHome}\\bin\\mvn package"
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
