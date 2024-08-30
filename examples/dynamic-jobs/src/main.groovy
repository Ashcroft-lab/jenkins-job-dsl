
import hudson.model.*
import utils.JobUtils
import jobTemplate.KubeDeployement
import jobTemplate.MavenDeployement





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

    String job_type = job_config.get_job_type()

    if (job_type == "kubernetes"){
        new KubeDeployement(pipelineJob(job_config.get_job_name()), job_config)
    }

    else if (job_type == "maven"){
        new MavenDeployement(pipelineJob(job_config.get_job_name()), job_config)
    }





}
