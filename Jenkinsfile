pipeline {
    agent any
    triggers {
        pollSCM('*/2 * * * *')
    }
    stages {
        stage('Build') {
            steps {
                sh '/usr/local/gradle-8.2.1/bin/gradle clean build -x test'
            }
        }
        stage('Test') {
            steps {
                sh '/usr/local/gradle-8.2.1/bin/gradle test'
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
                    def jarPath = 'build/libs/*.jar'
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
