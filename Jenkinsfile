pipeline {
    agent any
    stages{
    	stage("Start the docker"){
    		steps{
    			bat "docker-compose up -d hubervice chromeservice firefoxservice"
    		}
    	}
    	stage("Run flightbookingservice"){
    		steps{
    			bat "docker-compose up flightbookingservice"
    		}
    	}
    }
    
    post{
     always{
     	archiveArtifacts artifacts : 'testoutput/**'
     	bat "docker-compose down"
     }
    }
}