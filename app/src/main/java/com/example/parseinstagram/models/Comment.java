package com.example.parseinstagram.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

@Parcel(analyze=Post.class)
@ParseClassName("Comment")
public class Comment extends ParseObject {
    public static final String COMMENT_DESCRIPTION = "description";
    public static final String COMMENT_USER = "person";
    public static final String COMMENT_CREATED_KEY = "createdAt";

    public String getDescription(){
        return getString(COMMENT_DESCRIPTION);
    }

    public void setDescription(String description){
        put(COMMENT_DESCRIPTION, description);
    }

    public ParseUser getUser(){
        return getParseUser(COMMENT_USER);
    }
}
