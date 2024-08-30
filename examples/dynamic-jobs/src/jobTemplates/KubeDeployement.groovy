package jobTemplate

class KubeDeployement {
    void create(pipelineJob, job_config) {

        def env_list = job_config.get_environments()
        println("env list"+ env_list)

        def dev_stage = ""
        if ("dev" in env_list){
            dev_stage = """
                stage('Deploy DEV') {
                    steps {
                        echo 'Deploying the project...'
                    }
                }
            """
        }

        def qa_stage = ""
        if ("qa" in env_list) {
            qa_stage = """
                stage('Deploy QA') {
                    steps {
                        echo 'Deploying the project...'
                    }
                }
            """
        }

        def prod_stage = ""
        if ("prod" in env_list) {
            prod_stage = """
                stage('Deploy PROD') {
                    steps {
                        echo 'Deploying the project...'
                    }
                }
            """
        }



        pipelineJob.with {
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


                                ${dev_stage}


                                ${qa_stage}


                                ${prod_stage}
                            }
                        }
                    """)
                    sandbox()
                }
            }
        }
    }
}