package eu.melodic.upperware.guibackend.model.byon;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IpAddress extends io.github.cloudiator.rest.model.IpAddress {
    private long id;
}
