pipeline {
	agent any

	environment {
		mavenHome = tool 'mvn'
	}

	tools {
		jdk 'jdk-17'
	}

	stages {

		stage('Build'){
			steps {
				bat "mvn clean install -DskipTests"
			}
		}
		stage('Deploy') {
			steps {
			    bat "mvn jar:jar deploy:deploy"
			}
		}
	}
}