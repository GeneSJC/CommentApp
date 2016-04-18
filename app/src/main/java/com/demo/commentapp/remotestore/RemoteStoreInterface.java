package com.demo.commentapp.remotestore;

import com.demo.commentapp.model.Annotation;
import com.demo.commentapp.model.Comment;

import java.io.IOException;

/**
 * Created by gene on 4/18/16.
 */
public interface RemoteStoreInterface {

    public String saveComment(Comment comment) throws IOException;

    // public void saveAnnotation(Annotation annotation);
}
