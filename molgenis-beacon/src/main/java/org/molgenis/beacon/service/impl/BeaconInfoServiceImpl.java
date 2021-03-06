package org.molgenis.beacon.service.impl;

import static org.molgenis.beacon.config.BeaconMetadata.BEACON;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.molgenis.beacon.config.Beacon;
import org.molgenis.beacon.controller.model.BeaconResponse;
import org.molgenis.beacon.service.BeaconInfoService;
import org.molgenis.data.DataService;
import org.molgenis.data.UnknownEntityException;
import org.springframework.stereotype.Component;

@Component
public class BeaconInfoServiceImpl implements BeaconInfoService {
  private final DataService dataService;

  public BeaconInfoServiceImpl(DataService dataService) {
    this.dataService = Objects.requireNonNull(dataService);
  }

  @Override
  public List<BeaconResponse> getAvailableBeacons() {
    return dataService
        .findAll(BEACON, Beacon.class)
        .map(Beacon::toBeaconResponse)
        .collect(Collectors.toList());
  }

  @Override
  public BeaconResponse info(String beaconId) {
    Beacon beacon = dataService.findOneById(BEACON, beaconId, Beacon.class);
    if (beacon == null) {
      throw new UnknownEntityException(BEACON, beaconId);
    }
    return beacon.toBeaconResponse();
  }
}
