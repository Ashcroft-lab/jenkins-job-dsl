
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


String create_folder_structure(ArrayList dir_list) {

    String prev_path = ""
    for (path in dir_list) {
        println("creating folder: " + prev_path + path)
        folder(prev_path+path)
        prev_path = prev_path + path + "/"
    }
    return prev_path
}


def cwd = hudson.model.Executor.currentExecutor().getCurrentWorkspace().absolutize()

pipeline_file_list  = searchYamlFiles(cwd.toString())


for (pipeline in pipeline_file_list) {
    println("current pipeline: " + pipeline)

    // parsed_job_config = new Yaml().load((pipeline as File).text)

    JobUtils job_config = new JobUtils(pipeline)
    
    ArrayList dir_structure = job_config.get_dir_structure()

    String dir_prefix = create_folder_structure(dir_structure)

    println("dir structure: " + dir_prefix)

    println("job name : "+ job_config.get_job_name())

    String job_type = job_config.get_job_type()

    if (job_type == "kubernetes"){
        new KubeDeployement().create(pipelineJob(job_config.get_job_name()), job_config)
    }

    else if (job_type == "maven"){
        new MavenDeployement().create(pipelineJob(job_config.get_job_name()), job_config)
    }





}
