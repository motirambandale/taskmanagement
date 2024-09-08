pipeline {
    agent any

    environment {
        SONARQUBE_ENV = 'SonarServer'
        mvnHome = 'C:\\Ram Desktop\\Softwares\\apache-maven-3.9.3'
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

        // ... other stages remain unchanged

    }

    post {
        success {
            echo 'Pipeline completed successfully and passed the quality gate.'
        }
        failure {
            echo 'Build failed!'
        }
        always {
            echo 'Always runs after the pipeline stages'
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
