pipeline {
    agent any

    environment {
        // Ensure this matches the SonarQube server name configured in Jenkins
        SONARQUBE_ENV = 'SonarServer'
        mvnHome = 'C:\\Ram Desktop\\Softwares\\apache-maven-3.9.3'  // Update with your actual local Maven path (escaped backslashes)
        registry = 'docker.io/monty123'  // Replace with your Docker registry URL
        registryCredentials = 'docker-registry-credentials'  // Jenkins credentials ID for Docker registry
        sonarToken = credentials('jenkins')  // Fetching token from Jenkins credentials
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
                        bat "\"${mvnHome}\\bin\\mvn\" clean verify sonar:sonar -Dsonar.projectKey=taskmanagement_dev -Dsonar.login=${sonarToken}"
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                echo 'Waiting for SonarQube Quality Gate...'
                script {
                    timeout(time: 1, unit: 'MINUTES') { // Adjust the timeout as needed
                        def qualityGate = waitForQualityGate()
                        if (qualityGate.status != 'OK') {
                            error "Pipeline aborted due to quality gate failure: ${qualityGate.status}"
                        }
                    }
                }
            }
        }

        stage('Build & Test') {
            steps {
                echo 'Building and testing the project...'
                bat "\"${mvnHome}\\bin\\mvn\" clean package"
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    dockerImage = docker.build("${registry}/taskmanagement:${env.BUILD_NUMBER}")
                }
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    docker.withRegistry("https://${registry}", "${registryCredentials}") {
                        dockerImage.push()
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'The pipeline completed successfully and passed the quality gate.'
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed!'
        }
        always {
            echo 'Always runs after the pipeline stages'
            junit '**/target/surefire-reports/*.xml' // Archive JUnit reports
        }
    }
}
