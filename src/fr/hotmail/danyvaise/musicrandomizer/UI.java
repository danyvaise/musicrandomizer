package fr.hotmail.danyvaise.musicrandomizer;

import java.util.Scanner;

/*
 * Classe d'interaction utilisateur.
 */

public class UI
    {
    Scanner sc_input;
    String path;
    String targetFolderPath;
    int nbFiles;
    int nbFolders;
    
    public UI()
        {
        sc_input = new Scanner(System.in);
        path = "";
        targetFolderPath = "";
        nbFiles = 0;
        nbFolders = 0;
        }
    
    public UI(String str_path, String str_targetFolderPath, int i_nbFiles, int i_nbFolders)
        {
        sc_input = new Scanner(System.in);
        path = str_path;
        targetFolderPath = str_targetFolderPath;
        nbFiles = i_nbFiles;
        nbFolders = i_nbFolders;
        }
    
    public void displayHeader()
        {
        System.out.println("=============================");
        System.out.println("MusicRandomizer 0.2.0");
        System.out.println("by @Dany Vaise");
        System.out.println("=============================\n");
        }
    
    public void enterPath()
        {
        System.out.println("Please enter the music directory : ");
        path = sc_input.nextLine();
        }
    
    public void enterNbFilePerFolder()
        {
        String s = "";
        System.out.println("Please enter a number of files by directory : ");
        s = sc_input.nextLine();
        nbFiles = Integer.parseInt(s);
        }
    
    public void enterNbFolders()
        {
        String s = "";
        System.out.println("Please enter a number of folder.\n0 or nothing for unlimited folders :");
        s = sc_input.nextLine();
        
        if (s.equals(""))
            {
            s = "0";
            }
        
        nbFolders = Integer.parseInt(s);
        }
    
    public void enterTargetFolderPath()
        {
        String s = "";
        System.out.println("Please enter the target directory : ");
        s = sc_input.nextLine();
        targetFolderPath = s;
        }
    
    public void displayFileName(String fileName)
        {
        System.out.print(fileName + "\n");
        }
    
    public void displaySeparator(int i)
        {
        if (i == 1)
            {
            System.out.println();
            System.out.println("-------------------------------");
            }
        
        if (i == 2)
            {
            System.out.println("-------------------------------");
            }
        }
    
    public void displayError(int i)
        {
        if (i == 1)
            {
            System.err.println("\nNo file in the directory !");
            }
        
        if (i == 2)
            {
            System.err.println("\nThe directory is incorrect !");
            }
        }
    
    public String getPath()
        {
        return path;
        }
    
    public void setPath(String str_path)
        {
        path = str_path;
        }
    
    public String getTargetFolderPath()
        {
        return targetFolderPath;
        }
    
    public void setTargetFolderPath(String str_targetFolderPath)
        {
        targetFolderPath = str_targetFolderPath;
        }
    
    public int getNbFilePerFolder()
        {
        return nbFiles;
        }
    
    public void setNbFilePerFolder(int i_nbFiles)
        {
        nbFiles = i_nbFiles;
        }
    
    public int getNbFolders()
        {
        return nbFolders;
        }
    
    public void setNbFolder(int i_nbFolders)
        {
        nbFolders = i_nbFolders;
        }
    }
