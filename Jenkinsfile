pipeline {
    agent any

    stages {
        stage('Build and Test API Project') {
            steps {
                dir('api') {    // Navigate to the api project directory
                    sh 'mvn clean install'  // Run Maven build and tests for API project
                }
            }
        }

        stage('Build and Test Web Project') {
            steps {
                dir('web') {    // Navigate to the web project directory
                    sh 'mvn clean install'  // Run Maven build and tests for Web project
                }
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'  // Collect test reports from both projects
            archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true  // Archive JAR files from both projects
        }
    }
}
