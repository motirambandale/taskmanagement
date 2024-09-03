pipeline {
    agent any

    environment {
        SONARQUBE_ENV = 'SonarQube'
        MAVEN_TOOL = 'MAVEN_HOME'
        SONAR_SCANNER_TOOL = 'SonarScanner'
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
                    def mvnHome = tool name: "${MAVEN_TOOL}", type: 'Maven'
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
            // Publish SonarQube results to Jenkins
            script {
                def scannerHome = tool name: "${SONAR_SCANNER_TOOL}", type: 'hudson.plugins.sonar.SonarRunnerInstallation';
                withSonarQubeEnv("${SONARQUBE_ENV}") {
                    bat "${scannerHome}/bin/sonar-scanner"
                }
            }
        }
    }
}
