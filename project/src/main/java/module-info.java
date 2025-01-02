module ma.ensa.project {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires static lombok;
    requires java.desktop;
    requires org.testng;
//    requires org.junit.platform.commons;
    requires java.mail;
    requires activation;

    requires MaterialFX;
    requires jbcrypt;
    requires com.jfoenix;
    requires com.fasterxml.jackson.databind;
    requires org.json;
    requires styled.xml.parser;
    requires org.apache.pdfbox;
//    requires styled.xml.parser;
//    requires org.apache.pdfbox;


    opens ma.ensa.project to javafx.fxml;
    opens ma.ensa.project.test to org.json;



    exports ma.ensa.project;
    exports ma.ensa.project.entity;
    exports ma.ensa.project.test;
    exports ma.ensa.project.controller;

    opens ma.ensa.project.controller to javafx.fxml;
}