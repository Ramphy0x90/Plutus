module hhccco.plutus {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens hhccco.plutus to javafx.fxml;
    opens hhccco.plutus.models to javafx.base;
    exports hhccco.plutus;
    exports hhccco.plutus.controllers;
    opens hhccco.plutus.controllers to javafx.fxml;
}