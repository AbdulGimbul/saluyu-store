pipeline {
    agent {
        docker {
            image 'abdl00/gradle-custom-image'
            args ['-p', '3000:3000', '-v', '/root/.m2:/root/.m2']
        }
    }
    triggers {
        pollSCM('*/2 * * * *')
    }
    stages {
        stage('Build') {
            steps {
                sh 'ls'
                sh 'java --version'
                // sh 'chmod +x gradlew'
                sh 'gradle clean build -x test --scan'
            }
        }
        stage('Test') {
            steps {
                sh 'gradle test'
            }
            post {
                always {
                    junit '**/build/test-results/test/*.xml'
                }
            }
        }
        stage('Manual Approval') {
            input {
                message "Lanjutkan ke tahap Deploy?"
                ok "Yes, of course"
            }
            steps {
                echo "Let's go"
            }
        }
        stage('Deploy') {
            steps {
                script {
                    def remoteDir = 'app'
                    def jarPath = 'target/*.jar'
                    def deployPath = 'jenkins/scripts/deploy.sh'
                    def deployScript = 'deploy.sh'
                    def ec2PublicIp = '3.1.203.88'

                    sshagent(credentials: ['jenkins-to-aws']) {
                        sh "scp -o StrictHostKeyChecking=no -i ${jarPath} ${deployPath} app@${ec2PublicIp}:${remoteDir}/"
                        sh "ssh -o StrictHostKeyChecking=no -i app@${ec2PublicIp} 'bash ${remoteDir}/${deployScript}'"
                    }
                    sleep 60
                }
            }
        }
    }
}
