node{
    stage("Check Tools Installation Path"){
        def nodeToolPath = tool name: 'default', type: 'nodejs'
        def nodePath = "${nodeToolPath}/bin/npm"
        // echo "The Node Path is :- "
        // echo "${nodePath}"
        sh label: 'Check Node version', script: "${nodeToolPath}/bin/node -v"
        sh label: 'Check Npm version', script: "ls ${nodeToolPath}/bin/npx"
    }
}