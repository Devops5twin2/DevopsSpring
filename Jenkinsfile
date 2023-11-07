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

        stage('Start Nexus and SQL') {
            steps {
                script {
                    // Start only the Nexus and SQL services
                    sh 'sudo docker-compose -f docker-compose.yml up -d mysqldb'
                }
            }
        }
        
        // New stage for running tests
        stage('Run Tests') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw test -Dspring.profiles.active=test  '
            }
        }

        stage('Maven Build and Deploy to Nexus') {
            steps {
                sh './mvnw clean deploy -Dspring.profiles.active=build ' // sskipTests because we already run them
            }
        }

        stage('Build and Start Spring Application') {
            steps {
                script {
                    // Now build and start the Spring application container
                    sh 'sudo docker-compose -f docker-compose.yml up -d my-spring-app'
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully'
        }
        failure {
            echo 'Pipeline failed.'
        }
     
    }
}
