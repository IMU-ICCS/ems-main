package eu.passage.upperware.commons.model.provider;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProviderEnums {
    private List<String> providerNames;
    private List<String> cloudTypes;
}
