Adapter
========
The component of Melodic responsible for generating, validating and applying deployment plan based on CAMEL Model received from CDO Server. This component displaces both Adapter Manager and Plan Generator repositories.

Configuration
--------
Please make sure $MELODIC_CONFIG_DIR environment variable is set and following property files are available in this Melodic config directory:
- eu.melodic.upperware.adapter.config - used by the Adapter
- eu.melodic.mddb.cdo.client.properties - used by the CDO Client repository

For the security reasons the Adapter will not load default properties from local resource catalog.
