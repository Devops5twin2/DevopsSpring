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

         stage('Maven Clean') {
            steps {
                echo 'Running Maven Clean'
                sh 'mvn clean'
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
                sh 'mvn test'
            }
        }
        stage('JaCoCo Report') {
            steps {
               
                // Generate JaCoCo coverage report
                sh 'mvn jacoco:report'
            }
        }
         stage('Publish JaCoCo coverage report') {
            steps {
                // Publish the JaCoCo coverage report
                step([$class: 'JacocoPublisher', 
                      execPattern: '**/target/jacoco.exec', 
                      classPattern: '**/classes', 
                      sourcePattern: '**/src', 
                      exclusionPattern: '/target/**/,**/*Test,**/*_javassist/**'
                ])
            }
        }
            stage('Build package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Maven Install') {
            steps {
                sh 'mvn install'
            }
        }

               
        stage('SonarQube Scanner') {
            steps {
                script {
                withSonarQubeEnv('sonar') 
                    {
                    sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=sonar' 
              }

            }
                
            }
            }
        stage('Deploy to Nexus') {
            steps {
                sh 'mvn clean deploy -Dspring.profiles.active=prod'
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
