module com.solitarios {
    requires javafx.controls;
    requires javafx.fxml;
    opens com.solitarios to javafx.fxml;
    opens com.solitarios.module to javafx.fxml;
    exports com.solitarios to javafx.fxml, javafx.graphics;
    exports com.solitarios.module to javafx.fxml;
    exports com.solitarios.module.category to javafx.fxml;
    opens com.solitarios.module.category to javafx.fxml;
    exports com.solitarios.module.user to javafx.fxml;
    opens com.solitarios.module.user to javafx.fxml;
    exports com.solitarios.module.article to javafx.fxml;
    opens com.solitarios.module.article to javafx.fxml;
    exports com.solitarios.bean to javafx.fxml, com.fasterxml.jackson.databind;
    opens com.solitarios.bean to javafx.base, javafx.fxml;

    // jackson
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires java.net.http;
    // httpclient
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    // lombok
    requires lombok;
}