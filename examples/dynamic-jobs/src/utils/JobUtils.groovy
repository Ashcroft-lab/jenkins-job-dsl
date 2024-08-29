package utils

@Grab(group='org.yaml', module='snakeyaml', version='1.26')

import org.yaml.snakeyaml.Yaml

class JobUtils {

    def job_config

    JobUtils(pipeline){
        def parsed_job_config = new Yaml().load((pipeline as File).text)
        this.job_config = parsed_job_config
    }

    String get_job_name() {
        return this.job_config.job_name
    }

    String get_build_command() {
        return this.job_config.build_command
    }
}