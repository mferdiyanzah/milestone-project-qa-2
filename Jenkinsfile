pipeline {
    agent any
    
    tools {
        maven 'maven'
        jdk 'jdk'
    }
    
    environment {
        APP1_DIR = 'api'
        APP2_DIR = 'web'
    }
    
    stages {
        stage('Checkout') {
            steps {
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
                    junit allowEmptyResults: true, testResults: '**/test-results/*.xml'
                }
                success {
                    // Publish Cucumber Reports if the tests are successful
                    publishHTML([
                        reportName: 'Rest Assured - API Testing Report',
                        reportDir: "${APP1_DIR}/target/cucumber-reports",
                        reportFiles: 'cucumber.html',
                        keepAll: true,
                        allowMissing: false,
                        alwaysLinkToLastBuild: true
                    ])
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
                    junit allowEmptyResults: true, testResults: '**/test-results/*.xml'
                }
                success {
                    // Publish Cucumber Reports if the tests are successful
                    publishHTML([
                        reportName: 'Selenium - Web Testing Report',
                        reportDir: "${APP2_DIR}/target/cucumber-reports",
                        reportFiles: 'cucumber.html',
                        keepAll: true,
                        allowMissing: false,
                        alwaysLinkToLastBuild: true
                    ])
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
        success {
            echo 'Pipeline succeeded! All applications built and tested successfully.'
        }
        failure {
            echo 'Pipeline failed! Check the logs for details.'
        }
    }
}