% Rule Processor
% 02/02/2016


# Table of Contents

- Installation
- Configuration
- ZeroMQ Configuration
- Usage
- Features
  - Global Cloud Provider Requirements
  - Cloud Provider Restrictions



# Installation (RP only)

```bash
$ export RP_HOME=`pwd`/rule_processor
$ git clone https://tuleap.ow2.org/plugins/git/paasage/rule_processor.git
$ cd rule_processor
$ mvn clean install
```


# Configuration (RP only)

Copy the Rule Processor Configuration file `wp3_profiler.properties` to the 
PaaSage configuration directory. Creat the configuration directory, if it not 
yet exists as follows:

```bash
$ sudo mkdir -p $PAASAGE_CONFIG_DIR
$ export PAASAGE_CONFIG_DIR=/opt/paasage/
$ cd $RP_HOME
$ cp src/main/resources/wp3_profiler.properties $PAASAGE_CONFIG_DIR
```


# ZeroMQ Configuration (limited to develop branch)

The configuration has the following parameters to setup ZeroMQ:

```bash
# ZEROMQ: Subscriber
SUBSCRIBER_PROTOCOL=tcp://
SUBSCRIBER_HOST=localhost
SUBSCRIBER_PORT=5544
SUBSCRIBER_TOPIC=startSolving

# ZEROMQ: Publisher
PUBLISHER_PROTOCOL=tcp://
PUBLISHER_HOST=*
PUBLISHER_PORT=5545
PUBLISHER_TOPIC=RPSolutionAvailable
```



# Usage

It is assumed that everything is setup in PAASAGE_CONFIG_DIR. The following
example uses the `md+hf.xmi` use case.

```bash
$ java -jar server-2015.9.1-SNAPSHOT-jar-with-dependencies.jar
$ java -jar init-2015.9.1-SNAPSHOT-jar-with-dependencies.jar md+hf.xmi mdhf
$ java -jar generator-2015.9.1-SNAPSHOT-jar-with-dependencies.jar mdhf cp.out 
$ java -jar rule-processor-2015.9.1-SNAPSHOT-jar-with-dependencies.jar -m mdhf -c upperware-models/MDPlusHyperflow1456231299631
```



# Features

## Global Cloud Provider Requirements

In addition to your default organisation model, you can declare a second organisation 
model in Camel that includes a requirement of having either only public or private 
cloud providers as candidates to deploy your components. This is done through a 
provider model. Please note that it is a global requirement that will affect all of 
your components. I will give you an example for the text-based editor:

```bash
organisation model RP_ProviderRequirements {
  provider GlobalProviderRequirements {
    www: ""
    postal address: ""
    email: "noreply@foobar.org"
    public
  }
  security level: LOW
}
```

It is REQUIRED to name the organisation model 'RP_ProviderRequirements', so that in 
step 3 of the PaaSage platform (i.e., Rule Processor), this model is guaranteed to 
be processed. The provider name, here 'GlobalProviderRequirements', is mandatory, too.
You can require to have your components to be deployed only on public cloud providers 
(if available) by setting the attribute 'public'. On the other hand, you can also 
define that you only want to deploy on private cloud providers by removing the 
'public' attribute as follows:

```bash
organisation model RP_ProviderRequirements {
  provider GlobalProviderRequirements {
    www: ""
    postal address:  ""
    email:  "noreply@foobar.org"
  }
  security level:  LOW
}
```

If you are using the tree-based editor, create an organisation model, and as the only 
child add a Cloud Provider, and set the 'Public' attribute either to true or false. 
For instance,

```bash
<organisationModels name="RP_ProviderRequirements">
  <provider
    name="GlobalProviderRequirements"
    www=""
    postalAddress=""
    email="noreply@foobar.org"
    public="true"
    PaaS="true"
    IaaS="true"
  />
</organisationModels>
```


## Cloud Provider Restrictions

It is also possible to limit deployment to a specific set of cloud providers. In order
to doing so, please include the `RP_ProviderRequirements` organisation model as shown
above, first. Then, you can add via the `CloudCredentials` feature of the user in your
organization model of the user limitations to specific cloud providers.

Please use the following names for the cloud providers:
- AmazonEC2
- Flexiant
- GWDG
- Omistack


Here is an example of the XMI model, that has limitations to AmazonEC2, Flexiant, and GWDG.
As a result, other potential cloud providers such as Omistack will be removed from the
configuration:

```bash
<organisationModels
      name="bewanOrganisation">
    <organisation
        name="bewan_sprl"
        www="www.bewan.be"
        postalAddress="Dreve Richelle 161 L bte 46"
        email="info@bewan.be"/>
    <users name="vanraesf"
        email="franky.vanraes@bewan.be"
        firstName="Franky"
        lastName="Vanraes"
        requirementModels="//@requirementModels.0">
      <cloudCredentials
          name="AmazonEC2"
          cloudProvider="//@organisationModels.1/@provider"
          username=""/>
      <cloudCredentials
          name="Flexiant"
          cloudProvider="//@organisationModels.1/@provider"/>
      <cloudCredentials
          name="GWDG"
          cloudProvider="//@organisationModels.1/@provider"/>
      <paasageCredentials
          password="vanraesf"/>
    </users>
  </organisationModels>
```