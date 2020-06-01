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
          sh label: 'Build Docker Image', script: 'docker build -t nilamazing/testnodeproj:1.0.0 -f Dockerfile .'
    }
    stage("List Docker Images"){
          sh label: 'List Docker Images', script: 'docker images | grep testnodeproj'
    }
    stage("Docker Hub Login and Push Image"){
        withCredentials([string(credentialsId: 'docker-hub-pwd', variable: 'dockerHubPwd')]) {
             sh label: 'Docker Hub Login', script: "docker login -u nilamazing -p ${dockerHubPwd}"
             sh label: 'Docker Push Image', script: "docker push nilamazing/testnodeproj:1.0.0"
        }
    }
    stage("Run Docker Container in Target Host"){
        def dockerCmd = 'docker run -d --name TestNodeServer -p 8081:8081 nilamazing/testnodeproj:1.0.0'
        sshagent(['ssh-pvt-key-mylinux']) {
            sh "ssh -o StrictHostKeyChecking=no demo@192.168.0.93 ${dockerCmd}"
            
        }
    }
}
