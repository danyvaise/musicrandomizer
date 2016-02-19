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
    int folderMod;
    
    public UI()
        {
        sc_input = new Scanner(System.in);
        path = "";
        targetFolderPath = "";
        nbFiles = 0;
        folderMod = 0;
        }
    
    public UI(String str_path, String str_targetFolderPath, int i_nbFiles, int i_folderMod)
        {
        sc_input = new Scanner(System.in);
        path = str_path;
        targetFolderPath = str_targetFolderPath;
        nbFiles = i_nbFiles;
        folderMod = i_folderMod;
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
    
    public void enterFolderMod()
        {
        String s = "";
        System.out.println("Folder mode :\n    [1] -> 1 folder\n    [0] -> several folders");
        s = sc_input.nextLine();
        folderMod = Integer.parseInt(s);
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
            System.err.println("\nNot enough folders to create please up the number of files !");
            }
        
        if (i == 3)
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
    
    public int getFolderMod()
        {
        return folderMod;
        }
    
    public void setFolderMod(int i_folderMod)
        {
        folderMod = i_folderMod;
        }
    }
