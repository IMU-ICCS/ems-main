import {version} from '../../../../package.json'

export default {
  name: 'Footer',
  data() {
    return {
      ems_version: process.env.VUE_APP_EMS_VERSION,
      ems_build: process.env.VUE_APP_EMS_BUILD,
      package_version: version,
    };
  }
}
