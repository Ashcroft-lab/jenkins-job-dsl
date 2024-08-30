package utils

@Grab(group='org.yaml', module='snakeyaml', version='1.26')

import org.yaml.snakeyaml.Yaml

class JobUtils {

    def job_config
    def file_path

    JobUtils(pipeline){
        def parsed_job_config = new Yaml().load((pipeline as File).text)
        this.job_config = parsed_job_config
        this.file_path = pipeline
    }

    ArrayList get_dir_structure() {
        ArrayList dir_list = file_path.split("/pipelines/")[1].split("/")
        dir_list.pop()
        return dir_list
    }

    String get_job_name() {
        String file_name = file_path.split("/")[-1]
        String job_name = file_name.split(".yaml")[0].split(".yml")[0]
        return job_name
    }

    String get_build_command() {
        return this.job_config.build_command
    }

    ArrayList get_environments() {
        return this.job_config.env
    }

    String get_job_type() {
        return this.job_config.job_type
    }
}