package adsbacamo;

import java.lang.Math;

public class LatLon 
{ 
  // NL (Number of longitude zones depending from latitude zones
    private static double getNlValue(double Lat) {      
      double lat = Math.abs(Lat);
      if (lat < 10.47047130)
        return 59;
      else if (lat < 14.82817437)
        return 58;
      else if (lat < 18.18626357)
        return 57;
      else if (lat < 21.02939493)
        return 56;
      else if (lat < 23.54504487)
        return 55;
      else if (lat < 25.82924707)
        return 54;
      else if (lat < 27.93898710)
        return 53;
      else if (lat < 29.911356862)
        return 52;
      else if (lat < 31.77209708)
        return 51;
      else if (lat < 33.53993436)
        return 50;
      else if (lat < 35.22899598)
        return 49;
      else if (lat < 36.85025108)
        return 48;
      else if (lat < 38.41241892)
        return 47;
      else if (lat < 39.92256684)
        return 46;
      else if (lat < 41.38651832)
        return 45;
      else if (lat < 42.80914012)
        return 44;
      else if (lat < 44.19454951)
        return 43;
      else if (lat < 45.54626723)
        return 42;
      else if (lat < 46.86733252)
        return 41;
      else if (lat < 48.16039128)
        return 40;
      else if (lat < 49.42776439)
        return 39;
      else if (lat < 50.67150166)
        return 38;
      else if (lat < 51.89342469)
        return 37;
      else if (lat < 53.09516153)
        return 36;
      else if (lat < 54.27817472)
        return 35;
      else if (lat < 55.44378444)
        return 34;
      else if (lat < 56.59318756)
        return 33;
      else if (lat < 57.72747354)
        return 32;
      else if (lat < 58.84763776)
        return 31;
      else if (lat < 59.95459277)
        return 30;
      else if (lat < 61.04917774)
        return 29;
      else if (lat < 62.13216659)
        return 28;
      else if (lat < 63.20427479)
        return 27;
      else if (lat < 64.26616523)
        return 26;
      else if (lat < 65.31845310)
        return 25;
      else if (lat < 66.36171008)
        return 24;
      else if (lat < 67.39646774)
        return 23;
      else if (lat < 68.42322022)
        return 22;
      else if (lat < 69.44242631)
        return 21;
      else if (lat < 70.45451075)
        return 20;
      else if (lat < 71.45986473)
        return 19;
      else if (lat < 72.45884545)
        return 18;
      else if (lat < 73.45177442)
        return 17;
      else if (lat < 74.43893416)
        return 16;
      else if (lat < 75.42056257)
        return 15;
      else if (lat < 76.39684391)
        return 14;
      else if (lat < 77.36789461)
        return 13;
      else if (lat < 78.33374083)
        return 12;
      else if (lat < 79.29428225)
        return 11;
      else
        // north/south of 80.24923213
        return 10;
    }
    
    // j: latitude zone index
    private static double getLatZoneIndex (double Lat[], double nbValue){
      return Math.floor(((59*Lat[0]-60*Lat[1])/nbValue)+0.5);
    }
    
    // Rlat(i): Latitude Position
    private static double getRLatCalc (double Dlat[], int j, int i, double Lat[], double nbValue) {
      return (Dlat[i]* (mod(j, (60-i))) + (Lat[i]/nbValue));
    }
    
    // m: NL (Number of LonZone): NL(Rlat0) must equal NL(Rlat1) ... if (NL(Rlat0)==NL(Rlat1))
    private static double getLonZoneIndex (double Lon [], double nbValue, double NL){
      return ((((NL-1)*Lon[0]-NL*Lon[1])/nbValue)+0.5);
    }
    
    // Rlon(i): Longitude Position
    private static double getRLonCalc (double Dlon, int m, int i, double Lon[], double nbValue, double NL) {
        return ( Dlon * mod(m, (NL-i)) + (Lon[i]/nbValue) );
    } 

    // mod
    private static double mod ( double x, double y ) {
      return (double) x - y * Math.floor ( x / y );
    }
        
    public static double [] getRlatRlon(int iLat[], int iLon[]){      
      int even = 0;
	  int odd = 1;
      double [] lon = new double[2];
      double [] lat = new double[2];
      double [] Dlat = {360/60, 360/59};
      double [] Rlat = new double[2];
      double [] Rlon = new double[2];
      double [] latLon = new double [2];
      lon[even] = (double)iLon[even];
      lon[odd] = (double)iLon[odd];
      lat[even] = (double)iLat[even];
      lat[odd] = (double)iLat[odd];
    double nbValue =  Math.pow(2, 17);
    
    // Rlat calculation
    int j = (int)getLatZoneIndex(lat, nbValue);
        Rlat[even] = getRLatCalc(Dlat, j, even, lat, nbValue);
        Rlat[odd] = getRLatCalc(Dlat, j, odd, lat, nbValue);
    latLon[0] = Rlat[odd];
    
    // Rlon calculation
    if (getNlValue(Rlat[even])==getNlValue(Rlat[odd])){
          double NL[]= new double[2];
          NL [even] = getNlValue(Rlat[even]);
          NL [odd] = getNlValue(Rlat[odd]);
          double Dlon = 360.0/(Math.max((NL[odd]-odd), 1));
          int m = (int)getLonZoneIndex(lon, nbValue, NL[odd]);
          Rlon[even] = getRLonCalc(Dlon, m, even, lon, nbValue, NL[odd]);
            Rlon[odd] = getRLonCalc(Dlon, m, odd, lon, nbValue, NL[odd]);
            latLon[1] = Rlon[odd];
        }
    
      return latLon;
    }
    
  // public static void main(String[] args) {    
    // int [] Lat = {92095, 88385};
        // int [] Lon = {39846, 125818};
    // double [] latLon = new double[2];
    // latLon = getRlatRlon(Lat, Lon);
    // System.out.println("Rlat " + latLon[0]);
    // System.out.println("Rlon " + latLon[1]);
  // }
}
