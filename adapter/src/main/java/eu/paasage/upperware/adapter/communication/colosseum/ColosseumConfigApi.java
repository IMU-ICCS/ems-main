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

import java.util.List;
import java.util.Optional;

public interface ColosseumConfigApi {

  Api createApi(Api api);

  List<Api> getApis();

  Cloud createCloud(Cloud cloud);

  List<Cloud> getClouds();

  CloudProperty createCloudProperty(CloudProperty cloudProperty);

  List<CloudProperty> getCloudProperties();

  CloudCredential createCloudCredential(CloudCredential cloudCredential);

  List<CloudCredential> getCloudCredentials();

  Image updateImage(Image image);

  Optional<Image> getImage(Long cloudId, String name);

  Optional<Image> getImageWithWait(Long cloudId, String name, long timeout);

  OperatingSystem updateOperatingSystem(OperatingSystem operatingSystem);

  Optional<OperatingSystem> getOperatingSystemWithWait(Long id, long timeout);

  Optional<Location> getLocation(Long cloudId, String name);

  Optional<Location> getLocationWithWait(Long cloudId, String name, long timeout);

  Optional<Hardware> getHardware(Long cloudId, String name);

  Optional<Hardware> getHardwareWithWait(Long cloudId, String name, long timeout);
}
