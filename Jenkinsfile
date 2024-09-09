pipeline {
    agent any

    environment {
        // Ensure this matches the SonarQube server name configured in Jenkins
        SONARQUBE_ENV = 'SonarServer'
        mvnHome = 'C:\\Ram Desktop\\Softwares\\apache-maven-3.9.3'  // Local Maven path
        registry = 'docker.io/monty123'  // Docker registry URL
        registryCredentials = 'docker-registry-credentials'  // Jenkins credentials ID for Docker registry
        sonarToken = credentials('jenkins')  // SonarQube token from Jenkins credentials
        kubeconfigId = 'kubeconfig-credentials-id'  // Jenkins credentials ID for Kubernetes config
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

      stage('Deploy to Kubernetes') {
    steps {
        echo 'Deploying to Kubernetes...'
        script {
            // Use withCredentials to access the kubeconfig file securely
            withCredentials([file(credentialsId: "${kubeconfigId}", variable: 'KUBECONFIG')]) {
                // Apply Kubernetes manifests such as secret, mysql deployment, and service
                bat 'kubectl apply -f mysql-secret.yaml'
                bat 'kubectl apply -f mysql-deployment.yaml'
                bat 'kubectl apply -f taskmanagement-deployment.yaml'
                
                // Update the image with specific build number in the Kubernetes deployment
                bat "kubectl set image deployment/taskmanagement taskmanagement=${registry}/taskmanagement:${env.BUILD_NUMBER} --record"
                
                // Monitor the rollout status
                bat 'kubectl rollout status deployment/taskmanagement'
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
