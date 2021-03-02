package com.redevance.resources.view;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import com.redevance.alert.Alert;
import com.redevance.alert.AlertButtons;
import com.redevance.dao.dao.DAOFactory;
import com.redevance.dao.dao.DAOName;
import com.redevance.resources.icons.IconImage;
import com.redevance.services.listen;
import com.redevance.utils.AppUtils;
import com.redevance.utils.FileUtils;
import com.redevance.utils.filelistener;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import static com.redevance.Main.*;

public class Document extends AnchorPane {
    private enum typePath {
        DIRECTORY,FILE
    }

    private final String ICON_DOC="icon_doc",ICON_PDF="icon_pdf",ICON_IMAGE="icon_image",OTHER="file";
    private Path path;
    private final FileChooser fileChooser=new FileChooser();
    private filelistener listener;
    private listen list;
    private Image icon;
    ContextMenu cm;
    JFXButton menuAction=new JFXButton();
    private MenuItem rename,delete,open;
    FileUtils fu;

    private ObjectProperty<String> titre=new SimpleObjectProperty<>();
    Label title=new Label();
    //private WatchKey

    private void init(){

    }

    private class menu extends MenuItem{

    }

}
