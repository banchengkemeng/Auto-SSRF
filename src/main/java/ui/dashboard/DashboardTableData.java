package ui.dashboard;

import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import lombok.*;
import ui.common.CommonTableData;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class DashboardTableData extends CommonTableData {
    private Integer id;
    private String status;
    private String host;
    private String method;
    private String url;
    private Integer statusCode;
    private Integer length;
    private String mime;
    private HttpRequestResponse requestResponse;

    private DashboardTableData(
            Integer id,
            String status,
            String host,
            String method,
            String url,
            Integer statusCode,
            Integer length,
            String mime,
            HttpRequestResponse requestResponse
    ) {
        this.id = id;
        this.status = status;
        this.host = host;
        this.method = method;
        this.url = url;
        this.statusCode = statusCode;
        this.length = length;
        this.mime = mime;
        this.requestResponse = requestResponse;
    }

    public static ChangeItem getChangeStatusItem(StatusEnum statusEnum) {
        return new ChangeItem(
                "status",
                statusEnum.getText()
        );
    }

    public static DashboardTableData buildDashboardTableData(
            Integer id,
            HttpRequestResponse httpRequestResponse
    ) {
        HttpRequest request = httpRequestResponse.request();
        HttpResponse response = httpRequestResponse.response();
        return new DashboardTableData(
                id,
                StatusEnum.WAITING.getText(),
                request.url(),
                request.method(),
                request.path(),
                (int) response.statusCode(),
                response.body().length(),
                response.statedMimeType().description(),
                httpRequestResponse
        );
    }
}


