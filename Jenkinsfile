pipeline {
    agent { label 'agent-ubuntu' }
    triggers {
       githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                  git branch: 'houssem', credentialsId: 'Github_Credentials', url: 'https://github.com/Devops5twin2/DevopsSpring.git'
            }
        }
         stage('testing') {
                    steps {
                         sh 'chmod +x mvnw'
                        sh './mvnw test -Dspring.profiles.active=test '
                    }
                }
        stage('SonarQube Analysis') {             
                   steps{
                  sh "./mvnw clean verify sonar:sonar -Dsonar.projectKey=devop_kaddem -Dsonar.projectName='devop_kaddem' -Dsonar.token='squ_7f9c72e9c3c1771604a0ec8ca453ed1fda56c636'"
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
              
                sh './mvnw clean deploy -Dspring.profiles.active=prod'

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
            // sh 'sudo docker-compose -f docker-compose.yml down'
        }
     
    }
}
