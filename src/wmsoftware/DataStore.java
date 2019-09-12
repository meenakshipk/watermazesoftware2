/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wmsoftware;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Meenakshi 10092019
 */
public class DataStore {

    private File[] fileList;
    private Mouse[] Mice;
    private HashMap<Integer, XYSeries> posHMap = null;
    private HashMap<Integer, ArrayList<Integer>> resTimeHMap = null;
    private HashMap<Integer, ArrayList<Float>> distHMap = null;
    private HashMap<Integer, ArrayList<Float>> velHMap = null;
    private HashMap<Integer, ArrayList<Float>> velCosHMap = null;
    private HashMap<Integer, ArrayList<Float>> velSinHMap = null;
    private HashMap<Integer, ArrayList<Float>> velErrHMap = null;

    /**
     *
     * @param num Total number of mice
     * @param files
     */
    public DataStore(int num, File[] files) {
        Mice = new Mouse[num];
        for (int i = 0; i < Mice.length; i++) {
            Mice[i] = new Mouse();
            Mice[i].setID(i);
        }
        fileList = files;
    }

    /**
     * Get the files selected for a particular DataStore/trial For now, prints
     * the array of files as well
     *
     * @return an array of files
     */
    public File[] getFiles() {
        return this.fileList;
    }

    public Mouse[] getMice() {
        return this.Mice;
    }

//    public int getMiceTotal() {
//        return this.Mice.length;
//    }

    public HashMap getHMap(String s) {
        HashMap result = null;
        switch (s) {
            case "Position":
                result = posHMap;
                break;
            case "Residence Time":
                result = resTimeHMap;
                break;
            case "Distance":
                result = distHMap;
                break;
            case "Velocity":
                result = velHMap;
                break;
            case "Velocity along Pt":
                result = velCosHMap;
                break;
            case "Velocity perpendicular Pt":
                result = velSinHMap;
                break;
            case "Velocity Error":
                result = velErrHMap;
                break;
        }
        return result;
    }

    public void setHMap(String s, HashMap hm) {
        switch (s) {
            case "Position":
                posHMap = hm;
            case "Residence Time":
                resTimeHMap = hm;
            case "Distance":
                distHMap = hm;
            case "Velocity":
                velHMap = hm;
            case "Velocity along Pt":
                velCosHMap = hm;
            case "Velocity perpendicular Pt":
                velSinHMap = hm;
            case "Velocity Error":
                velErrHMap = hm;
        }
    }

    /**
     * Reads an array of file
     *
     */
    public void readFile() {
        if (this.fileList != null) {
            int noFiles = this.fileList.length;   //stores the number of files selected by the user
            int count = 0;

            for (File curFile : this.fileList) {

                String dataString = "";
                int c = 0;
                float xData = 0;
                float yData = 0;
                FileReader fReader = null;                      //Reader class : Java class for reading text files (ASCII)
                XYSeries series = new XYSeries("Position", false);
                posHMap = posHMap == null ? new HashMap<>() : posHMap;

                if (curFile.exists()) {

                    try {
                        fReader = new FileReader(curFile);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(DataStore.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try {
                        while ((c = fReader.read()) != -1) {
                            switch (c) {
                                case '\t':
                                    xData = Float.parseFloat(dataString);
                                    dataString = "";
                                    break;
                                case '\n':
                                    yData = Float.parseFloat(dataString);
                                    //dt.addData(xData, yData);
                                    XYDataItem XY = new XYDataItem(xData, yData);
                                    series.add(XY);
                                    dataString = "";
                                    break;
                                default:
                                    dataString += (char) c;
                            }
                        }
                        posHMap.put(Mice[count].getID(), series);
                        count = count + 1;
                    } catch (IOException ex) {
                        Logger.getLogger(DataStore.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
}
