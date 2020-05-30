node{
    stage("Git Checkout"){
        git credentialsId: 'git-creds', url: 'https://github.com/nilamazing/NodeProjDevOps', branch: 'master'
    }
    stage("Check Node & Npm version"){
        nodejs('default') {
            sh label: '', script: 'node -v; npm -v'
        }
    }
    stage("Install npm dependency"){
        nodejs('default') {
            sh label: '', script: 'npm install'
        }
    }
    stage("Run npm test case"){
        nodejs('default') {
            sh label: '', script: 'npm test'
        }
    }
    stage("Check Docker Installation"){
          sh label: 'Check docker version', script: 'docker -v'
    }
    stage("Build Docker Image"){
          sh label: 'Build Docker Image', script: 'docker build -t testnodeproj:1.0.0 -f Dockerfile .'
    }
    stage("List Docker Images"){
          sh label: 'List Docker Images', script: 'docker images | grep testnodeproj'
    }
}
