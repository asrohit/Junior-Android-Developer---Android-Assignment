package com.example.assignment;

public class Model
{
    String id,image,name,model,varent,type,addTimeStamp,updateTimeStamp;

    public Model(String id, String image, String name, String model, String varent, String type, String addTimeStamp, String updateTimeStamp) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.model = model;
        this.varent = varent;
        this.type = type;
        this.addTimeStamp = addTimeStamp;
        this.updateTimeStamp = updateTimeStamp;
    }


    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public String getVarent() {
        return varent;
    }

    public String getType() {
        return type;
    }

    public String getAddTimeStamp() {
        return addTimeStamp;
    }

    public String getUpdateTimeStamp() {
        return updateTimeStamp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setVarent(String varent) {
        this.varent = varent;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAddTimeStamp(String addTimeStamp) {
        this.addTimeStamp = addTimeStamp;
    }

    public void setUpdateTimeStamp(String updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }
}
