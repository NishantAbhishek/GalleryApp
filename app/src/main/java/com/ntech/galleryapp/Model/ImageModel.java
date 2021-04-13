package com.ntech.galleryapp.Model;

public class ImageModel{

    private ImageInformation imageInformation;
    private PhotoGrapherImage photoGrapherImage;
    private PhotoGrapherInfo photoGrapherInfo;

    public ImageInformation getImageInformation() {
        return imageInformation;
    }

    public void setImageInformation(ImageInformation imageInformation) {
        this.imageInformation = imageInformation;
    }

    public PhotoGrapherImage getPhotoGrapherImage() {
        return photoGrapherImage;
    }

    public void setPhotoGrapherImage(PhotoGrapherImage photoGrapherImage) {
        this.photoGrapherImage = photoGrapherImage;
    }

    public PhotoGrapherInfo getPhotoGrapherInfo() {
        return photoGrapherInfo;
    }

    public void setPhotoGrapherInfo(PhotoGrapherInfo photoGrapherInfo) {
        this.photoGrapherInfo = photoGrapherInfo;
    }

    private String raw;
    private String full;
    private String regular;
    private String small;
    private String thumb;

    public ImageModel() {

    }

    public ImageModel(String raw, String full, String regular, String small, String thumb) {
        this.raw = raw;
        this.full = full;
        this.regular = regular;
        this.small = small;
        this.thumb = thumb;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getRegular() {
        return regular;
    }

    public void setRegular(String regular) {
        this.regular = regular;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public class ImageInformation {
        private String id;
        private String createdAt;
        private String updatedAt;
        private String description;
        private String blurHash;
        private int likes;

        public ImageInformation(){

        }

        public ImageInformation(String id, String createdAt, String updatedAt, String description, String blurHash, int likes) {
            this.id = id;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.description = description;
            this.blurHash = blurHash;
            this.likes = likes;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBlurHash() {
            return blurHash;
        }

        public void setBlurHash(String blurHash) {
            this.blurHash = blurHash;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }
    }

    public class PhotoGrapherImage{
        private String smallImage;
        private String mediumImage;
        private String largeImage;

        public PhotoGrapherImage() {
        }

        public PhotoGrapherImage(String smallImage, String mediumImage, String largeImage) {
            this.smallImage = smallImage;
            this.mediumImage = mediumImage;
            this.largeImage = largeImage;
        }

        public String getSmallImage() {
            return smallImage;
        }

        public void setSmallImage(String smallImage) {
            this.smallImage = smallImage;
        }

        public String getMediumImage() {
            return mediumImage;
        }

        public void setMediumImage(String mediumImage) {
            this.mediumImage = mediumImage;
        }

        public String getLargeImage() {
            return largeImage;
        }

        public void setLargeImage(String largeImage) {
            this.largeImage = largeImage;
        }
    }

    public class PhotoGrapherInfo{
        private String userId;
        private String userName;
        private String name;
        private String firstName;
        private String lastName;
        private String bio;

        public PhotoGrapherInfo() {
        }

        public PhotoGrapherInfo(String userId, String userName, String name, String firstName, String lastName, String bio) {
            this.userId = userId;
            this.userName = userName;
            this.name = name;
            this.firstName = firstName;
            this.lastName = lastName;
            this.bio = bio;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }
    }
}
