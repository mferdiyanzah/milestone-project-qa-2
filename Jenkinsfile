pipeline {
    agent any
    
    tools {
        maven 'maven'
        jdk 'jdk'
    }
    
    environment {
        API = 'api'
        WEB = 'web'
        MOBILE = 'mobile'
    }

    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Prepare Directories') {
            steps {
                dir(API) {
                    sh 'mkdir -p target/cucumber-reports'
                }
                dir(WEB) {
                    sh 'mkdir -p target/cucumber-reports'
                }
                dir(MOBILE) {
                    sh 'mkdir -p target/cucumber-reports'
                }
            }
        }
        
        stage('Build API Testing') {
            steps {
                dir(API) {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }
        
        stage('API Testing') {
            steps {
                dir(API) {
                    sh 'mvn test | true'
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
                dir(WEB) {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }
        
        stage('Web Testing') {
            steps {
                dir(WEB) {
                    sh 'mvn test || true'
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

        stage('Build Mobile Testing') {
            steps {
                dir(MOBILE) {
                    sh 'mvn clean install -DskipTests'
                }
            }
        }

        stage('Mobile Testing') {
            steps {
                dir(MOBILE) {
                    sh 'mvn test || true'
                }
            }
        }

        stage('Generate Mobile Testing Report') {
            steps {
                cucumber buildStatus: 'UNSTABLE',
                    reportTitle: 'Appium - Mobile Testing report',
                    fileIncludePattern: 'mobile/**/*.json',
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
                        dir(API) {
                            sh 'mvn site'
                        }
                    },
                    "Web Testing Report Report": {
                        dir(WEB) {
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