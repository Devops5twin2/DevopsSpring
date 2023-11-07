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
        

        stage('Maven Build and Deploy to Nexus') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw clean deploy -DskipTests'
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
             sh 'sudo docker-compose -f docker-compose.yml down'
        }
     
    }
}
