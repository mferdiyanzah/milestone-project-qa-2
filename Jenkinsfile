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
        DISCORD_WEBHOOK_URL = 'https://discord.com/api/webhooks/1298922886257578056/wCR-TDuvBpUz9zCZyQ8nXqXQiorOnLeEUcQ_SABnjp0ZW7eCEEhg0AADfrlW0b0J1d3D'
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
                    reportTitle: 'Selenium - Web Testing report',
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

        // New Stage to Send Reports to Discord
        stage('Send Reports to Discord') {
            steps {
                script {
                    // Read and parse the JSON reports for each testing type
                    def reports = ['API', 'Web', 'Mobile']
                    
                    for (report in reports) {
                        def jsonFile = "${report.toLowerCase()}/target/cucumber-reports/cucumber.json"
                        def reportJson = readJSON file: jsonFile
                        
                        // Initialize counters
                        int passedTests = 0
                        int failedTests = 0
                        int skippedTests = 0

                        // Iterate through each scenario and count results
                        for (scenario in reportJson.elements) { 
                            if (scenario.type == "scenario" || scenario.type == "scenario outline") { 
                                int scenarioResult = 0 // 0 = passed, 1 = failed, 2 = skipped
                                for (step in scenario.steps) { 
                                    switch (step.result.status) { 
                                        case "passed":
                                            scenarioResult = 0
                                            break
                                        case "failed":
                                            scenarioResult = 1
                                            break
                                        case "skipped":
                                            scenarioResult = 2
                                            break
                                    } 
                                }
                                if (scenarioResult == 0) {
                                    passedTests++
                                } else if (scenarioResult == 1) {
                                    failedTests++
                                } else {
                                    skippedTests++
                                }
                            } 
                        }

                        // Total tests calculated as sum of passed, failed, and skipped tests
                        int totalTests = passedTests + failedTests + skippedTests

                        // Determine color based on results
                        def color = failedTests == 0 ? 65280 : (passedTests == 0 ? 16711680 : 16776960)

                        // Send the report to Discord
                        def payload = """
                            {
                                "embeds": [{
                                    "title": "${report} Testing Results - Build #${currentBuild.number}",
                                    "color": ${color},
                                    "fields": [
                                        {"name": "Status", "value": "${currentBuild.currentResult}", "inline": true},
                                        {"name": "Test Results", "value": "✅ Passed: ${passedTests}\\n❌ Failed: ${failedTests}\\n⏩ Skipped: ${skippedTests}\\n📊 Total: ${totalTests}", "inline": false}
                                    ],
                                    "footer": {"text": "View detailed report: [Jenkins](${env.BUILD_URL})"},
                                    "timestamp": "${new Date().format("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", TimeZone.getTimeZone('UTC'))}"
                                }]
                            }
                            """
                            sh """
                                curl -H "Content-Type: application/json" -d '${payload}' ${DISCORD_WEBHOOK_URL}
                            """
                    }
                }
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