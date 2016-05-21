/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataLoader;

/**
 *
 * @author Nixholas
 */
public class Station{
    private String LineDesc     = ""; //Data for Station Name
    private String LineCde      = ""; //Data for Station Code
    private float xAxis         = 0;
    private float yAxis         = 0;
     
    public Station (String LineDesc) {
        this.LineDesc = LineDesc;
        this.xAxis = 0;
        this.xAxis = 0;
    }
    
    public Station (String LineDesc, String LineCde) {
        this.LineDesc = LineDesc;
        this.LineCde = LineCde;
        this.xAxis = 0;
        this.xAxis = 0;
    }
    
    public Station (String LineDesc, float xAxis, float yAxis) {
       this.LineDesc = LineDesc;
       this.LineCde = "";
       this.xAxis = xAxis;
       this.yAxis = yAxis;        
    }
    
    public Station (String LineDesc, String LineCde, float xAxis, float yAxis) {
       this.LineDesc = LineDesc;
       this.LineCde = LineCde;
       this.xAxis = xAxis;
       this.yAxis = yAxis;        
    }
    
    public String getName() {
        return LineDesc;
    }
    
    public String getCode() {
        return LineCde;
    }
    
    public void setName(String LineDesc) {
        this.LineDesc = LineDesc;
    }
    
    public float getxAxis() {
        return xAxis;
    }
    
    public void setxAxis(float x) {
        this.xAxis = x;
    }
    
    public float getyAxis() {
        return yAxis;
    }
    
    public void setyAxis(float y) {
        this.yAxis = y;
    }
    
}
