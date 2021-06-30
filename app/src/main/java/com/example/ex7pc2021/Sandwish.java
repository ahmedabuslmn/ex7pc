package com.example.ex7pc2021;

import com.google.android.material.badge.BadgeDrawable;

import java.util.UUID;

public class Sandwish {
    public  String id ;
    public String customer_name ;
    public int pickles;
    public String hummus ;
    public String tihini;
    public String comment ;
    public String status  ;
    public boolean trash ;
    public String tiltle ;
    public String order ;
    public Sandwish()
    {
        this.id = UUID.randomUUID().toString() ;
        this.hummus="" ;
        this.tihini ="";
        this.comment = "";
        this.status="waiting.." ;
        this.pickles =0 ;
        trash = false ;
        this.order = "ADD ORDER" ;
        this.tiltle = " new reservation " ;
        this.customer_name="";
    }
    public void addPickles()
    {
        if(this.pickles < 10)
        {
            this.pickles++;
        }
    }
    public void  removePickless()
    {
        if(this.pickles > 0 )
        {
            this.pickles-- ;
        }
    }
    public void addComment(String com)
    {
        this.comment = com ;
    }
    public  void setName(String name)
    {
        this.customer_name =name ;
    }
    public void setStatus(String status)
    {
        this.status = status ;
    }
    public void addHummus()
    {
        if(this.hummus.equals("") )
        {
            this.hummus ="y";
        }
    }
    public void  removeHummus()
    {
        if(!this.hummus.equals("") )
        {
            this.hummus ="";
        }
    }

    public void addTihini()
    {
        if(this.tihini.equals("") )
        {
            this.tihini ="y" ;
        }
    }
    public void  removeTihini()
    {
        if(!this.tihini.equals("") )
        {
            this.tihini ="" ;
        }
    }

   public  int  getPickles()
   {
       return this.pickles ;
   }
    public String getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public String getStatus() {
        return status;
    }
    public String getCustomer_name()
        {
            return this.customer_name ;
        }

}
