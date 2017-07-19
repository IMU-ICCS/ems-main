Adapter
========
The component of PaaSage responsible for generating, validating and applying deployment plan based on CAMEL Model received from CDO Server. This component displaces both Adapter Manager and Plan Generator repositories.

Configuration
--------
Please make sure $PAASAGE_CONFIG_DIR environment variable is set and following property files are available in this PaaSage config directory:
- eu.paasage.upperware.adapter.config - used by the Adapter
- eu.paasage.mddb.cdo.client.properties - used by the CDO Client repository

For the security reasons the Adapter will not load default properties from local resource catalog.
