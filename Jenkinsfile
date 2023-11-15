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
                git branch: 'chehine', credentialsId: 'github_chehine', url: 'https://github.com/Devops5twin2/DevopsSpring.git'
            }
        }

        stage('Start MySQL') {
            steps {
                script {
                    sh 'docker-compose -f docker-compose.yml up -d mysqldb'
                }
            }
        }

        stage('Maven Clean') {
            steps {
                echo 'Running Maven Clean'
                sh 'mvn clean '
            }
        }

        stage('Maven Compile') {
            steps {
                echo 'Running Maven Compile'
                sh 'mvn compile'
            }
        }

        stage('JUNIT / MOCKITO') {
            steps {
                sh 'mvn test -Dspring.profiles.active=test'
            }
        }

        stage('JaCoCo Report') {
            steps {
                sh 'mvn jacoco:report'
            }
        }

        stage('Build package') {
            steps {
                sh 'mvn package -Dspring.profiles.active=test'
            }
        }

        

        stage('SonarQube Analysis') {
            steps {
                sh 'mvn verify -Dspring.profiles.active=test sonar:sonar -Dsonar.login=admin -Dsonar.password=chehine'
            }
        }

        stage('Deploy to Nexus') {
            steps {
                sh 'mvn -X clean deploy -Dspring.profiles.active=test'
            }
        }

        stage('Docker Image') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'DOCKERHUB_ACCESS_TOKEN', variable: 'dockeraccesstoken')]) {
                        sh "docker login -u chehinedh -p ${dockeraccesstoken}"
                    }
                    sh 'docker build -t chehinedhemaied-5twin2 .'
                }
            }
        }

//         stage('Push Docker Image') {
//     steps {
//         script {
//             withCredentials([string(credentialsId: 'DOCKERHUB_ACCESS_TOKEN', variable: 'dockeraccesstoken')]) {
//                 sh "docker login -u chehinedh -p ${dockeraccesstoken}"
//             }
//             sh 'docker info'
//             sh 'docker tag chehinedhemaied-5twin2 chehinedh/devops:v3'
//             sh 'docker push chehinedh/devops:v3'
//         }
//     }
// }


        stage('Build and Start Spring Application') {
            steps {
                script {
                    sh 'docker-compose -f docker-compose.yml up -d my-spring-app'
                }
            }
        }

        stage('Prometheus & Grafana Jenkins supervision') {
            steps {
                sh 'docker start prometheus'
                sh 'docker start grafana'
            }
        }
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