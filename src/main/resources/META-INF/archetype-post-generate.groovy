def projectPath = new File(request.outputDirectory, request.artifactId)

def mockserver = request.getProperties().get("mockserver")
def reports = request.getProperties().get("reports")
def sonarqube = request.getProperties().get("sonarqube")
def checkstyle = request.getProperties().get("checkstyle")
def githubActions = request.getProperties().get("github-actions")
def jenkins = request.getProperties().get("jenkins")

def packageName = request.getProperties().get("package")

def packagePath = packageName.replace(".", "/")

def base = new File(projectPath, "src/test/java/" + packagePath + "/base")
def tests = new File(projectPath, "src/test/java/" + packagePath + "/tests")
def utils = new File(projectPath, "src/test/java/" + packagePath + "/utils")
def resources = new File(projectPath, "src/test/resources")

if (mockserver != 'true') {
    assert new File(base, "MockContext.java").delete()
    assert new File(utils, "mocks").deleteDir()
    assert new File(utils, "listeners/MockServerListener.java").delete()
    assert new File(tests, "ExampleMockedTest.java").delete()
    assert new File(resources, "mocks").deleteDir()
}

if (reports != 'true') {
    assert new File(utils, "reports").deleteDir()
    assert new File(projectPath, "extent-config.xml").delete()
    assert new File(utils, "listeners/ExtentReportListener.java").delete()
}

if (sonarqube != 'true') {
    assert new File(projectPath, "Dockerfile").delete()
    assert new File(projectPath, "sonar.properties").delete()
}

if (checkstyle != 'true') {
    assert new File(projectPath, "checkstyle.xml").delete()
}

if (githubActions != 'true') {
    assert new File(projectPath, ".github").deleteDir()
}

if (jenkins != 'true') {
    assert new File(projectPath, "Jenkinsfile").delete()
}

// delete empty dirs
if (new File(utils, "listeners").list().length == 0) {
    assert new File(utils, "listeners").deleteDir();
}

