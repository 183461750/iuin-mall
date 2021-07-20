pipeline {
    agent any
    parameters {
        gitParameter branchFilter: 'origin/(.*)', defaultValue: 'master', name: 'BRANCH', type: 'PT_BRANCH'
    }
    stages {
        stage('Example') {
          steps {
            git branch: "${params.BRANCH}", url: 'git@gitlab.ihaozhuo.com:Java_Service/YJK-Java.git'
          }
        }
        stage('package') {
            agent {
                docker {
                    image 'maven'
                    args '-v /root/.m2:/root/.m2 --entrypoint='
                }
            }
            steps {
                script{
                    echo "WORKSPACE：${env.WORKSPACE}"
                    echo "Branch：${env.NODE_NAME}"
                    if ("${env.NODE_NAME}" == "master") {
                        sh "sh package-prod.sh"
                    }
                }
            }
        }
        stage('build') {
            agent none
            steps {
                script{
                    echo "WORKSPACE：${env.WORKSPACE}"
                    echo "Branch：${env.NODE_NAME}"
                    if ("${env.NODE_NAME}" == "master") {
                        sh "sh build-prod.sh"
                    }
                }
            }
        }
    }
}