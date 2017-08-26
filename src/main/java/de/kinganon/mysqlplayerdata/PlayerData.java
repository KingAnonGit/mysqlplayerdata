package de.kinganon.mysqlplayerdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class PlayerData {
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_UNDERLINE_YELLOW = "\u001B[33;4m";
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Dieses Programm lässt dich Spieler daten übertragen.\n");
        System.out.println("Bitte gib den die IP der Datenbanken an.\nIP: ");
        String address = br.readLine();
        System.out.println("Bitte gib die erste Datenbank an.\nDatenbankname: ");
        String database1 = br.readLine();
        System.out.println("Bitte gib den Benuter erste Datenbank an.\nBenutzername: ");
        String database1user = br.readLine();
        System.out.println("Bitte gib das Passwort erste Datenbank an.\nPasswort: ");
        String database1password = br.readLine();
        System.out.println("Bitte gib die Tabelle erste Datenbank an.\nTabellenname: ");
        String database1table = br.readLine();
        System.out.println("Bitte gib die Spalte erste Datenbank an.\nSpaltenname: ");
        String database1line = br.readLine();
        System.out.println("Bitte gib die zweite Datenbank an.\nDatenbankname: ");
        String database2 = br.readLine();
        System.out.println("Bitte gib den Benuter zweiten Datenbank an.\nBenutzername: ");
        String database2user = br.readLine();
        System.out.println("Bitte gib das Passwort zweiten Datenbank an.\nPasswort: ");
        String database2password = br.readLine();
        System.out.println("Bitte überprüfe die Daten:\n" +
                "IP der Datenbanken: " + address + "\n" +
                ANSI_UNDERLINE_YELLOW + "\nDatenbank 1\n\r" + ANSI_RESET +
                "Name: " + database1 + "\n" +
                "Username: " + database1user + "\n" +
                "Passwort: " + database1password + "\n" +
                "Tabelle: " + database1table + "\n" +
                "Spalte: " + database1line + "\n" +
                ANSI_UNDERLINE_YELLOW + "\nDatenbank 2\n\r" + ANSI_RESET +
                "Name: " + database2 + "\n" +
                "Username: " + database2user + "\n" +
                "Passwort: " + database2password + "\n");
        System.out.println("\nSind diese angaben richtig? " + ANSI_GREEN + "Ja/" + ANSI_RED + "Nein" + ANSI_RESET + "\n");
        String answer = br.readLine();
        if (answer.equalsIgnoreCase("ja")) {
            new MySQL(address, database1user, database1password, database1, database2user, database2password, database2);
            String[] uuids = MySQL.getUUID(database1line, database1table);
            for (int i = 0; i < uuids.length; i++) {
                int counter = i +1;
                System.out.print("\r"+counter+"/"+uuids.length);
                MySQL.addUser(uuids[i]);
            }
            System.out.println("\n"+ANSI_GREEN + "Fertig!");
        } else {
            System.exit(1);
        }
    }
}
