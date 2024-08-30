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