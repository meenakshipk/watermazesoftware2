/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wmsoftware;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.data.xy.XYDataItem;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author Meenakshi 10092019
 */
public class DataStore {

    private File[] fileList;
    private ArrayList<Mouse> Mice = new ArrayList<>();
    private HashMap<Integer, XYSeries> posHMap = null;
    private HashMap<Integer, ArrayList<Integer>> resTimeHMap = null;
    private HashMap<Integer, ArrayList<Float>> distHMap = null;
    private HashMap<Integer, ArrayList<Float>> velHMap = null;
    private HashMap<Integer, ArrayList<Float>> velCosHMap = null;
    private HashMap<Integer, ArrayList<Float>> velSinHMap = null;
    private HashMap<Integer, ArrayList<Float>> velErrHMap = null;
//    private HashMap<Integer, ArrayList<Float>> RmVelMap = null;
//    private HashMap<Integer, ArrayList<Float>> RmVelCosMap = null;
//    private HashMap<Integer, ArrayList<Float>> RmVelSinMap = null;
//    private HashMap<Integer, ArrayList<Float>> RmVelErrMap = null;
//    private HashMap<Integer, ArrayList<Float>> RmVelPlot = null;
//    private HashMap<Integer, ArrayList<Float>> RmVelCosPlot = null;
//    private HashMap<Integer, ArrayList<Float>> RmVelSinPlot = null;
//    private HashMap<Integer, ArrayList<Float>> RmVelErrPlot = null;
    private HashMap<Integer, Float> RmDistMap = null;
    private HashMap<Integer, Float> RmVelMap = null;
    private HashMap<Integer, Float> RmVelCosMap = null;
    private HashMap<Integer, Float> RmVelSinMap = null;
    private HashMap<Integer, Float> RmVelErrMap = null;
    private HashMap<Integer, Float> RmVelPlot = null;
    private HashMap<Integer, Float> RmVelCosPlot = null;
    private HashMap<Integer, Float> RmVelSinPlot = null;
    private HashMap<Integer, Float> RmVelErrPlot = null;

    /**
     *
     * @param num Total number of mice
     * @param files
     */
    public DataStore(int num, File[] files) {
        for (int i = 0; i < num; i++) {
            Mice.add(i, new Mouse());
            Mice.get(i).setID(i);
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
        return fileList;
    }

    public int getTotalMice() {
        return fileList.length;
    }

    public ArrayList<Mouse> getMiceList() {
        return Mice;
    }

    public void setMouse(Mouse M) {
        Mice.add(M);
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
            case "Velocity along Platform":
                result = velCosHMap;
                break;
            case "Velocity perpendicular Platform":
                result = velSinHMap;
                break;
            case "Velocity Error":
                result = velErrHMap;
                break;
            case "Distance Rm Map":
                result = RmDistMap;
                break;
            case "Velocity Rm Map":
                result = RmVelMap;
                break;
            case "Velocity along Platform Rm Map":
                result = RmVelCosMap;
                break;
            case "Velocity perpendicular Platform Rm Map":
                result = RmVelSinMap;
                break;
            case "Velocity Error Rm Map":
                result = RmVelErrMap;
                break;
            case "Velocity Rm Plot":
                result = RmVelPlot;
                break;
            case "Velocity along Platform Rm Plot":
                result = RmVelCosPlot;
                break;
            case "Velocity perpendicular Platform Rm Plot":
                result = RmVelSinPlot;
                break;
            case "Velocity Error Rm Plot":
                result = RmVelErrPlot;
                break;
        }
        return result;
    }

    public void setHMap(String s, HashMap hm) {
        switch (s) {
            case "Position":
                posHMap = hm;
                break;
            case "Residence Time":
                resTimeHMap = hm;
                break;
            case "Distance":
                distHMap = hm;
                break;
            case "Velocity":
                velHMap = hm;
                break;
            case "Velocity along Platform":
                velCosHMap = hm;
                break;
            case "Velocity perpendicular Platform":
                velSinHMap = hm;
                break;
            case "Velocity Error":
                velErrHMap = hm;
                break;
            case "Distance Rm Map":
                RmDistMap = hm;
                break;
            case "Velocity Rm Map":
                RmVelMap = hm;
                break;
            case "Velocity along Platform Rm Map":
                RmVelCosMap = hm;
                break;
            case "Velocity perpendicular Platform Rm Map":
                RmVelSinMap = hm;
                break;
            case "Velocity Error Rm Map":
                RmVelErrMap = hm;
                break;
            case "Velocity Rm Plot":
                RmVelPlot = hm;
                break;
            case "Velocity along Platform Rm Plot":
                RmVelCosPlot = hm;
                break;
            case "Velocity perpendicular Platform Rm Plot":
                RmVelSinPlot = hm;
                break;
            case "Velocity Error Rm Plot":
                RmVelErrPlot = hm;
                break;
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
                        posHMap.put(Mice.get(count).getID(), series);
                        count = count + 1;
                    } catch (IOException ex) {
                        Logger.getLogger(DataStore.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

//    public File writeFiles(String name, String directory, HashMap hs) {
//
//        FileWriter outStream = null;
//        File out = new File(directory + "\\" + name);
//        int totalMice = hs.size();
//        int count = 0;
//        String toWrite = "";
//        try {
//            outStream = new FileWriter(out);
//            while (count < totalMice) {
//                ArrayList<Float> measure = (ArrayList<Float>) hs.get(count);
//                int count2 = 0;
//                int datalength = measure.size();
//
//                while (count2 < datalength) {
//                    if (count2 == 0) {
//                        toWrite = "Mouse" + count + "\t";
//                    }
//                    if (count2 == datalength - 1) {
//                        toWrite = toWrite + measure.get(count2) + "\n";
//                    } else {
//                        toWrite = toWrite + measure.get(count2) + "\t";
//                    }
//                    count2++;
//                }
//                outStream.write(toWrite);
//                count++;
//            }
//            outStream.close();
//        } catch (IOException ex) {
//            Logger.getLogger(DataStore.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return out;
//    }
    public File writeFiles(String name, String directory, HashMap hs) {

        FileWriter outStream = null;
        File out = new File(directory + "\\" + name);
        int totalMice = hs.size();
        int count = 0;
        String toWrite = "";
        try {
            outStream = new FileWriter(out);
            while (count < totalMice) {
                float measure = (float) hs.get(count);
                toWrite = "Mouse" + count + "\t" + measure + "\n";
                outStream.write(toWrite);
                count++;
            }
            outStream.close();
        } catch (IOException ex) {
            Logger.getLogger(DataStore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }
}
