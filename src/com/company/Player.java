package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyHandler;
    int c=0;
    int c2=0;
    public final int scrnx;
    public final int scrny;
    Boolean key1=false;


    public Player(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;
        worldx=2700;//spawn points
        worldy=1800;
        speed=4;
        setplayerimg();
        scrnx=gp.scrnwidth/2- gp.tilesize/2;
        scrny=gp.scrnhight/2-gp.tilesize/2;
        solid=new Rectangle(8,16,24,24);
        solidareadefultx=solid.x;
        solidareadefulty=solid.y;



    }
    public void setplayerimg()
    {
        try {
            down1=ImageIO.read(new FileInputStream("src/playersprites/mydown1.png"));
            down2=ImageIO.read(new FileInputStream("src/playersprites/mydown2.png"));
            left1=ImageIO.read(new FileInputStream("src/playersprites/myleft1.png"));
            left2=ImageIO.read(new FileInputStream("src/playersprites/myleft2.png"));
            up1=ImageIO.read(new FileInputStream("src/playersprites/myup1.png"));
            up2=ImageIO.read(new FileInputStream("src/playersprites/myup2.png"));
            right1=ImageIO.read(new FileInputStream("src/playersprites/myright1.png"));
            right2=ImageIO.read(new FileInputStream("src/playersprites/myright2.png"));


        }catch (IOException e)
        {e.printStackTrace();}
    }
    public void update()
    {
        if(keyHandler.uppress==true||keyHandler.rightpress==true||keyHandler.leftpress==true||keyHandler.downpress==true) {
            if (keyHandler.gospeed == true) {
                speed=12;
            }
            else speed=4;
            if (keyHandler.uppress == true) {//w
                direction = "up";
            }
            if (keyHandler.downpress == true) {//s
                direction = "down";
            }
            if (keyHandler.leftpress == true) {//a
                direction = "left";
            }
            if (keyHandler.rightpress == true) {//d
                direction = "right";
            }
            gp.collisionDetector.checktile(this);
            int obj_indx=gp.collisionDetector.checkObj(this);//todo look here
            pickupobj(obj_indx);
            if(obj_indx!=-1)
            twomodeobj.openDoor(this,obj_indx);
            if(this.iscollision==false)
                switch (direction){
                    case "up":worldy -= speed;break;
                    case "down":worldy += speed;break;
                    case "left":worldx -= speed;break;
                    case "right":worldx += speed;break;

                }
            c2++;
            if (c2 > 10) {//sprite change after 1000/6 mili secs
                c++;
                c2 = 0;
            }
        }
    }

    public void pickupobj(int obj_indx) {
        if(obj_indx!=-1&&gp.objarr[obj_indx] instanceof Key1){
            key1=true;
            gp.objarr[obj_indx]=null;
        }
    }

    public void draw(Graphics2D g2)
    {   BufferedImage image=changesprite();
        g2.drawImage(image,scrnx,scrny,48,48,null);
    }
    public BufferedImage changesprite()
    {

        BufferedImage image=null;
        switch (direction){
            case "up":
                if(c%2==0)
                    image=up1;
                else image=up2;
                break;
            case "down":
                if(c%2==0)
                    image=down1;
                else image=down2;
                break;
            case "left":
                if(c%2==0)
                    image=left1;
                else image=left2;
                break;
            case "right":
                if(c%2==0)
                    image=right1;
                else image=right2;
                break;
        }

        return image;
    }
}
