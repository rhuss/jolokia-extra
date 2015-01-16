# Release instructions

* Update ${jolokia.version} in pom.xml

* Depending on the profile you want to release, replease `$profile` below with one of the following values:

  - `jsr77` for the JSR-77 extension
  - `health` for health checks

### Create release

   mvn -DdevelopmentVersion=1.0.1-SNAPSHOT -DreleaseVersion=1.0.0 -Dtag=v1.0.0 -Plabs,dist,$profile release:prepare
   mvn -Plabs,dist,$profile release:perform


### Deploy to Sonatype

   cd target/checkout
   mvn -Pdist,$profile deploy

