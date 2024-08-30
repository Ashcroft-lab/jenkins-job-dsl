
import hudson.model.*
import utils.JobUtils



// function to search for all the files ending with .yaml or .yml
ArrayList searchYamlFiles(String dirPath) {
    ArrayList file_list = []
    File dir = new File(dirPath)
    if (dir.isDirectory()) {
        dir.eachFileRecurse { file ->
            if (file.isFile() && (file.name.endsWith(".yaml") || file.name.endsWith(".yml"))) {
                file_list.add(file.absolutePath)
            }
        }
    } else {
        println("Error: $dirPath is not a valid directory.")
    }
    return file_list
}

def cwd = hudson.model.Executor.currentExecutor().getCurrentWorkspace().absolutize()

pipeline_file_list  = searchYamlFiles(cwd.toString())


for (pipeline in pipeline_file_list) {
    println("current pipeline: " + pipeline)

    // parsed_job_config = new Yaml().load((pipeline as File).text)

    JobUtils job_config = new JobUtils(pipeline)

    println("job name : "+ job_config.get_job_name())

    pipelineJob(job_config.get_job_name()) {
    definition {
        cps {
            script("""
pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
            }
        }

        stage('Build') {
            steps {
                echo "Building"
                echo '${job_config.get_build_command()}'
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
            }
        }

        stage('Deploy DEV') {
            steps {
                echo 'Deploying the project...'
            }
        }

        stage('Deploy QA') {
            steps {
                echo 'Deploying the project...'
            }
        }

        stage('Deploy PROD') {
            steps {
                echo 'Deploying the project...'
            }
        }
    }
}
""")
            sandbox()
        }
    }
}



}
