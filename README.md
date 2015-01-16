# jolokia-extra - Addons for Jolokia

*jolokia-extra*'s purpose is to provide a potpourri of extensions to the [Jolokia JMX-HTTP Bridge](http://www.jolokia.org)
 which are either to special or to big to include in the regular distribution.  


## JSR-77 Simplifier

Initially provided as a [pull request](https://github.com/rhuss/jolokia/pull/50) to Jolokia by Marcin Płonka, a set 
of simplifier is available for better access to [JSR-77](https://jcp.org/aboutJava/communityprocess/mrel/jsr077/index.html) 
defined statistic values. Although JSR-77 has been abandoned for JEE 6 and later it is somewhat continues to live
in various JEE containers, especially Websphere continues to support the JSR-77 naming and statistics scheme.

Using the profile **jsr77** will build the agents with JSR-77 support. This means they simplifiers get included then 
into the JVM and WAR agent. For OSGi you can directly deploy the `jolokia-extra-addon-jsr77.jar` to the OSGi container. 

To build the agents call maven as usual:

    mvn -Pjsr77 clean install
     
The agents can be found below `agents/`

## Health Checks

Health checks can be used to provide internal, possibly complex checks on MBeans and return a consolidated view of the
results via extra MBeans registered.

To create the specific agent, use the profile `health`

    mvn -Phealth clean install

Agents can be found in `agents/`

Currently a Proof-of-Concept is available which registers an MBean `jolokia:type=plugin,name=health` with two operations:
`cpuLoadCheck` (which takes a CPU load threshhold) and `mbeanCountCheck` which simply checks whether there are any MBeans
registered. Refer to the source in `addon/health` for details.

You can expect a much more sophisticated version with a flexible configuration here soon.