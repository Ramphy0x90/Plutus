module hhccco.plutus {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.xerial.sqlitejdbc;

    opens hhccco.plutus to javafx.fxml;
    opens hhccco.plutus.models to javafx.base;
    opens hhccco.plutus.controllers to javafx.fxml;

    exports hhccco.plutus;
    exports hhccco.plutus.models;
    exports hhccco.plutus.views;
    exports hhccco.plutus.controllers;
    exports hhccco.plutus.util;
}