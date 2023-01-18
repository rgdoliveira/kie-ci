/**
* Configure required fields, like attributes, for a non-text-only RHSA Errata
*/
def scriptTemplate = this.getClass().getResource("job-scripts/prod_rhsa_errata_config.jenkinsfile").text
def parsedScript = scriptTemplate.replaceAll(/<%=\s*(\w+)\s*%>/) { config[it[1]] ?: '' }

def folderPath = "PROD"
folder(folderPath)

pipelineJob("${folderPath}/rhsa-errata-config") {
    description('This job configures required fields, like attributes, for a non-text-only RHSA Errata.')

    parameters {
        stringParam('ERRATA_ID', '', 'The Errata ID')
        stringParam('CHUNK_SIZE', '300', 'Ammount of files that will be updated in each chunk')
    }

    logRotator {
        numToKeep(32)
    }

    definition {
        cps {
            script(parsedScript)
            sandbox()
        }
    }

}