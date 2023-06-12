package pt.isec.pa.tinypac.ui.gui.ressources;

import javafx.scene.image.Image;

import java.util.HashMap;

public class ImageLoader {
    private static HashMap<String,Image> images = new HashMap<>();

    public static Image getImage(String name){
        Image image=images.get(name);
        if(image==null){
            image=new Image(ImageLoader.class.getResourceAsStream("images/"+name));
            images.put(name,image);
        }
        return image;
    }
}
