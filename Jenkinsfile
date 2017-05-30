def app = "";
def commit_id="";
def module_name = "${env.JOB_NAME}".split("/")[-2];
def service_name = "${env.JOB_BASE_NAME}";
def path = "${module_name}/${service_name}"
def build_wkflo;
def ci_image = "egovio/ci:0.0.1"
def notifier = "";

try {
    node("slave"){
        checkout([
            scm: 'GitSCM',
            branches: [[name: 'origin/master']],
            sparseCheckoutPaths: [[path:'jenkins'], [path: 'Jenkinsfile'], [path: 'build.sh'], [path: path]]
        ])
        sh "git rev-parse --short HEAD > .git/commit-id".trim()
        commit_id = readFile('.git/commit-id')
        code_builder = load("jenkins/code_builder.groovy")
        archiver = load("jenkins/archiver.groovy")
        image_builder = load("jenkins/image_builder.groovy")
        notifier = load("jenkins/notifier.groovy")
        currentBuild.displayName = "${BUILD_ID}-${commit_id}"

        code_builder.build(path, ci_image)

        archiver.archiveArtifacts(["${path}/target/*.jar", "${path}/target/*.html"])

        image_builder.build(module_name, service_name, commit_id)

        image_builder.publish(service_name, commit_id)

        image_builder.clean(service_name, commit_id)
    }
} catch (e) {
    node{
        notifier.notifyBuild("FAILED")
        throw e
    }
}

