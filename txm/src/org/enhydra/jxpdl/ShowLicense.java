/**
 * Together XPDL Model
 * Copyright (C) 2011 Together Teamsolutions Co., Ltd. 
 * 
 * This program is free software: you can redistribute it and/or modify 
 * it under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. 
 *
 * This program is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the 
 * GNU General Public License for more details. 
 *
 * You should have received a copy of the GNU General Public License 
 * along with this program. If not, see http://www.gnu.org/licenses
 */

package org.enhydra.jxpdl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Utility class that prints TXM GNU Disclaimer or TXM License text into the console
 * output.
 */
public class ShowLicense {

   /**
    * Prints TXM GNU Disclaimer or TXM license text into the console output. If one of the
    * provided String arguments is equal to '-showlicense', the class prints TXM license
    * (the license text file has to be in the classpath), otherwise it prints TXM GNU
    * Disclaimer.
    * @param args String array with arguments. The only relevant argument is currently -showlicense.
    * @return false if GNU disclaimer was printed
    */
   public static boolean showLicense(String[] args) {
      String gnuDisclaimer = "\n";
      gnuDisclaimer += "\n";
      gnuDisclaimer += "Together XPDL Model";
      gnuDisclaimer += "\nCopyright (C) 2011 Together Teamsolutions Co., Ltd.";
      gnuDisclaimer += "\n";
      gnuDisclaimer += "\nThis program comes with ABSOLUTELY NO WARRANTY; for details use the -showlicense option.";
      gnuDisclaimer += "\nThis is free software, and you are welcome to redistribute it under certain conditions; use the -showlicense option for details.";
      gnuDisclaimer += "\n";
      gnuDisclaimer += "\n";
      try {
         for (int i = 0; i < args.length; i++) {
            if ("-showlicense".equals(args[i])) {
               String textL = "";
               URL u = ShowLicense.class.getClassLoader()
                  .getResource("org/enhydra/jxpdl/License.txt");
               URLConnection ucon = u.openConnection();

               BufferedReader br = new BufferedReader(new InputStreamReader(ucon.getInputStream()));

               String temp;
               while ((temp = br.readLine()) != null) {
                  textL += temp + "\n";
               }
               System.out.println(textL);
               return true;
            }
         }
      } catch (Throwable thr) {
         System.out.println("Problem with printing project license!");
         return true;
      }
      System.out.println(gnuDisclaimer);
      return false;
   }
}
