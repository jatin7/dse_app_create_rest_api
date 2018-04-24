# Setup your environment

Follow this instruction to setup your environment. And go through the tutorial

### Install Git, Java and Maven

If not already available please, install 
* [Download and install GIT](https://git-scm.com/downloads)
* [Download and install Java8+](http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html)
* [Download and install Maven](https://maven.apache.org/download.cgi)

Here are the commands to install on MAC with `brew`:
```
brew install git
brew tap caskroom/versions
brew update
brew cask install java8
brew install maven
```

You should be able to run `mvn -v`
```
Apache Maven 3.5.2 (138edd61fd100ec658bfa2d307c43b76940a5d7d; 2017-10-18T09:58:13+02:00)
Maven home: /usr/local/Cellar/maven/3.5.2/libexec
Java version: 1.8.0_162, vendor: Oracle Corporation
Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0_162.jdk/Contents/Home/jre
Default locale: en_FR, platform encoding: UTF-8
OS name: "mac os x", version: "10.13.4", arch: "x86_64", family: "mac"
```

### Install your IDE

Download and install your favorite IDE. 
Here are some of the most relevant. Video tutorial have been captured with Eclipse STS.
* [Eclipse](https://www.eclipse.org/downloads/eclipse-packages/)
* [Eclipse STS](https://spring.io/tools/sts/all)
* [IntelliJ](https://www.jetbrains.com/idea/)

### Download and install Docker

* [Download and install Docker](https://docs.docker.com/install)

Here are the commands to install on MAC with `brew`:
```
brew install docker
```

You should be able to execute `docker -v`
```
Docker version 18.03.0-ce, build 0520e24
```

### Start Datastax Enterprise and Datastax Studio

Datastax provides docker images on the [Dockerhub](https://hub.docker.com/u/datastax). This tutorial will use latest version of DSE and studio. At anytime you can download and use them in development. Production usages require licenses. Here the command to start the container on your station at anytime.
```
# do not execute those lines - only for explanation please use docker compose
docker run -e "DS_LICENSE=accept" -it -d -p 9042:9042 --name dse6 datastax/dse-server
docker run -e "DS_LICENSE=accept" -it -d -p 9091:9091 --link dse6:dse6 datastax/dse-studio
```

To ease development we will leverage on [Docker Compose](https://docs.docker.com/compose/) to start and link our containers :

* Clone this repository 
```
git clone https://github.com/DataStax-Academy/tutorial-create-rest-api.git
cd tutorial-create-rest-api
```

* Startup Studio and DSE using docker : 
```
docker-compose up -d
```

Your should get output like
```
cedricklunven@Cedricks-MBP:~/dev/workspace-misc/tutorial-create-rest-api> docker-compose up -d
Creating network "tutorialcreaterestapi_default" with the default driver
Creating tutorialcreaterestapi_dse6_1 ... done
Creating tutorialcreaterestapi_studio_1 ... done
```

To see your running containers :
```
docker ps | grep datastax
```

### Setup studio

* Connect to [Datastax Studio](http://localhost:9091)

* Edit connection to use hostname dse6 (and not 127.0.0.1)
<img src="https://raw.githubusercontent.com/DataStax-Academy/tutorial-create-rest-api/master/img/studio-connection.png" width="500" />

* Create a notebook "REST API"
<img src="https://raw.githubusercontent.com/DataStax-Academy/tutorial-create-rest-api/master/img/studio-notebook.png" width="500" />


* Test Connectivity
```sql
describe keyspaces;
```

* Create KeySpace (sample)
```sql
CREATE KEYSPACE IF NOT EXISTS demo 
WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };
```

* Create Schema (sample)
```sql
// Comments for a given video
CREATE TABLE IF NOT EXISTS demo.comments_by_video (
    videoid uuid,
    commentid timeuuid,
    userid uuid,
    comment text,
    PRIMARY KEY (videoid, commentid)
) WITH CLUSTERING ORDER BY (commentid DESC);

// Comments for a given user
CREATE TABLE IF NOT EXISTS demo.comments_by_user (
    userid uuid,
    commentid timeuuid,
    videoid uuid,
    comment text,
    PRIMARY KEY (userid, commentid)
) WITH CLUSTERING ORDER BY (commentid DESC);
```

### Import project

* Browse to project and check everything ok
```
cd tutorial-create-rest-api
mvn clean install
```

* Import the maven project into your environment

* Open class `TestConnection`
* Remove the `@Ignore` annotation.
* execute `checkConnection` test.




