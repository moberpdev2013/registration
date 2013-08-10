package com.application.datastore;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import com.jdo.CloudComplaintsData;
import com.jdo.CloudContactEnty;
import com.jdo.CloudContactUtils;

public class UploadComplaints extends HttpServlet {
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
       
        BlobKey blobKey = blobs.get("fileField");       
        
       PersistenceManager pm = PMF.get().getPersistenceManager();
       
       CloudComplaintsData cc=new CloudComplaintsData();
       
       cc.setCreatetionDate(new Date().getTime()+"");
       
       if(blobKey!=null){
    	   
    	   cc.setDataImage(blobKey.getKeyString());
      
       }
       String user=req.getParameter("user");
       
       cc.setOwner(user);
       
       CloudContactEnty cu =CloudContactUtils.getContact(user);
       
       if(cu!=null){
    	   
    	   cc.setOwnerCategory(cu.getCategory());
       }
       
       String cT=req.getParameter("complaintBoxText");
       String cA=req.getParameter("complaintBoxAddress");
       
       cc.setOwner("");
       cc.setDataText(cT);
       cc.setAddress(cA);
       cc.setStatus(CloudComplaintsData.OPEN);
       cc.setResolutionText("");
       cc.setResolver("");
       pm.makePersistent(cc);
       
       
       
    }
}
