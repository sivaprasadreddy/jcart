# Jcart (forked from [sivaprasadreddy](https://github.com/sivaprasadreddy/jcart))
JCart is a simple e-commerce application built with Spring.

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/c9cd10aebb104847aeb0240d73d64f30)](https://www.codacy.com/app/rajadilipkolli/jcart?utm_source=github.com&utm_medium=referral&utm_content=rajadilipkolli/jcart&utm_campaign=badger)
[![Build status](https://travis-ci.org/rajadilipkolli/jcart.svg?branch=master)](https://travis-ci.org/rajadilipkolli/jcart.svg) [![Quality Gate](https://sonarqube.com/api/badges/gate?key=com.sonarqube.examples.java-gradle-travis-project)](https://sonarqube.com/dashboard/index/com.sivalabs:jcart)

#### This project is analysed on [SonarQube.com](https://sonarqube.com)!

## How to use jcart-admin application
login to Admin by using the url http://localhost:9090 and by entering the credentials as below, to use your own credentials edit data.sql present under jcart-core module resources folder

	Username :: rajadileepkolli@gmail.com
	Password :: superadmin
	
# To run sonar report locally execute below command
<b>mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package sonar:sonar</b>

# How to configure sonarqube online?
  - Login to Online SonarQube using GitHub Account at https://sonarqube.com/sessions/new?return_to=%2Fabout and once requested to authorize application , click on Authorize application
  - create .travis.yml file and place it in the root folder of the project 
  - Login to travis-ci using github
  - install gem and ruby installer in local machine
  - Generate Authorization Token for sonarQube to use travis CI which can be generated from https://sonarqube.com/account/security/ copy and place the authorization code at some handy place
  - using gem install encrypt the authorization code by issuing command travis encrypt ---------- (copied Authorization Code)
  - copy the encrypted code and paste in the below file at .travis.yml
  
		language: java
		sudo: false
		install: true
		addons:
		  sonarqube:
		    token:
		      secure: your_secure_authorization_code
		jdk:
		- oraclejdk8
		script:
		- mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package sonar:sonar
		cache:
		  directories:
		  - "$HOME/.m2/repository"
		  - "$HOME/.sonar/cache"

		  
  - Create a new file with name sonar-project.properties at the root path and set below properties
  
		# must be unique in a given SonarQube instance
		sonar.projectKey=com.sivalabs
		# this is the name and version displayed in the SonarQube UI. Was mandatory prior to SonarQube 6.1.
		sonar.projectName=jcart
		sonar.projectVersion=1.0
		# Path is relative to the sonar-project.properties file. Replace "\\" by "/" on Windows.
		# Since SonarQube 4.2, this property is optional if sonar.modules is set. 
		# If not set, SonarQube starts looking for source code from the directory containing 
		# the sonar-project.properties file.
		#sonar.sources=.
		 
		# Encoding of the source code. Default is default system encoding
		sonar.sourceEncoding=UTF-8
		
		# Some properties that will be inherited by the modules
		sonar.sources=src
		
  - Once we commit to github a travis-ci build will be generated and after successful Completion of build results will be published to sonarQube online		
  
