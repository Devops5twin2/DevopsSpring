pipeline {
    agent { label 'agent-ubuntu1' }
    triggers {
       githubPush()
    }
    
    stages {
        stage('Checkout') {
            steps {
                git branch: 'mouhib', credentialsId: 'github-access-token', url: 'https://github.com/Devops5twin2/DevopsSpring.git'
            }
        }

        stage('Maven Build and Deploy to Nexus') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean deploy -DskipTests'
            }
        }
        
        stage('Build Docker Image and Deploy with Docker Compose') {
            steps {
                script {
                    // Using Docker Compose to build and run the services
                    sh 'sudo docker-compose -f docker-compose.yml up --build -d'
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully'
        }
        always {
            // Clean up
            sh 'sudo docker-compose -f docker-compose.yml down'
        }
    }
}
