pipeline {
    agent any
    stages{
    	stage("Start the docker")
    	{
    		steps
    		{
    			bat "docker-compose up -d hub chromeservice firefoxservice"
    		}
    	}
    	stage("Run flightbookingservice")
    	{
    		steps
    		{
    		bat "docker-compose up flightbookingservice"
    		}
    	}
    	
    	stage("Bring Grid down")
    	{
    		steps
    		{
    		bat "docker-compose down"
    		}
    	}
    }
}