package common;

import burp.api.montoya.collaborator.Interaction;
import burp.api.montoya.http.message.HttpRequestResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaboratorResult {
    private Integer id;
    private Boolean success;
    private HttpRequestResponse httpRequestResponse;
    private List<Interaction> interactions;
}