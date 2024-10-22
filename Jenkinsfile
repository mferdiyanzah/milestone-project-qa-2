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

        stage('Prepare Directories') {
            steps {
                dir(APP1_DIR) {
                    sh 'mkdir -p target/cucumber-reports'
                }
                dir(APP2_DIR) {
                    sh 'mkdir -p target/cucumber-reports'
                }
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
                    sh 'mvn test | true'
                    stash includes: 'target/cucumber-reports/*.json', name: 'cucumber-reports'
                }
            }
        }

        stage('Generate API Report') {
            steps{
                cucumber buildStatus: 'UNSTABLE',
                    reportTitle: 'REST Assured - API Testing report',
                    fileIncludePattern: 'api/**/*.json',
                    trendsLimit: 10,
                    classifications: [
                        [
                            'key': 'Browser',
                            'value': 'Chrome'
                        ]
                    ]
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
                    sh 'mvn test || true'
                    stash includes: 'target/cucumber-reports/*.json', name: 'cucumber-reports'
                }
            }
        }

        stage('Generate Web Testing Report') {
            steps {
                cucumber buildStatus: 'UNSTABLE',
                    reportTitle: 'Selenium - API Testing report',
                    fileIncludePattern: 'web/**/*.json',
                    trendsLimit: 10,
                    classifications: [
                        [
                            'key': 'Browser',
                            'value': 'Chrome'
                        ]
                    ]
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