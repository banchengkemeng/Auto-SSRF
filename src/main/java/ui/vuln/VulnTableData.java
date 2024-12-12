package ui.vuln;

import burp.api.montoya.collaborator.Interaction;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ui.common.CommonTableData;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class VulnTableData extends CommonTableData {
    private Integer id;
    private String host;
    private String method;
    private String url;
    private Integer statusCode;
    private Integer length;
    private String mime;
    private HttpRequestResponse requestResponse;
    private List<Interaction> interactions;

    private VulnTableData(
            Integer id,
            String host,
            String method,
            String url,
            Integer statusCode,
            Integer length,
            String mime,
            HttpRequestResponse requestResponse,
            List<Interaction> interactions
    ) {
        this.id = id;
        this.host = host;
        this.method = method;
        this.url = url;
        this.statusCode = statusCode;
        this.length = length;
        this.mime = mime;
        this.requestResponse = requestResponse;
        this.interactions = interactions;
    }

    public static VulnTableData buildVulnTableData(
            Integer id,
            HttpRequestResponse httpRequestResponse,
            List<Interaction> interactions
    ) {
        HttpRequest request = httpRequestResponse.request();
        HttpResponse response = httpRequestResponse.response();
        return new VulnTableData(
                id,
                request.url(),
                request.method(),
                request.path(),
                (int) response.statusCode(),
                response.body().length(),
                response.statedMimeType().description(),
                httpRequestResponse,
                interactions
        );
    }
}
