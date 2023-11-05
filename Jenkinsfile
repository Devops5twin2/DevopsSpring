pipeline {
    agent { label 'agent-ubuntu1' }
    triggers {
       githubPush()
    }
    
   
    stages {
        stage('Build') {
            steps {
                git branch: 'mouhib', credentialsId: 'github-access-token', url: 'https://github.com/Devops5twin2/DevopsSpring.git'
                sh 'chmod +x mvnw'
                // Check if the Docker image already existts
                script {
                    def imageExists = sh(script: 'docker images -q my-spring-app', returnStdout: true).trim()
                       
                    if (imageExists) {
                        echo ' Docker image already exists. Removing the existing image...'
                        sh 'sudo docker stop springboot-mysql-container'
                        sh 'sudo docker rm springboot-mysql-container'
                        sh 'sudo docker rmi my-spring-app'
                    }
                    
                    sh './mvnw clean deploy -DskipTests'
                    sh 'sudo docker build -t my-spring-app .'
                }
            }
        }

        stage('Run Container') {
            steps {
                sh 'sudo docker start mysqldb'
                sh 'sudo docker run --network springboot-mysql-net --name springboot-mysql-container -p 9090:9090 my-spring-app'
            }
        }
    }

    post {
        success {
            // Print a success message
            echo 'Pipeline executed successfully'
        }
    }
}
