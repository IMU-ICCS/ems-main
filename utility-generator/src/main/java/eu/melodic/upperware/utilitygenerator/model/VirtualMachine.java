package eu.melodic.upperware.utilitygenerator.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class VirtualMachine {

  private final String id;
  private final Double cost;
  @Setter
  private int count;
}
