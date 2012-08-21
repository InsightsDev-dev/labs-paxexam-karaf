# Welcome to the PAXEXAM Apache Karaf extension

## Introduction

While [PAXEXAM](http://team.ops4j.org/wiki/display/paxexam/Pax+Exam) is an OSGi testing framework which is (since 
version 2.0) able to run plain osgi runtime natively or based on 
[PAXRUNNER](http://team.ops4j.org/wiki/display/paxrunner/Pax+Runner) [Apache Karaf](http://karaf.apache.org) is an 
OSGi Server based on freely selectable OSGi runtimes. In addition Apache Karaf adds tons of additional configurations 
and libraries making it far easier to work with OSGi. On the other hand though those extensions make it very hard to 
test Apache Karaf itself or on Apache Karaf based distributions using PAXEXAM.

This project creates a container for PAXEXAM starting any Apache Karaf based distribution directly and deploys your 
test probes on it. That way less configuration is needed and the risk for errors is drastically reduced since the
exact configurations of the distribution are used.

## Limitations

Currently this extension does only work with distributions based on Karaf >= 2.2.0 like the latest Karaf releases
themselves or the [OpenEngSB](http://openengsb.org). For this reason [Servicemix](http://servicemix.apache.org) in
a version smaller or equal 4.3.0 won't work with this framework.

## Usage

Basically there is nearly no difference to any other PAXEXAM test except that you use
org.openengsb.extensions.paxexam.karaf/container as your container instead of native or runner in your pom.xml.

From that point on there are minor differences. The extension tries to support the configuration options available 
via PAXEXAM itself as good as possible. Still there are various additional configurations and some are handled 
a little bit differently than in a "regular" PAXEXAM environment. The full usage of the framework could be
found in the [Github Wiki Pages](https://github.com/openengsb/labs-paxexam-karaf/wiki).

## License

The entire source code is licensend under the Apache 2 License and is therefore free to be used in commercial
projects.

## Build

To build the project simply checkout the latest master or tag and do a `maven install`.

## Contribute

Contributions are always welcomed in every form (documentation, ideas or code). For ideas or problems please
simply use the [OpenEngSB Issue Tracker Extension Project](issues.openengsb.org).
For documentation the [Github Wiki Pages](https://github.com/openengsb/labs-paxexam-karaf/wiki) 
and for code the Github Pull Request mechanism.

