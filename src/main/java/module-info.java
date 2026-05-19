module com.fx.Aircraft_Inventory_Management_System {
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

    opens com.fx.Aircraft_Inventory_Management_System to javafx.fxml;
    opens com.fx.Aircraft_Inventory_Management_System.controllers to javafx.fxml;
    opens com.fx.Aircraft_Inventory_Management_System.model to javafx.base, javafx.fxml;
    exports com.fx.Aircraft_Inventory_Management_System;
    opens com.fx.Aircraft_Inventory_Management_System.utils to javafx.base, javafx.fxml;
}