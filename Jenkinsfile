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
        stage('Start SQL') {
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
          stage('SonarQube Analysis') {
 steps {
              sh "./mvnw clean verify sonar:sonar -Dsonar.projectKey=kadeem -Dsonar.projectName='kadeem' -Dsonar.token='squ_fe60665abc6b86b59109051fb5ba68928f82b0e6' -Dspring.profiles.active=test "
}
          }

        stage('Maven Build and Deploy to Nexus') {
            steps {
            script {
             def attempts = 0
                          def maxAttempts = 5
                          def success = false
                            while (!success && attempts < maxAttempts) {
                              try {
                                  // Use docker exec to run mysqladmin ping within the container
                                      sh 'sudo docker exec mysqldb mysqladmin -uroot -proot ping --silent'
                                      success = true
                              } catch (Exception e) {
                                 attempts++
                                  if (attempts < maxAttempts) {
                                  echo "Waiting for SQL server to be up... attempt: ${attempts}"
                                  sleep(time: 60, unit: 'SECONDS') // wait for 60 seconds before next retry
                                    } else {
                                  echo "Failed to start SQL server after ${maxAttempts} attempts"
                                  currentBuild.result = 'FAILURE'
                                  return
                                  }
                                 }
                                }
            sh './mvnw clean deploy -Dspring.profiles.active=build -DskipTests '
            }

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
