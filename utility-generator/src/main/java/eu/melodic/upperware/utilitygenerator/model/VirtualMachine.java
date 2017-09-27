package eu.melodic.upperware.utilitygenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class VirtualMachine {

  private String id;
  private Double cost;
  @Setter
  private int count;
}
