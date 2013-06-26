 import java.io.*;
   import java.net.*;
   import java.util.*;
   
   public class ListNets {
   
     public static void main(String args[]) throws 
         SocketException {
       Enumeration<NetworkInterface> nets =
         NetworkInterface.getNetworkInterfaces();
       for (NetworkInterface netint : Collections.list(nets)) {
         displayInterfaceInformation(netint);
       }
     }
   
     private static void displayInterfaceInformation(
         NetworkInterface netint) throws SocketException {
       System.out.printf("Display name: %s%n",netint.getDisplayName());
       System.out.printf("Name: %s%n", netint.getName());
       Enumeration<InetAddress> inetAddresses = 
           netint.getInetAddresses();
       for (InetAddress inetAddress : Collections.list(
           inetAddresses)) {
         System.out.printf("InetAddress: %s%n", inetAddress);
       }
   
       System.out.printf("Parent: %s%n", netint.getParent());
       System.out.printf("Up? %s%n", netint.isUp());
       System.out.printf("Loopback? %s%n", netint.isLoopback());
       System.out.printf(
           "PointToPoint? %s%n", netint.isPointToPoint());
       System.out.printf(
           "Supports multicast? %s%n", netint.isVirtual());
       System.out.printf("Virtual? %s%n", netint.isVirtual());
       System.out.printf("Hardware address: %s%n",
         Arrays.toString(netint.getHardwareAddress()));
       System.out.printf("MTU: %s%n", netint.getMTU());
   
       List<InterfaceAddress> interfaceAddresses = 
           netint.getInterfaceAddresses();
       for (InterfaceAddress addr : interfaceAddresses) {
         System.out.printf(
             "InterfaceAddress: %s%n", addr.getAddress());
       }
       System.out.printf("%n");
       Enumeration<NetworkInterface> subInterfaces = 
           netint.getSubInterfaces();
       for (NetworkInterface networkInterface : Collections.list(
           subInterfaces)) {
         System.out.printf("%nSubInterface%n");
         displayInterfaceInformation(networkInterface);
       }
       System.out.printf("%n");
     }
   } 