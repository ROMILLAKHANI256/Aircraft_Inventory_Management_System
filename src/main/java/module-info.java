module com.fx.apd_workshop6_7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires annotations;

    opens com.fx.apd_workshop6_7 to javafx.fxml;
    opens com.fx.apd_workshop6_7.controllers to javafx.fxml;
    opens com.fx.apd_workshop6_7.model to javafx.base, javafx.fxml;
    exports com.fx.apd_workshop6_7;
    opens com.fx.apd_workshop6_7.utils to javafx.base, javafx.fxml;
}