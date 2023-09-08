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
            //post {
            //    always {
            //        junit '**/build/test-results/test/*.xml'
            //    }
            //}
        }
        stage('Deploy') {
            steps {
                script {
                    def remoteDir = 'app'
                    def jarPath = 'build/libs/*.jar'
                    def deployPath = 'jenkins/scripts/deploy.sh'
                    def deployScript = 'deploy.sh'
                    def ec2PublicIp = '13.213.43.63'

                    sshagent(credentials: ['jenkins-to-aws']) {
                        sh "scp -o StrictHostKeyChecking=no -i ./jenkins/scripts/abdl_aws_key.pem ${jarPath} ${deployPath} app@${ec2PublicIp}:${remoteDir}/"
                        sh "ssh -o StrictHostKeyChecking=no -i ./jenkins/scripts/abdl_aws_key.pem app@${ec2PublicIp} 'nohup bash ${remoteDir}/${deployScript} > /dev/null 2>&1 &'"
                    }
                }
            }
        }
    }
}
