/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.communication.colosseum;

import de.uniulm.omi.cloudiator.colosseum.client.Client;
import de.uniulm.omi.cloudiator.colosseum.client.entities.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ColosseumConfigClientApi implements ColosseumConfigApi {

  private Client client;

  @Override
  public Api createApi(@NonNull Api api) {
    return client.controller(Api.class)
      .create(api);
  }

  @Override
  public List<Api> getApis() {
    return client.controller(Api.class)
      .getList();
  }

  @Override
  public Cloud createCloud(@NonNull Cloud cloud) {
    return client.controller(Cloud.class)
      .create(cloud);
  }

  @Override
  public List<Cloud> getClouds() {
    return client.controller(Cloud.class)
      .getList();
  }

  @Override
  public CloudProperty createCloudProperty(@NonNull CloudProperty cloudProperty) {
    return client.controller(CloudProperty.class)
      .create(cloudProperty);
  }

  @Override
  public List<CloudProperty> getCloudProperties() {
    return client.controller(CloudProperty.class)
      .getList();
  }

  @Override
  public CloudCredential createCloudCredential(@NonNull CloudCredential cloudCredential) {
    return client.controller(CloudCredential.class)
      .create(cloudCredential);
  }

  @Override
  public List<CloudCredential> getCloudCredentials() {
    return client.controller(CloudCredential.class)
      .getList();
  }

  @Override
  public Image updateImage(@NonNull Image image) {
    return client.controller(Image.class)
      .update(image);
  }

  @Override
  public Optional<Image> getImage(Long cloudId, String name) {
    return client.controller(Image.class)
      .getSingle(image -> cloudId.equals(image.getCloud()) && name.equals(image.getProviderId()))
      .transform(Optional::ofNullable).or(Optional.empty());
  }

  @Override
  public Optional<Image> getImageWithWait(@NonNull Long cloudId, @NonNull String name, long timeout) {
    return client.controller(Image.class)
      .waitAndGetSingle(image -> cloudId.equals(image.getCloud()) && name.equals(image.getProviderId()), timeout, MILLISECONDS)
      .transform(Optional::ofNullable).or(Optional.empty());
  }

  @Override
  public OperatingSystem updateOperatingSystem(@NonNull OperatingSystem operatingSystem) {
    return client.controller(OperatingSystem.class)
      .update(operatingSystem);
  }

  @Override
  public Optional<OperatingSystem> getOperatingSystemWithWait(@NonNull Long id, long timeout) {
    return client.controller(OperatingSystem.class)
      .waitAndGetSingle(operatingSystem -> id.equals(operatingSystem.getId()), timeout, MILLISECONDS)
      .transform(Optional::ofNullable).or(Optional.empty());
  }

  @Override
  public Optional<Location> getLocation(Long cloudId, String name) {
    return client.controller(Location.class)
      .getSingle(location -> cloudId.equals(location.getCloud()) && name.equals(location.getName()))
      .transform(Optional::ofNullable).or(Optional.empty());
  }

  @Override
  public Optional<Location> getLocationWithWait(@NonNull Long cloudId, @NonNull String name, long timeout) {
    return client.controller(Location.class)
      .waitAndGetSingle(location -> cloudId.equals(location.getCloud()) && name.equals(location.getName()), timeout, MILLISECONDS)
      .transform(Optional::ofNullable).or(Optional.empty());
  }

  @Override
  public Optional<Hardware> getHardware(Long cloudId, String name) {
    return client.controller(Hardware.class)
      .getSingle(hardware -> cloudId.equals(hardware.getCloud()) && name.equals(hardware.getName()))
      .transform(Optional::ofNullable).or(Optional.empty());
  }

  @Override
  public Optional<Hardware> getHardwareWithWait(@NonNull Long cloudId, @NonNull String name, long timeout) {
    return client.controller(Hardware.class)
      .waitAndGetSingle(hardware -> cloudId.equals(hardware.getCloud()) && name.equals(hardware.getName()), timeout, MILLISECONDS)
      .transform(Optional::ofNullable).or(Optional.empty());
  }
}
