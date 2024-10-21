pipeline {
    agent any
    
    tools {
        maven 'maven' // Make sure this matches your Maven installation name in Jenkins
        jdk 'jdk' // Make sure this matches your JDK installation name in Jenkins
    }
    
    environment {
        // Adjust these paths according to your repository structure
        APP1_DIR = 'api'
        APP2_DIR = 'web'
    }
    
    stages {
        stage('Checkout') {
            steps {
                // Checkout code from repository
                checkout scm
            }
        }
        
        stage('Build API Testing') {
            steps {
                dir(APP1_DIR) {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }
        
        stage('API Testing') {
            steps {
                dir(APP1_DIR) {
                    sh 'mvn test'
                }
            }
            post {
                always {
                    junit "**/target/surefire-reports/*.xml"
                }
            }
        }
        
        stage('Build Web Testing') {
            steps {
                dir(APP2_DIR) {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }
        
        stage('Web Testing') {
            steps {
                dir(APP2_DIR) {
                    sh 'mvn test'
                }
            }
            post {
                always {
                    junit "**/target/surefire-reports/*.xml"
                }
            }
        }
        
        stage('Generate Reports') {
            steps {
                parallel(
                    "API Testing Report": {
                        dir(APP1_DIR) {
                            sh 'mvn site'
                        }
                    },
                    "Web Testing Report Report": {
                        dir(APP2_DIR) {
                            sh 'mvn site'
                        }
                    },
                )
            }
        }
    }
    
    post {
        always {
            // Clean workspace after build
            cleanWs()
        }
        success {
            echo 'Pipeline succeeded! All applications built and tested successfully.'
        }
        failure {
            echo 'Pipeline failed! Check the logs for details.'
        }
    }
}