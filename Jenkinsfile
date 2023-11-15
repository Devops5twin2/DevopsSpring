pipeline {
    agent {
        label 'agent1'
    }

    triggers {
        githubPush()
    }

    environment {
        registry = "malek05/dockerhub"
        registryCredential = '4c82311e-8f96-4423-9548-88af910555c9'
        dockerImage = ''
    }

    stages {


        stage('Maven Clean & Compile') {
            steps {
                sh 'mvn clean compile'
            }
        }



        stage('Run Tests') {
            steps {
                sh 'chmod +x mvnw'
                sh './mvnw test -Dspring.profiles.active=test'
            }
        }
       stage("MVN SONARQUBE") {
        steps {
            script {
             withSonarQubeEnv('sonarServer') {
                sh 'mvn sonar:sonar -Dsonar.login=squ_45dcb005cdae70ecf5280f05f51c25cb7b03c4c4'
            }
        }
    }
    }

        stage('Nexus Deploy') {
            steps {
                sh 'mvn deploy'
            }
        }



        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("${registry}:${BUILD_NUMBER}")
                }
            }
        }

        stage('Deploy Docker Image') {
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
                        dockerImage.push()
                    }
                }
            }
        }

        stage('Build Image') {
            steps {
                sh 'docker build -t my-spring-app .'
            }
        }

        stage('Docker Compose') {
            steps {

                    sh 'docker compose down --remove-orphans'
                    sh 'docker compose up -d'

            }

         }
        stage('Send Email Notification') {
            steps {
                emailext(
                     subject: "Build Status: ${currentBuild.currentResult}",
                     body: "The build status is: ${currentBuild.currentResult}",
                     to: 'malek.zlitni@esprit.tn',
                     attachLog: true,
                )
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
