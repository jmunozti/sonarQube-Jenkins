pipeline {
    agent any

    tools {
        // Install the SonarQube Scanner tool
        sonarqubeScanner 'SonarQubeScanner'
    }

    stages {
        stage('SonarQube analysis') {
            steps {
                // Run the SonarQube analysis
                script {
                    def scannerHome = tool 'SonarQubeScanner'
                    withSonarQubeEnv('SonarQube') {
                        sh "${scannerHome}/bin/sonar-scanner"
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                // Wait for the SonarQube analysis to complete and check the quality gate status
                waitForQualityGate abortPipeline: true
            }
        }
    }
}