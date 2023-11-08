pipeline {
    agent {
        label 'agent-ubuntu1'
    }
    triggers {
        githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'chehine', credentialsId: 'github_chehine', url: 'https://github.com/Devops5twin2/DevopsSpring'
            }
        }

        stage('Start MySQL') {
            steps {
                script {
                    // Start only the Nexus and SQL services
                    sh 'docker-compose -f docker-compose.yml up -d mysqldb'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                sh "./mvnw clean verify sonar:sonar -Dsonar.projectKey=devops-kaddem -Dsonar.projectName='devops-kaddem' -Dsonar.login=29dfe77363d51f8ecd848f50149766704abce9cb"
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

        // stage('Prometheus & Grafana Jenkins supervision') {
        //     steps {
        //         sh 'docker start prometheus'
        //         sh 'docker start grafana'
        //     }
        // }
    }

    post {
        success {
            echo 'Pipeline executed successfully'
        }
        failure {
            echo 'Pipeline failed.'
            sh 'docker-compose -f docker-compose.yml down'
        }
    }
}
