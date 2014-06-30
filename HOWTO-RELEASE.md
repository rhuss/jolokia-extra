# Release instructions

* Update ${jolokia.version} in pom.xml
   
### Create release

   mvn -DdevelopmentVersion=1.0.1-SNAPSHOT -DreleaseVersion=1.0.0 -Dtag=v1.0.0 -Plabs,dist,jsr77 release:prepare
   mvn -Plabs,dist,jsr77 release:perform

### Deploy to Sonatype

   cd target/checkout
   mvn -Pdist,jsr77 deploy

