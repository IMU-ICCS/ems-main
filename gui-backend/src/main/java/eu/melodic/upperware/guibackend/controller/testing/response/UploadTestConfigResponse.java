package eu.melodic.upperware.guibackend.controller.testing.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadTestConfigResponse {
    private String testConfigFilePath;
}
