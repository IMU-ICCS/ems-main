/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.communication.colosseum;

import de.uniulm.omi.cloudiator.colosseum.client.entities.*;

public interface ColosseumConfigApi {

  Api createApi(String name, String internalProviderName);

  Api getApi(String name);

  Cloud createCloud(String name, String endpointName, long apiId);

  Cloud getCloud(String name);

  CloudProperty createCloudProperty(long cloudId, String key, String value);

  CloudCredential createCloudCredential(int cloudId, String user, String password, String tenant);

  Location getLocation(long cloudId, long cloudProviderId);

  Hardware getHardware(long cloudId, long cloudProviderId);

  Image getImage(long cloudId, long cloudProviderId);

}
